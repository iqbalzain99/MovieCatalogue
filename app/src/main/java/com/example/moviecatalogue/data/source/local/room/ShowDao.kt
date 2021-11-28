package com.example.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.moviecatalogue.data.source.local.entity.FavoriteShowItems

@Dao
interface ShowDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteShow: FavoriteShowItems)

    @Delete
    fun delete(favoriteShow: FavoriteShowItems)

    @Query("SELECT * from favoriteshowitems WHERE type = 0 ORDER BY _id ASC")
    fun getAllMovieFavorite(): List<FavoriteShowItems>

    @Query("SELECT * from favoriteshowitems WHERE type = 1 ORDER BY _id ASC")
    fun getAllTvFavorite(): List<FavoriteShowItems>

    @Query("SELECT * from favoriteshowitems WHERE type = :type AND id = :id")
    fun getSelectedFromDb(id: Int, type: Int): LiveData<List<FavoriteShowItems>>

}