package com.luja93.githubreposearch.githubreposearch

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.luja93.githubreposearch.common.dagger.components.ApplicationComponent
import com.luja93.githubreposearch.common.dagger.components.DaggerApplicationComponent
import com.luja93.githubreposearch.common.dagger.modules.ContextModule
import com.luja93.githubreposearch.common.data.remote.ApiInterface
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Created by lleopoldovic on 13/09/2019.
 */

class App : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    private var apiInterface: ApiInterface? = null

    lateinit var component: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent.builder()
            .contextModule(ContextModule(this))
            .build().also {
                component = it
                apiInterface = component.apiInterface
            }
            .inject(this)

        Glide.with(this).setDefaultRequestOptions(RequestOptions().diskCacheStrategy(
            DiskCacheStrategy.AUTOMATIC)
        )
    }
}