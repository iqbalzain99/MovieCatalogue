package com.example.moviecatalogue.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatalogue.data.ShowRepository
import com.example.moviecatalogue.data.source.local.entity.FavoriteShowItems
import com.example.moviecatalogue.data.source.remote.response.MovieDetailResponse
import com.example.moviecatalogue.data.source.remote.response.TvDetailResponse

class DetailViewModel (private val repository: ShowRepository) : ViewModel() {
    fun insert(favorite: FavoriteShowItems) = repository.insertFavorite(favorite)

    fun delete(favorite: FavoriteShowItems) = repository.deleteFavorite(favorite)

    fun getTvShow(id: Int): LiveData<TvDetailResponse> = repository.getDetailTv(id)

    fun getSelectedFavoriteTv(id: String): LiveData<List<FavoriteShowItems>> =
        repository.getSelectedFavorite(id, 1)

    fun getMovies(id: Int): LiveData<MovieDetailResponse> = repository.getDetailMovie(id)

    fun getSelectedFavoriteMovie(id: String): LiveData<List<FavoriteShowItems>> =
        repository.getSelectedFavorite(id, 0)
}