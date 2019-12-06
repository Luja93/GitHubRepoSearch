package com.luja93.githubreposearch.githubreposearch.repository.user

import com.luja93.githubreposearch.githubreposearch.model.User
import io.reactivex.Completable
import io.reactivex.Observable

interface UserData {

    fun getUser(id: Long): Observable<User>

    fun saveUser(user: User): Completable

}