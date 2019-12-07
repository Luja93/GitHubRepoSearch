package com.luja93.githubreposearch.githubreposearch.repository.user

import com.luja93.githubreposearch.common.mvvm.basemodels.ResourceState
import com.luja93.githubreposearch.githubreposearch.model.User
import io.reactivex.Observable

/**
 * Created by lleopoldovic on 06/12/2019.
 */

interface UserRepo {

    fun getUser(id: Long, username: String): Observable<ResourceState<User>>

}