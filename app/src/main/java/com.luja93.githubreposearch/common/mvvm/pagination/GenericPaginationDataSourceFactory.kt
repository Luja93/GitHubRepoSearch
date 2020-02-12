package com.luja93.githubreposearch.common.mvvm.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.luja93.githubreposearch.common.mvvm.basemodels.ResourceState
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by lleopoldovic on 23/01/2020.
 *
 * Use this class to create a pagination DataSourceFactory.
 *
 * The generic DataType stands for the type of items which will be fetched for pagination (e.g. FeedItem,
 * EducationArticle etc.)
 * The generic PaginationParams stands for the pagination params class which extends [GenericPaginationParams].
 *
 * @param params Extend [GenericPaginationParams] and provide all the other params which need to be used for fetching
 *               the data (e.g. search query, unique identifiers etc.).
 * @param call A repository function responsible for fetching the pagination data. It should accept @param params as
 *             the argument.
 * @param instantLoadInitial Set this to false if you don't want to fire the @param call immediately with the loadInit()
 *                           method of the [GenericPaginationDataSource] when your ViewModel LiveData is initialized.
 *                           In that case, an empty list is returned.
 *                           Used when a search query must be provided to fetch the data to populate a pagination adapter.
 *                           That way, your LiveData<ResourceState<PagedList<DataType>>> can still be initialized with
 *                           your ViewModel and reInit() can be called when a search query is provided. Considering the
 *                           logic, this param will auto reset to true when calling reInit().
 *                           True by default.
 */

class GenericPaginationDataSourceFactory<DataType, PaginationParams>(
    var params: GenericPaginationParams<PaginationParams>,
    var call: (params: GenericPaginationParams<PaginationParams>) -> Observable<ResourceState<List<DataType>>>,
    var compositeDisposable: CompositeDisposable,
    var instantLoadInitial: Boolean = true
) : DataSource.Factory<Int, DataType>() {

    private var shouldClear: Boolean = false
    private var mInstantLoadInitial = instantLoadInitial
    private var mParams: GenericPaginationParams<PaginationParams> = params
    private var dataSource: GenericPaginationDataSource<DataType, PaginationParams>? = null
    private var fetchState: MutableLiveData<ResourceState<DataType>> =
        MutableLiveData(ResourceState.idle())

    override fun create(): DataSource<Int, DataType> {
        // A new instance of _dataSource has to be created every time create() is called.
        val _dataSource = GenericPaginationDataSource<DataType, PaginationParams>(
            mParams, call, compositeDisposable, mInstantLoadInitial, shouldClear, fetchState
        )
        dataSource = _dataSource
        shouldClear = false

        return _dataSource
    }

    /**
     * When using the Paging Library, it's up to the data layer to notify the other layers of your app when a table or
     * row has become stale. To do so, call invalidate() from the DataSource class that you've chosen for your app.
     *
     * Note: Your app's UI can trigger this data invalidation functionality using a swipe to refresh model.
     */
    fun reInit(params: GenericPaginationParams<PaginationParams>) {
        mParams = params
        mInstantLoadInitial = true
        dataSource?.invalidate()
    }

    /**
     * Clears the items in the pagination adapter (returns an emptyList).
     *
     * If a PaginationAdapter was populated, then hidden because the search query returned no results or because no
     * search query was provided (BLANK_SEARCH), the LiveData<PagedList> used to populate the adapter has to be cleared,
     * otherwise a small glitch, showing a portion of old data will be shown, until the DiffUtil has done its thing to
     * repopulate the adapter.
     *
     * Note: you must NOT set the adapter's RecyclerView visibility to GONE, because the changes won't render. Instead,
     * set it to INVISIBLE is possible.
     */
    fun clear() {
        shouldClear = true
        dataSource?.invalidate()
    }

    /**
     * Use this method to retry the loading of a failed page (either initial page, next page, or before page).
     */
    fun retry() {
        dataSource?.retry()
    }

    /**
     * This method is only used by the [BasePaginationViewModel] to expose the state of the call (success/loading/error)
     * to wrap the result of the pagination call with [ResourceState].
     */
    fun getFetchState(): MutableLiveData<ResourceState<DataType>> {
        return fetchState
    }
}