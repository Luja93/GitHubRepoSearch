package com.luja93.githubreposearch.githubreposearch.repository.repo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luja93.githubreposearch.githubreposearch.model.Repo
import io.reactivex.Observable

@Dao
interface RepoDao : RepoData {

    @Query("select * from Repo where name like :query")
    override fun getRepositories(query: String): Observable<List<Repo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun saveRepository(repo: Repo)

}