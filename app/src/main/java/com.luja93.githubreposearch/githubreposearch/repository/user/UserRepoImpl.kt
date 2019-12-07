package com.luja93.githubreposearch.githubreposearch.repository.user

import com.luja93.githubreposearch.common.data.local.AppDatabase
import com.luja93.githubreposearch.common.data.remote.ApiInterface
import com.luja93.githubreposearch.common.mvvm.BaseRepo
import com.luja93.githubreposearch.common.mvvm.basemodels.ResourceState
import com.luja93.githubreposearch.common.session.SessionPrefImpl
import com.luja93.githubreposearch.githubreposearch.model.User
import com.luja93.githubreposearch.githubreposearch.repository.mock.MockRepo
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by lleopoldovic on 06/12/2019.
 */

class UserRepoImpl @Inject constructor(
    private val database: AppDatabase,
    private val api: ApiInterface,
    private val session: SessionPrefImpl,
    private val mockRepo: MockRepo
): BaseRepo(database, api, session), UserRepo {

    override fun getUser(id: Long, username: String): Observable<ResourceState<User>> {
        return twoSideCall(
            firstCall = { database.userDao().getUser(id) },
            secondCall = { api.getUser(username) },
            saveSecondCallData = { database.userDao().saveUser(it) }
        )
    }

}