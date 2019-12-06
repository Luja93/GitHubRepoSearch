package com.luja93.githubreposearch.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luja93.githubreposearch.githubreposearch.model.Owner
import com.luja93.githubreposearch.githubreposearch.model.Repo
import com.luja93.githubreposearch.githubreposearch.model.User
import com.luja93.githubreposearch.githubreposearch.repository.repo.RepoDao
import com.luja93.githubreposearch.githubreposearch.repository.user.UserDao

/**
 * Created by lleopoldovic on 13/09/2019.
 */

@Database(
    entities = [Repo::class, Owner::class, User::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun repositoryDao() : RepoDao

    abstract fun userDao() : UserDao

}