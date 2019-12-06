package com.luja93.githubreposearch.common.dagger.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by lleopoldovic on 13/09/2019.
 */

@Module
class ContextModule(private val context: Context) {

    @Provides
    @Singleton
    fun context(): Context {
        return context
    }

}
