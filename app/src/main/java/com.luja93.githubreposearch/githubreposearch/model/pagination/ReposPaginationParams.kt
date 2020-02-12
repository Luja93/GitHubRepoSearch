package com.luja93.githubreposearch.githubreposearch.model.pagination

import com.luja93.githubreposearch.common.mvvm.pagination.GenericPaginationParams
import com.luja93.githubreposearch.githubreposearch.model.Repo

data class ReposPaginationParams(
    var query: String = "",
    var sort: Repo.Sorting = Repo.Sorting.Default
) : GenericPaginationParams<ReposPaginationParams>()