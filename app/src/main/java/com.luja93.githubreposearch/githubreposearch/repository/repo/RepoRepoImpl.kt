package com.luja93.githubreposearch.githubreposearch.repository.repo

import com.luja93.githubreposearch.common.data.local.AppDatabase
import com.luja93.githubreposearch.common.data.remote.ApiInterface
import com.luja93.githubreposearch.common.mvvm.BaseRepo
import com.luja93.githubreposearch.common.mvvm.basemodels.ResourceState
import com.luja93.githubreposearch.common.session.SessionPrefImpl
import com.luja93.githubreposearch.githubreposearch.model.Repo
import com.luja93.githubreposearch.githubreposearch.repository.mock.MockRepo
import io.reactivex.Observable
import javax.inject.Inject

class RepoRepoImpl @Inject constructor(
    private val database: AppDatabase,
    private val api: ApiInterface,
    private val session: SessionPrefImpl,
    private val mockRepo: MockRepo
): BaseRepo(database, api, session), RepoRepo {

    override fun getRepositories(
        query: String,
        sort: Repo.Sorting
    ): Observable<ResourceState<List<Repo>>> {
        return twoSideCall(
            // TODO: Sort the values when fetching locally
            firstCall = { database.repositoryDao().getRepositories("%$query%") },
            secondCall = { api.searchRepositories(query, sort.value).map { it.items } },
            saveSecondCallData = { repos ->
                repos.forEach { repo ->
                    database.repositoryDao().saveRepository(repo)
                }
            }
        )
    }

}