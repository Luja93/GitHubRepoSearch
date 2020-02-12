package com.luja93.githubreposearch.common.mvvm

import androidx.lifecycle.Observer
import com.luja93.githubreposearch.common.mvvm.basemodels.BaseError
import com.luja93.githubreposearch.common.mvvm.basemodels.ResourceState
import com.luja93.githubreposearch.common.mvvm.basemodels.Status

/**
 * Created by lleopoldovic on 02/10/2019.
 */

class ResourceStateObserver<T>(
    private val view: BaseView,
    private val onSuccess: (data: T?) -> Unit,
    private val onLoading: (isLoading: Boolean) -> Unit = DEFAULT_LOADING_ACTION,
    private val onError: (baseError: BaseError) -> Unit = DEFAULT_ERROR_ACTION
) : Observer<ResourceState<T>> {

    companion object {
        private val DEFAULT_ERROR_ACTION: (baseError: BaseError) -> Unit = {}
        private val DEFAULT_LOADING_ACTION: (isLoading: Boolean) -> Unit = {}
    }

    override fun onChanged(t: ResourceState<T>?) {
        // Loading state handler:
        onLoadingActionCheck(onLoading, isLoading = t?.status == Status.LOADING)

        if (t?.status == Status.ERROR) {
            // Error state handler
            onErrorActionCheck(
                onError,
                baseError = t.error ?: BaseError(
                    errorMessage = BaseError.baseErrorMessage
                )
            )
            // If a response code needs to be handled specifically, add the new case here
            when (t.error?.errorCode) {
                /* not defined */
            }
        } else if (t?.status == Status.SUCCESS) {
            // Success state handler
            onSuccess(t.data)
        }
    }

    private fun onLoadingActionCheck(
        loadingAction: (isLoading: Boolean) -> Unit = DEFAULT_LOADING_ACTION,
        isLoading: Boolean
    ) {
        if (loadingAction === DEFAULT_LOADING_ACTION) {
            // A lambda is NOT defined - use the default value
            view.showProgressCircle(isLoading)
        } else {
            // A lambda is defined - no default value used
            onLoading(isLoading)
        }
    }

    private fun onErrorActionCheck(
        errorAction: (baseError: BaseError) -> Unit = DEFAULT_ERROR_ACTION,
        baseError: BaseError
    ) {
        if (errorAction === DEFAULT_ERROR_ACTION) {
            // A lambda is NOT defined - use the default value
            view.showError(baseError.errorMessage)
        } else {
            // A lambda is defined - no default value used
            onError(baseError)
        }
    }
}