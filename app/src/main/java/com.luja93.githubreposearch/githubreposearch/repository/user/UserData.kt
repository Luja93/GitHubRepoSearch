package com.luja93.githubreposearch.githubreposearch.repository.user

import com.luja93.githubreposearch.githubreposearch.model.User
import io.reactivex.Observable

/**
 * Created by lleopoldovic on 06/12/2019.
 */

interface UserData {

    fun getUser(id: Long): Observable<User>

    fun saveUser(user: User)

}