package com.luja93.githubreposearch.common.mvvm.basemodels

/**
 * Created by lleopoldovic on 23/09/2019.
 *
 *
 * Base class to wrap each database/api provision with.
 *
 * Status values are used by view (activity/fragment) to indicate the fetch status of a request,
 * where Resource is commonly wrapped with a MutableLiveData property of a ViewModel.
 */

data class ResourceState<out T>(val status: Status, val data: T?, val error: BaseError?) {
    companion object {
        fun <T> loading(data: T? = null): ResourceState<T> {
            return ResourceState(Status.LOADING, data, null)
        }

        fun <T> success(data: T?): ResourceState<T> {
            return ResourceState(Status.SUCCESS, data, null)
        }

        fun <T> error(baseError: BaseError, data: T? = null): ResourceState<T> {
            return ResourceState(Status.ERROR, data, baseError)
        }
    }
}
