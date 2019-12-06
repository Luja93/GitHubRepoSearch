package com.luja93.githubreposearch.common.dagger.modules

import com.luja93.githubreposearch.BuildConfig
import com.luja93.githubreposearch.common.session.SessionPrefImpl
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

/**
 * Created by lleopoldovic on 06/12/2019.
 */

@Module(includes = [ContextModule::class])
class NetworkModule {

    @Provides
    @Singleton
    fun requestInterceptor(): Interceptor {
        return Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Accept", "application/vnd.github.v3+json")
                .method(original.method, original.body)

            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun okHttpClient(requestInterceptor: Interceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(requestInterceptor)

        // Add logging interceptor for DEBUG variants
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            client.addInterceptor(interceptor)
        }

        return client.build()
    }
}

