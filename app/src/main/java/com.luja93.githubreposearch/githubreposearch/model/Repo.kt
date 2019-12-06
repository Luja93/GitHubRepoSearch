package com.luja93.githubreposearch.githubreposearch.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Repo(
    @PrimaryKey
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("private")
    val isPrivate: Boolean,
    @Embedded
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("html_url")
    val url: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("created_at")
    val _createdAt: String,
    @SerializedName("updated_at")
    val _updatedAt: String,
    @SerializedName("watchers_count")
    val watcherCount: Long,
    @SerializedName("forks_count")
    val forksCount: Long,
    @SerializedName("open_issues_count")
    val openIssuesCount: Long,
    @SerializedName("language")
    val language: String?
) {

    enum class Sorting(val value: String) {
        Undefined(""),
        Forks("forks"),
        Watchers("stars"),
        Issues("help-wanted-issues")
    }

}