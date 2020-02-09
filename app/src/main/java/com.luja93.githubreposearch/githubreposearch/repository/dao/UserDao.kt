package com.luja93.githubreposearch.githubreposearch.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luja93.githubreposearch.githubreposearch.model.User
import io.reactivex.Observable

/**
 * Created by lleopoldovic on 06/12/2019.
 */

@Dao
interface UserDao {

    @Query("select * from User where id = :id")
    fun getUser(id: Long): Observable<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User)

}