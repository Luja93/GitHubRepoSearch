package com.luja93.githubreposearch.githubreposearch.repository.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luja93.githubreposearch.githubreposearch.model.User
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface UserDao : UserData {

    @Query("select * from User where id = :id")
    override fun getUser(id: Long): Observable<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun saveUser(user: User): Completable

}