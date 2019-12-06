package com.luja93.githubreposearch.common.data.remote

import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.lang.reflect.Type

class RxErrorHandlingCallAdapterFactory private constructor() : CallAdapter.Factory() {
    private val original: RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return RxCallAdapterWrapper(
            retrofit,
            original.get(returnType, annotations, retrofit) as CallAdapter<out Any, *>
        )
    }

    private class RxCallAdapterWrapper<R>(
        private val retrofit: Retrofit,
        private val wrapped: CallAdapter<R, *>
    ) : CallAdapter<R, Any> {

        override fun responseType(): Type {
            return wrapped.responseType()
        }


        override fun adapt(call: Call<R>): Any {
            val result = wrapped.adapt(call)

            if (result is Observable<*>) {
                return (wrapped.adapt(call) as Observable<*>).onErrorResumeNext { throwable: Throwable ->
                    Observable.error(
                        asRetrofitException(throwable, retrofit)
                    )
                }
            }

            if (result is Completable) {
                return (wrapped.adapt(call) as Completable).onErrorResumeNext { throwable ->
                    Completable.error(
                        asRetrofitException(throwable, retrofit)
                    )
                }
            }

            return result
        }

    }

    companion object {

        fun create(): CallAdapter.Factory {
            return RxErrorHandlingCallAdapterFactory()
        }

        private fun asRetrofitException(
            throwable: Throwable,
            retrofit: Retrofit
        ): RetrofitException {
            Log.d("RxErrorHandler", throwable.message)
            throwable.printStackTrace()

            // We had non-200 http error
            if (throwable is HttpException) {
                val response = throwable.response()
                return RetrofitException.httpError(
                    response?.raw()?.request?.url.toString(),
                    response,
                    retrofit
                )
            }
            // A network error happened
            return if (throwable is IOException) {
                RetrofitException.networkError(throwable)
            } else RetrofitException.unexpectedError(throwable)

            // We don't know what happened. We need to simply convert to an unknown error

        }
    }
}
