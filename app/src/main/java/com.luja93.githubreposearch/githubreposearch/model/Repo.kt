package com.luja93.githubreposearch.githubreposearch.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.luja93.githubreposearch.utils.DateTimeUtils
import org.parceler.Parcel

/**
 * Created by lleopoldovic on 06/12/2019.
 */

@Parcel
@Entity
data class Repo(
    @PrimaryKey
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("full_name")
    val fullName: String = "",
    @SerializedName("private")
    val isPrivate: Boolean = false,
    @Embedded
    @SerializedName("owner")
    val owner: Owner = Owner(),
    @SerializedName("html_url")
    val url: String = "",
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("created_at")
    val _createdAtTimeStamp: String = "",
    @SerializedName("updated_at")
    val _updatedAtTimeStamp: String = "",
    @SerializedName("watchers_count")
    val watcherCount: Long = 0,
    @SerializedName("forks_count")
    val forksCount: Long = 0,
    @SerializedName("open_issues_count")
    val openIssuesCount: Long = 0,
    @SerializedName("language")
    val language: String? = null
) {

    enum class Sorting(val value: String) {
        Default(""),
        Forks("forks"),
        Stars("stars"),
        Issues("help-wanted-issues")
    }

    val createdAt: String get() = DateTimeUtils().getRepoDateString(_createdAtTimeStamp)
    val updatedAt: String get() = DateTimeUtils().getRepoDateString(_updatedAtTimeStamp)

}