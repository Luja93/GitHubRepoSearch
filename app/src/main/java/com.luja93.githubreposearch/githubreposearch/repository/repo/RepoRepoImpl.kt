package com.luja93.githubreposearch.githubreposearch.repository.repo

import com.luja93.githubreposearch.common.data.local.AppDatabase
import com.luja93.githubreposearch.common.data.remote.ApiInterface
import com.luja93.githubreposearch.common.mvvm.BaseRepo
import com.luja93.githubreposearch.common.mvvm.basemodels.ResourceState
import com.luja93.githubreposearch.common.session.SessionPrefImpl
import com.luja93.githubreposearch.githubreposearch.model.Repo
import com.luja93.githubreposearch.githubreposearch.model.api.SearchReposResponse
import com.luja93.githubreposearch.githubreposearch.repository.mock.MockRepo
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by lleopoldovic on 06/12/2019.
 */

class RepoRepoImpl @Inject constructor(
    private val database: AppDatabase,
    private val api: ApiInterface,
    private val session: SessionPrefImpl,
    private val mockRepo: MockRepo
): BaseRepo(database, api, session), RepoRepo {

    override fun getRepositories(
        query: String,
        sort: Repo.Sorting
    ): Observable<ResourceState<SearchReposResponse>> {
        // Since GitHub's search query is a bit more complex than just the SQL's "LIKE" operator,
        // fetch local data only when remote call fails.
        return oneSideCall(
            call = {
                api.searchRepositories(query, sort.value)
            },
            saveCallData = { response ->
                response.items.forEach { repo ->
                    database.repositoryDao().saveRepository(repo)
                }
            },
            performOnError = {
                database.repositoryDao().getRepositories("%$query%").flatMap {
                    val repos = when (sort) {
                        Repo.Sorting.Default -> it
                        Repo.Sorting.Forks -> it.sortedByDescending { repo -> repo.forksCount }
                        Repo.Sorting.Stars -> it.sortedByDescending { repo -> repo.watcherCount }
                        Repo.Sorting.Issues -> it.sortedByDescending { repo -> repo.openIssuesCount }
                    }
                    Observable.just(SearchReposResponse(it.count().toLong(), repos))
                }
            }
        )
    }

}