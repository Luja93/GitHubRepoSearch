package com.luja93.githubreposearch.githubreposearch.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by lleopoldovic on 06/12/2019.
 */

@Entity
data class User(
    @PrimaryKey
    @SerializedName("id")
    val id: Long,
    @SerializedName("login")
    val username: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("html_url")
    val url: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("bio")
    val bio: String?,
    @SerializedName("public_repos")
    val publicReposCount: Int,
    @SerializedName("public_gists")
    val publicGistsCount: Int,
    @SerializedName("followers")
    val followersCount: Long,
    @SerializedName("following")
    val followingCount: Int
) {
}