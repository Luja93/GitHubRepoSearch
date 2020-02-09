package com.luja93.githubreposearch.common.mvvm.pagination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.luja93.githubreposearch.common.mvvm.BaseViewModel
import com.luja93.githubreposearch.common.mvvm.basemodels.BaseError
import com.luja93.githubreposearch.common.mvvm.basemodels.ResourceState
import com.luja93.githubreposearch.common.mvvm.basemodels.Status
import com.luja93.githubreposearch.common.scheduler.SchedulerProvider
import io.reactivex.Observable

/**
 * Created by lleopoldovic on 28/01/2019.
 *
 * Extend this class if your ViewModel WILL implement pagination via [GenericPaginationDataSourceFactory].
 * Otherwise, use [BaseViewModel].
 */

abstract class BasePaginationViewModel(schedulers: SchedulerProvider) : BaseViewModel(schedulers) {

    /**
     * Use this call to build LivePagedList, instead of [LivePagedListBuilder]. This method will wrap the return type
     * of {@link LivePagedListBuilder#build()} with [ResourceState] so you only have to observe the live data which is
     * returned by this call to be able to submit the PagedData to your pagination adapter and also show loading/error
     * states of your database/API call. Make sure to use [ResourceStateObserver] in order to observe the results.
     */
    fun <DataType, PaginationParams> buildLivePagedListWithState(
        dataSourceFactory: GenericPaginationDataSourceFactory<DataType, PaginationParams>,
        paginationConfig: PagedList.Config
    ): LiveData<ResourceState<PagedList<DataType>>> {
        // Input values
        val pagedDataList =
            LivePagedListBuilder<Int, DataType>(dataSourceFactory, paginationConfig).build()
        val fetchState: MutableLiveData<ResourceState<DataType>> = dataSourceFactory.getFetchState()
        // Output value
        val mergedSource = MediatorLiveData<ResourceState<PagedList<DataType>>>()

        mergedSource.addSource(pagedDataList) { pagedList ->
            when (fetchState.value?.status) {
                Status.SUCCESS -> mergedSource.value = ResourceState.success(pagedList)
                Status.LOADING -> mergedSource.value = ResourceState.loading()
                Status.ERROR -> mergedSource.value = ResourceState.error(
                    data = pagedList,
                    baseError = fetchState.value?.error ?: BaseError(
                        errorMessage = BaseError.baseErrorMessage
                    )
                )
            }
        }
        mergedSource.addSource(fetchState) { newFetchState ->
            when (newFetchState.status) {
                Status.SUCCESS -> mergedSource.value = ResourceState.success(pagedDataList.value)
                Status.LOADING -> mergedSource.value = ResourceState.loading()
                Status.ERROR -> mergedSource.value = ResourceState.error(
                    data = pagedDataList.value,
                    baseError = newFetchState.error ?: BaseError(
                        errorMessage = BaseError.baseErrorMessage
                    )
                )
            }
        }

        return mergedSource
    }

    /**
     * Use this method to generate an instance of [GenericPaginationDataSourceFactory].
     */
    fun <DataType, PaginationParams> generatePaginationDataSourceFactory(
        paginationParams: GenericPaginationParams<PaginationParams>,
        call: (params: GenericPaginationParams<PaginationParams>) -> Observable<ResourceState<List<DataType>>>,
        instantLoadInitial: Boolean = true
    ): GenericPaginationDataSourceFactory<DataType, PaginationParams> {
        return GenericPaginationDataSourceFactory(
            params = paginationParams,
            call = call,
            compositeDisposable = compositeDisposable,
            instantLoadInitial = instantLoadInitial
        )
    }

    /**
     * Use this method to generate the default configuration of a PagedList.
     *
     * In most cases, the default PagedList configuration will work since the total count of items is usually not
     * available in remote APIs so placeholders should be disabled and our ih-house practises have not set a larger
     * initial load size hint than @param paginationParams.pageSize * 2.
     */
    fun <PaginationParams> generateDefaultPaginationConfig(
        paginationParams: GenericPaginationParams<PaginationParams>
    ): PagedList.Config {
        return PagedList.Config.Builder()
            .setPageSize(paginationParams.pageSize)
            .setInitialLoadSizeHint(paginationParams.pageSize * 0) // If not set, initialLoadSizeHint equals PAGE_SIZE * 3
            .setEnablePlaceholders(false) // Since the total count is usually not available in remote APIs, placeholders should be disabled.
            .build()
    }
}