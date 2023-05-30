package com.sentigo.bangkit.sentigoapp.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_table")
@Parcelize
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = false)
    @field:ColumnInfo("id")
    val id: Int,

    @field:ColumnInfo("name")
    val name: String,

    @field:ColumnInfo("rating")
    val rating: Double,

    @field:ColumnInfo("city")
    val city: String,

    @field:ColumnInfo("img")
    val img: String,

    @field:ColumnInfo("favorite")
    var isFavorite: Boolean

) : Parcelable
