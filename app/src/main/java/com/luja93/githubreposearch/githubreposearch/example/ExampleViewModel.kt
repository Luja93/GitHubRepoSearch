package com.luja93.githubreposearch.githubreposearch.example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.luja93.githubreposearch.common.mvvm.BaseViewModel
import com.luja93.githubreposearch.common.mvvm.basemodels.ResourceState
import com.luja93.githubreposearch.common.scheduler.SchedulerProvider
import com.luja93.githubreposearch.githubreposearch.model.Repo
import com.luja93.githubreposearch.githubreposearch.repository.repo.RepoRepo
import javax.inject.Inject

class ExampleViewModel @Inject constructor(
    schedulers: SchedulerProvider,
    private val repoRepo: RepoRepo
) : BaseViewModel(schedulers) {

    private val _repositories = MutableLiveData<ResourceState<List<Repo>>>()
    val repositories: LiveData<ResourceState<List<Repo>>> = _repositories

    fun searchRepositories(query: String) {
        observableCall(_repositories, {
            repoRepo.getRepositories(query, Repo.Sorting.Undefined)
        })
    }
}