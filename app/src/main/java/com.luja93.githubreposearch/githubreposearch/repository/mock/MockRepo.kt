package com.luja93.githubreposearch.githubreposearch.repository.mock

import com.luja93.githubreposearch.common.data.local.AppDatabase
import com.luja93.githubreposearch.common.data.remote.ApiInterface
import com.luja93.githubreposearch.common.mvvm.basemodels.ResourceState
import com.luja93.githubreposearch.common.session.SessionPrefImpl
import com.luja93.githubreposearch.githubreposearch.model.User
import com.luja93.githubreposearch.githubreposearch.model.api.SearchReposResponse
import com.luja93.githubreposearch.githubreposearch.model.pagination.ReposPaginationParams
import com.luja93.githubreposearch.githubreposearch.repository.repo.RepoRepo
import com.luja93.githubreposearch.githubreposearch.repository.user.UserRepo
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by lleopoldovic on 03/10/2019.
 */

class MockRepo @Inject constructor(
    private val database: AppDatabase,
    private val api: ApiInterface,
    private val session: SessionPrefImpl
) : RepoRepo, UserRepo {

    override fun getRepositories(params: ReposPaginationParams): Observable<ResourceState<SearchReposResponse>> {
        // TODO: Define if necessary
        return Observable.empty()
    }

    override fun getUser(id: Long, username: String): Observable<ResourceState<User>> {
        // TODO: Define if necessary
        return Observable.empty()
    }

}