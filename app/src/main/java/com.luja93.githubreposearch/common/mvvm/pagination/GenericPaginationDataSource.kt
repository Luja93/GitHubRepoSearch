package com.luja93.githubreposearch.common.mvvm.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.luja93.githubreposearch.common.mvvm.basemodels.BaseError
import com.luja93.githubreposearch.common.mvvm.basemodels.ResourceState
import com.luja93.githubreposearch.common.mvvm.basemodels.Status
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

/**
 * Created by lleopoldovic on 23/01/2020.
 *
 * Use this class as the DataSource for [GenericPaginationDataSourceFactory].
 */

class GenericPaginationDataSource<T, R>(
    private var params: GenericPaginationParams<R>,
    private var call: (params: GenericPaginationParams<R>) -> Observable<ResourceState<List<T>>>,
    private var compositeDisposable: CompositeDisposable,
    private var instantLoadInitial: Boolean,
    private var shouldClear: Boolean,
    private var fetchState: MutableLiveData<ResourceState<T>>
) : PageKeyedDataSource<Int, T>() {

    private var retryCompletable: Completable? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        val previousPageNo =
            if (this.params.pageNumber > this.params.apiFirstPageNumber) this.params.pageNumber - 1
            else null
        val nextPageNo = this.params.pageNumber + 1

        if (!instantLoadInitial) {
            callback.onResult(emptyList(), previousPageNo, nextPageNo - 1)
            fetchState.postValue(ResourceState.idle())
            return
        }

        if (shouldClear) {
            callback.onResult(emptyList(), previousPageNo, nextPageNo - 1)
            fetchState.postValue(ResourceState.success(null))
            return
        }

        fetchState.postValue(ResourceState.loading())

        compositeDisposable.add(
            call(this.params)
                .subscribe({
                    when (it.status) {
                        // The fetch request status (API/database)
                        Status.SUCCESS -> {
                            callback.onResult(it.data ?: emptyList(), previousPageNo, nextPageNo)
                            fetchState.postValue(ResourceState.success(null))
                        }
                        Status.ERROR -> {
                            it.data?.let { data ->
                                // In case a network error occurred, still show the database data (if there is any)
                                callback.onResult(data, previousPageNo, nextPageNo)
                            }
                            setError(it, Action { loadInitial(params, callback) })
                        }
                    }
                }, {
                    // An error related to this subscription occurred
                    it.printStackTrace()
                })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        val adjacentPageNo = params.key + 1

        if (this.params.loadAfter) {
            fetchState.postValue(ResourceState.loading())

            compositeDisposable.add(
                call(this.params.apply { pageNumber = params.key })
                    .subscribe({
                        when (it.status) {
                            // The fetch request status (API/database)
                            Status.SUCCESS -> {
                                callback.onResult(it.data ?: emptyList(), adjacentPageNo)
                                fetchState.postValue(ResourceState.success(null))
                            }
                            Status.ERROR -> {
                                it.data?.let { data ->
                                    // In case a network error occurred, still show the database data (if there is any)
                                    callback.onResult(data, adjacentPageNo)
                                }
                                setError(it, Action { loadAfter(params, callback) })
                            }
                        }
                    }, {
                        // An error related to this subscription occurred
                        it.printStackTrace()
                    })
            )
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        val adjacentPageNo =
            if (params.key - 1 < this.params.apiFirstPageNumber) null else params.key - 1

        if (this.params.loadBefore) {
            fetchState.postValue(ResourceState.loading())

            compositeDisposable.add(
                call(this.params.apply { pageNumber = params.key })
                    .subscribe({
                        when (it.status) {
                            // The fetch request status (API/database)
                            Status.SUCCESS -> {
                                callback.onResult(it.data ?: emptyList(), adjacentPageNo)
                                fetchState.postValue(ResourceState.success(null))
                            }
                            Status.ERROR -> {
                                it.data?.let { data ->
                                    // In case a network error occurred, still show the database data (if there is any)
                                    callback.onResult(data, adjacentPageNo)
                                }
                                setError(it, Action { loadBefore(params, callback) })
                            }
                        }
                    }, {
                        // An error related to this subscription occurred
                        it.printStackTrace()
                    })
            )
        }
    }


    private fun setError(data: ResourceState<List<T>>, action: Action?) {
        fetchState.postValue(
            ResourceState.error(
                data.error ?: BaseError(
                    errorMessage = BaseError.baseErrorMessage
                )
            )
        )
        setRetryAction(action)
    }

    private fun setRetryAction(action: Action?) {
        retryCompletable = action?.let { Completable.fromAction(it) }
    }

    /**
     * This method is exposed to be used by [GenericPaginationDataSourceFactory] to retry the loading of a failed page
     * (either initial page, next page, or before page) on user's demand.
     */
    fun retry() {
        retryCompletable?.let {
            compositeDisposable.add(
                it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            )
        }
    }
}