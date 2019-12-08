package com.luja93.githubreposearch.githubreposearch.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.luja93.githubreposearch.common.mvvm.BaseViewModel
import com.luja93.githubreposearch.common.mvvm.basemodels.ResourceState
import com.luja93.githubreposearch.common.scheduler.SchedulerProvider
import com.luja93.githubreposearch.githubreposearch.AppConstants.BLANK_SEARCH
import com.luja93.githubreposearch.githubreposearch.model.Repo
import com.luja93.githubreposearch.githubreposearch.model.api.SearchReposResponse
import com.luja93.githubreposearch.githubreposearch.repository.repo.RepoRepo
import javax.inject.Inject

/**
 * Created by lleopoldovic on 06/12/2019.
 */

class SearchReposViewModel @Inject constructor(
    schedulers: SchedulerProvider,
    private val repoRepo: RepoRepo
) : BaseViewModel(schedulers) {

    private val _repositories = MutableLiveData<ResourceState<SearchReposResponse>>()
    val repositories: LiveData<ResourceState<SearchReposResponse>> = _repositories
    private val _query = MutableLiveData<ResourceState<String>>()
    val query: LiveData<ResourceState<String>> = _query
    private val _sorting = MutableLiveData<ResourceState<Repo.Sorting>>()
    val sorting: LiveData<ResourceState<Repo.Sorting>> = _sorting

    init {
        _query.value = ResourceState.success("")
        _sorting.value = ResourceState.success(Repo.Sorting.Default)
    }

    fun searchRepositories(query: String, searchIfSameQuery: Boolean = false) {
        // If user entered a repo/user details screen and then returned to repo listing,
        // don't refresh the list
        if (_query.value?.data == query && !searchIfSameQuery) {
            return
        } else {
            _query.value = ResourceState.success(query)
        }

        if (query.trim().isBlank()) {
            _repositories.value =
                ResourceState.success(SearchReposResponse(BLANK_SEARCH, emptyList()))
        } else {
            observableCall(_repositories, {
                repoRepo.getRepositories(query, _sorting.value?.data ?: Repo.Sorting.Default)
            })
        }
    }

    fun setSortingOption(position: Int) {
        _sorting.value = ResourceState.success(Repo.Sorting.values()[position])
    }
}