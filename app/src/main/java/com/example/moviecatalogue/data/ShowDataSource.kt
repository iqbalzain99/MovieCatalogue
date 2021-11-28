package com.example.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.moviecatalogue.data.source.local.entity.FavoriteShowItems
import com.example.moviecatalogue.data.source.remote.response.MovieDetailResponse
import com.example.moviecatalogue.data.source.remote.response.TvDetailResponse

interface ShowDataSource {

    fun getListMovie(): LiveData<PagingData<FavoriteShowItems>>

    fun getListMovie(query: String): LiveData<PagingData<FavoriteShowItems>>

    fun getListTv(): LiveData<PagingData<FavoriteShowItems>>

    fun getListTv(query: String): LiveData<PagingData<FavoriteShowItems>>

    fun getDetailMovie(id: Int): LiveData<MovieDetailResponse>

    fun getDetailTv(id: Int): LiveData<TvDetailResponse>

    fun insertFavorite(favorite : FavoriteShowItems)

    fun deleteFavorite(favorite : FavoriteShowItems)

    fun getMovieFavoritesFromDb() : LiveData<PagingData<FavoriteShowItems>>

    fun getTvFavoritesFromDb() : LiveData<PagingData<FavoriteShowItems>>

    fun getSelectedFavorite(id: String, type: Int) : LiveData<List<FavoriteShowItems>>
}