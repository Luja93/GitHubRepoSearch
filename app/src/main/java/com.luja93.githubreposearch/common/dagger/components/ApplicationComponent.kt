package com.luja93.githubreposearch.common.dagger.components

import android.content.Context
import com.luja93.githubreposearch.githubreposearch.App
import com.luja93.githubreposearch.githubreposearch.repository.mock.MockRepo
import com.luja93.githubreposearch.common.dagger.modules.*
import com.luja93.githubreposearch.common.data.local.AppDatabase
import com.luja93.githubreposearch.common.data.remote.ApiInterface
import com.luja93.githubreposearch.common.session.SessionPrefImpl
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * Created by lleopoldovic on 13/09/2019.
 */

@Singleton
@Component(modules = [
    (AndroidInjectionModule::class),

    (BindingModuleActivities::class),
    (BindingModuleFragments::class),

    (SchedulersModule::class),

    (ApiServiceModule::class),
    (DatabaseModule::class),
    (ViewModelModule::class),
    (RepositoryModule::class)
])
interface ApplicationComponent : AndroidInjector<App> {

    val context: Context

    val apiInterface: ApiInterface

    val database: AppDatabase

    val sessionPrefImpl: SessionPrefImpl


    val mockRepo: MockRepo
}