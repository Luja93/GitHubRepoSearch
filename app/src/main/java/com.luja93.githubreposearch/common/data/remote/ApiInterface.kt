package com.luja93.githubreposearch.common.data.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.luja93.githubreposearch.githubreposearch.model.User
import com.luja93.githubreposearch.githubreposearch.model.api.SearchReposResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by lleopoldovic on 06/12/2019.
 */

interface ApiInterface {

    @GET("search/repositories")
    fun searchRepositories(
        @Query("page") pageNumber: Int,
        @Query("per_page") pageSize: Int,
        @Query("q") query: String,
        @Query("sort") sort: String): Observable<SearchReposResponse>

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Observable<User>


    // Errors
    class GeneralError {
        @Expose
        @SerializedName("message")
        var message: String = ""
    }

}


