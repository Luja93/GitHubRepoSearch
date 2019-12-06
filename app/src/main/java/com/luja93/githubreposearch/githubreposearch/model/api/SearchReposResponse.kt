package com.luja93.githubreposearch.githubreposearch.model.api

import com.google.gson.annotations.SerializedName
import com.luja93.githubreposearch.githubreposearch.model.Repo

data class SearchReposResponse(
    @SerializedName("items")
    val items: List<Repo>
) {
}