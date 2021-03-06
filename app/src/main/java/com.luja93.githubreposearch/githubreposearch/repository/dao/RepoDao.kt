package com.luja93.githubreposearch.githubreposearch.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luja93.githubreposearch.githubreposearch.model.Repo
import io.reactivex.Observable

/**
 * Created by lleopoldovic on 06/12/2019.
 */

@Dao
interface RepoDao {

    @Query("select * from Repo where name like :query limit 30")
    fun getRepositories(query: String): Observable<List<Repo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveRepository(repo: Repo)

}