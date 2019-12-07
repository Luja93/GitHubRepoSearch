package com.luja93.githubreposearch.githubreposearch.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.luja93.githubreposearch.utils.DateTimeUtils

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
    val followingCount: Int,
    @SerializedName("created_at")
    val _createdAtTimeStamp: String,
    @SerializedName("updated_at")
    val _updatedAtTimeStamp: String,
    @SerializedName("email")
    val email: String?,
    @SerializedName("location")
    val location: String?
) {

    val createdAt: String get() = DateTimeUtils().getRepoDateString(_createdAtTimeStamp)
    val updatedAt: String get() = DateTimeUtils().getRepoDateString(_updatedAtTimeStamp)
}