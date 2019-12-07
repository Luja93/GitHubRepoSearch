package com.luja93.githubreposearch.githubreposearch.repository.mock

import com.luja93.githubreposearch.common.data.local.AppDatabase
import com.luja93.githubreposearch.common.data.remote.ApiInterface
import com.luja93.githubreposearch.common.session.SessionPrefImpl
import com.luja93.githubreposearch.githubreposearch.model.Repo
import com.luja93.githubreposearch.githubreposearch.model.User
import com.luja93.githubreposearch.githubreposearch.repository.repo.RepoData
import com.luja93.githubreposearch.githubreposearch.repository.user.UserData
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by lleopoldovic on 03/10/2019.
 */

class MockRepo @Inject constructor(
    private val database: AppDatabase,
    private val api: ApiInterface,
    private val session: SessionPrefImpl
) : RepoData, UserData {

    override fun saveRepository(repo: Repo) { /* not implemented */
    }

    override fun getRepositories(query: String): Observable<List<Repo>> {
        // TODO
        return Observable.empty()
    }

    override fun getUser(id: Long): Observable<User> {
        // TODO
        return Observable.empty()
    }

    override fun saveUser(user: User): Completable = Completable.complete()


}