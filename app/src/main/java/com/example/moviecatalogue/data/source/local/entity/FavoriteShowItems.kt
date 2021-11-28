package com.example.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FavoriteShowItems(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val _id: Int = 0,

    @ColumnInfo(name = "type")
    val type: Int,

    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "category")
    val category: String,

    @ColumnInfo(name = "year")
    val year: String,

    @ColumnInfo(name = "rating")
    val rating: String,

    @ColumnInfo(name = "imagePath")
    val imagePath: String
) : Parcelable