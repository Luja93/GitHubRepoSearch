package com.luja93.githubreposearch.githubreposearch.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.luja93.githubreposearch.common.mvvm.basemodels.ResourceState
import com.luja93.githubreposearch.common.mvvm.pagination.BasePaginationViewModel
import com.luja93.githubreposearch.common.mvvm.pagination.GenericPaginationDataSourceFactory
import com.luja93.githubreposearch.common.scheduler.SchedulerProvider
import com.luja93.githubreposearch.githubreposearch.AppConstants.BLANK_SEARCH
import com.luja93.githubreposearch.githubreposearch.model.Repo
import com.luja93.githubreposearch.githubreposearch.model.pagination.ReposPaginationParams
import com.luja93.githubreposearch.githubreposearch.repository.repo.RepoRepo
import javax.inject.Inject

/**
 * Created by lleopoldovic on 06/12/2019.
 */

class SearchReposViewModel @Inject constructor(
    schedulers: SchedulerProvider,
    private val repoRepo: RepoRepo
) : BasePaginationViewModel(schedulers) {

    // Lateinits
    private var reposDataSourceFactory: GenericPaginationDataSourceFactory<Repo, ReposPaginationParams>
    private var reposPagedListConfig: PagedList.Config
    var repos: LiveData<ResourceState<PagedList<Repo>>>

    // LiveData
    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query
    private val _sorting = MutableLiveData<Repo.Sorting>()
    val sorting: LiveData<Repo.Sorting> = _sorting
    private val _totalCount = MutableLiveData<Long>()
    val totalCount: LiveData<Long> = _totalCount

    // Other
    private var reposPaginationParams = ReposPaginationParams()


    init {
        // Pagination setup
        reposDataSourceFactory = generatePaginationDataSourceFactory(
            paginationParams = reposPaginationParams,
            // We don't have the search query yet, so don't fire the call, but initialize the dataSource
            instantLoadInitial = false,
            call = {
                repoRepo.getRepositories(it as ReposPaginationParams).mapData { data ->
                    _totalCount.postValue(data.totalCount)
                    data.items
                }
            }
        )
        reposPagedListConfig = generateDefaultPaginationConfig(
            paginationParams = reposPaginationParams
        )
        repos = buildLivePagedListWithState(
            reposDataSourceFactory, reposPagedListConfig
        )

        // Other
        _query.value = ""
        _sorting.value = Repo.Sorting.Default
        _totalCount.value = BLANK_SEARCH
    }


    fun searchRepositories(query: String, searchIfSameQuery: Boolean = false) {
        val queryTrimmed = query.trim()
        // If user entered a repo/user details screen and then returned to repo listing,
        // don't refresh the list
        if (_query.value == queryTrimmed && !searchIfSameQuery) {
            return
        } else {
            _query.value = queryTrimmed
        }

        if (queryTrimmed.isBlank()) {
            _totalCount.value = BLANK_SEARCH
            reposDataSourceFactory.clear()
        } else {
            performSearch(queryTrimmed)
        }
    }

    fun setSorting(sorting: Repo.Sorting) {
        _sorting.value = sorting
        searchRepositories(_query.value ?: "", true)
    }

    private fun performSearch(query: String) {
        reposPaginationParams.apply {
            this.query = query
            this.pageNumber = this.apiFirstPageNumber
            this.sort = _sorting.value ?: Repo.Sorting.Default
        }

        reposDataSourceFactory.reInit(
            reposPaginationParams
        )
    }
}