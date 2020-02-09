package com.luja93.githubreposearch.common.dagger.modules

import com.luja93.githubreposearch.BuildConfig
import com.luja93.githubreposearch.common.data.remote.ApiInterface
import com.luja93.githubreposearch.common.data.remote.RxErrorHandlingCallAdapterFactory
import com.luja93.githubreposearch.utils.GsonUtil
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

/**
 * Created by lleopoldovic on 23/09/2019.
 */

@Module(includes = [NetworkModule::class])
class ApiServiceModule {

    @Provides
    @Singleton
    fun apiService(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun retrofit(client: OkHttpClient): Retrofit {

        val gson = GsonUtil.defaultGson

        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.BASE_API_URL)
            .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

}