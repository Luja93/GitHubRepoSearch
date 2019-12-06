package com.luja93.githubreposearch.common.dagger.modules

import android.content.Context
import androidx.room.Room
import com.luja93.githubreposearch.common.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by lleopoldovic on 13/09/2019.
 */

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun database(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}