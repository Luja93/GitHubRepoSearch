package com.luja93.githubreposearch.githubreposearch.model.api

import com.google.gson.annotations.SerializedName
import com.luja93.githubreposearch.githubreposearch.model.Repo

/**
 * Created by lleopoldovic on 06/12/2019.
 */

data class SearchReposResponse(
    @SerializedName("total_count")
    val totalCount: Long,
    @SerializedName("items")
    val items: List<Repo>
) {
}