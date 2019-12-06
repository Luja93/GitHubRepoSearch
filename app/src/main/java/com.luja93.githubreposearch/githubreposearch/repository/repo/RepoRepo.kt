package com.luja93.githubreposearch.githubreposearch.repository.repo

import com.luja93.githubreposearch.common.mvvm.basemodels.ResourceState
import com.luja93.githubreposearch.githubreposearch.model.Repo
import io.reactivex.Observable


interface RepoRepo /* Unfortunate naming */ {

    fun getRepositories(query: String, sort: Repo.Sorting): Observable<ResourceState<List<Repo>>>

}