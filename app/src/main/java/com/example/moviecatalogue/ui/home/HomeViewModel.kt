package com.example.moviecatalogue.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.moviecatalogue.data.ShowRepository
import com.example.moviecatalogue.data.source.local.entity.FavoriteShowItems

class HomeViewModel (private val repository: ShowRepository) : ViewModel() {

    private lateinit var  _movie : LiveData<PagingData<FavoriteShowItems>>
    val movie : LiveData<PagingData<FavoriteShowItems>> get() = _movie

    private lateinit var  _tv : LiveData<PagingData<FavoriteShowItems>>
    val tv : LiveData<PagingData<FavoriteShowItems>> get() = _tv

    fun getMovies(){
        _movie = repository.getListMovie()
    }

    fun getMovies(query: String){
        _movie = repository.getListMovie(query)
    }

    fun getTvShow(){
        _tv = repository.getListTv()
    }

    fun getTvShow(query: String){
        _tv = repository.getListTv(query)
    }
}