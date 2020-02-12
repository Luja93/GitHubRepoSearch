package com.luja93.githubreposearch.githubreposearch.repository.repo

import com.luja93.githubreposearch.common.mvvm.basemodels.ResourceState
import com.luja93.githubreposearch.githubreposearch.model.api.SearchReposResponse
import com.luja93.githubreposearch.githubreposearch.model.pagination.ReposPaginationParams
import io.reactivex.Observable

/**
 * Created by lleopoldovic on 06/12/2019.
 */

interface RepoRepo /* Unfortunate naming */ {

    fun getRepositories(params: ReposPaginationParams): Observable<ResourceState<SearchReposResponse>>

}