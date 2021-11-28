package com.example.moviecatalogue.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.moviecatalogue.data.ShowRepository
import com.example.moviecatalogue.data.source.local.entity.FavoriteShowItems

class FavoriteViewModel (private val repository: ShowRepository) : ViewModel() {
    fun getMovies(): LiveData<PagingData<FavoriteShowItems>> = repository.getMovieFavoritesFromDb()

    fun getTvShow(): LiveData<PagingData<FavoriteShowItems>> = repository.getTvFavoritesFromDb()
}