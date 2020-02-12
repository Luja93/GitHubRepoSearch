package com.luja93.githubreposearch.common.mvvm

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.luja93.githubreposearch.common.data.local.AppDatabase
import com.luja93.githubreposearch.common.data.remote.ApiInterface
import com.luja93.githubreposearch.common.data.remote.RetrofitException
import com.luja93.githubreposearch.common.mvvm.basemodels.BaseError
import com.luja93.githubreposearch.common.mvvm.basemodels.ResourceState
import com.luja93.githubreposearch.common.mvvm.basemodels.ResponseCodes
import com.luja93.githubreposearch.common.session.SessionPrefImpl
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by lleopoldovic on 16/09/2019.
 */

abstract class BaseRepo (
    private val database: AppDatabase,
    private val api: ApiInterface,
    private val session: SessionPrefImpl
) {
    /**
     * Wraps the fetched data with Observable<ResourceState> for appropriate ViewModel provision.
     *
     * Used for fetching the data both from local and remote source. Provide a saveSecondCallData()
     * to locally store the remotely fetched data.
     */
    fun <T> twoSideCall(
        firstCall: () -> Observable<T>,
        secondCall: () -> Observable<T>,
        saveSecondCallData: (data: T) -> Unit
    ) : Observable<ResourceState<T>> {
        return Observable.mergeDelayError(
            firstCall()
                .map { ResourceState.success(it) }
                .onErrorResumeNext { error: Throwable ->
                    Observable.just(
                        ResourceState.error(
                            handleError(error),
                            null
                        )
                    )
                },
            secondCall()
                .map { ResourceState.success(it) }
                .doAfterNext {
                    it.data?.let { newData -> saveSecondCallData(newData) }
                }
                .onErrorResumeNext { error: Throwable ->
                    Observable.just(
                        ResourceState.error(
                            handleError(error),
                            null
                        )
                    )
                }
                .delay(100, TimeUnit.MILLISECONDS)
        )
    }

    /**
     * Wraps the fetched data with Observable<ResourceState> for appropriate ViewModel provision.
     *
     * When fetching the data from an API, provide a saveCallData() to locally store the fetched
     * data (optionally). Otherwise, use just call().
     *
     * Optionally, provide a performOnError() definition if you would like to perform certain actions
     * in case and when the call() fails (e.g. provide local data only in case the remote call fails).
     */
    fun <T> oneSideCall(
        call: () -> Observable<T>,
        saveCallData: ((data: T) -> Unit)? = null,
        performOnError: () -> Observable<T> = { Observable.empty() }
    ): Observable<ResourceState<T>> {
        return call()
            .map { ResourceState.success(it) }
            .doAfterNext {
                if (saveCallData != null) {
                    it.data?.let { newData -> saveCallData(newData) }
                }
            }
            .onErrorResumeNext { error: Throwable ->
                Observable.mergeDelayError(
                    performOnError()
                        .map { ResourceState.success(it) }
                        .onErrorResumeNext { subError: Throwable ->
                            Observable.just(
                                ResourceState.error(
                                    handleError(subError),
                                    null
                                )
                            )
                        }
                        .delay(100, TimeUnit.MILLISECONDS),
                    Observable.just(
                        ResourceState.error(
                            handleError(error),
                            null
                        )
                    )
                )
            }
    }

    // Processes the error and provides the user with the error message
    private fun handleError(e: Throwable): BaseError {
        e.printStackTrace()
        if (e is RetrofitException) {
            val errorCode = e.response?.code() ?: ResponseCodes.UNDEFINED.code
            return if (e.processNetworkError()) {
                BaseError(BaseError.networkErrorMessage, errorCode)
            } else {
                val errorBodyString =
                    e.response?.errorBody()?.string() ?: BaseError.emptyErrorBodyMessage
                e.response?.errorBody()?.close()
                try {
                    val generalError =
                        Gson().fromJson(errorBodyString, ApiInterface.GeneralError::class.java)
                    BaseError(generalError.message, errorCode)
                } catch (e1: JsonSyntaxException) {
                    Log.i("BaseRepo", "Exception GeneralError: " + e1.message, e1)
                    BaseError(BaseError.baseErrorMessage)
                }
            }
        } else {
            return BaseError(BaseError.localErrorMessage)
        }
    }
}