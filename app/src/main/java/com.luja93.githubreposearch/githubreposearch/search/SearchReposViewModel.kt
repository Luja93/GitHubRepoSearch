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

    private var query: String = ""
    private var sorting: Repo.Sorting = Repo.Sorting.Default

    fun searchRepositories(query: String) {
        //if (this.query == query) return else this.query = query

        if (query.trim().isNullOrBlank()) {
            _repositories.value =
                ResourceState.success(SearchReposResponse(BLANK_SEARCH, emptyList()))
        } else {
            observableCall(_repositories, {
                repoRepo.getRepositories(query, sorting)
            })
        }
    }

    fun setSortingOption(position: Int) {
        sorting = Repo.Sorting.values()[position]
    }
}