package com.luja93.githubreposearch.githubreposearch.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.parceler.Parcel

/**
 * Created by lleopoldovic on 06/12/2019.
 */

@Parcel
@Entity
data class Owner(
    @PrimaryKey
    @ColumnInfo(name = "ownerId")
    @SerializedName("id")
    val id: Long = 0,
    @SerializedName("login")
    val username: String = "",
    @SerializedName("avatar_url")
    val avatarUrl: String = "",
    @ColumnInfo(name = "ownerUrl")
    @SerializedName("html_url")
    val url: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("url")
    val _detailsUrl: String = ""
) {
}