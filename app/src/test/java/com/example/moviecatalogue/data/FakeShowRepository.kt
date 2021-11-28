package com.example.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.moviecatalogue.data.source.local.LocalDataSource
import com.example.moviecatalogue.data.source.local.entity.FavoriteShowItems
import com.example.moviecatalogue.data.source.remote.RemoteDataSource
import com.example.moviecatalogue.data.source.remote.response.MovieDetailResponse
import com.example.moviecatalogue.data.source.remote.response.TvDetailResponse

class FakeShowRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : ShowDataSource{


    override fun getListMovie(): LiveData<PagingData<FavoriteShowItems>> {

        val result = MutableLiveData<PagingData<FavoriteShowItems>>()
        remoteDataSource.getListMovie(object : RemoteDataSource.LoadMovieListCallback{
            override fun onAllMovieReceived(movieListResponse: PagingData<FavoriteShowItems>) {
                result.postValue(movieListResponse)
            }
        })
        return result
    }

    override fun getListMovie(query: String): LiveData<PagingData<FavoriteShowItems>> {

        val result = MutableLiveData<PagingData<FavoriteShowItems>>()
        remoteDataSource.getListMovie(query, object : RemoteDataSource.LoadMovieListCallback{
            override fun onAllMovieReceived(movieListResponse: PagingData<FavoriteShowItems>) {
                result.postValue(movieListResponse)
            }
        })
        return result
    }

    override fun getListTv(): LiveData<PagingData<FavoriteShowItems>> {
        val result = MutableLiveData<PagingData<FavoriteShowItems>>()
        remoteDataSource.getListTv(object : RemoteDataSource.LoadTvListCallback{
            override fun onAllTvReceived(tvListResponse: PagingData<FavoriteShowItems>) {
                result.postValue(tvListResponse)
            }
        })
        return result
    }

    override fun getListTv(query: String): LiveData<PagingData<FavoriteShowItems>> {
        val result = MutableLiveData<PagingData<FavoriteShowItems>>()
        remoteDataSource.getListTv(query, object : RemoteDataSource.LoadTvListCallback{
            override fun onAllTvReceived(tvListResponse: PagingData<FavoriteShowItems>) {
                result.postValue(tvListResponse)
            }
        })
        return result
    }

    override fun getDetailMovie(id: Int): LiveData<MovieDetailResponse> {
        val result = MutableLiveData<MovieDetailResponse>()
        remoteDataSource.getDetailMovie(id, object : RemoteDataSource.LoadDetailMovie{
            override fun onDetailMovieReceived(detailMovie: MovieDetailResponse) {
                result.postValue(detailMovie)
            }
        })
        return result
    }

    override fun getDetailTv(id: Int): LiveData<TvDetailResponse> {
        val result = MutableLiveData<TvDetailResponse>()
        remoteDataSource.getDetailTv(id, object : RemoteDataSource.LoadDetailTv{
            override fun onDetailTvReceived(detailTv: TvDetailResponse) {
                result.postValue(detailTv)
            }
        })
        return result
    }

    override fun insertFavorite(favorite: FavoriteShowItems) {
        localDataSource.insertFavorite(favorite)
    }

    override fun deleteFavorite(favorite: FavoriteShowItems) {
        localDataSource.deleteFavorite(favorite)
    }

    override fun getMovieFavoritesFromDb(): LiveData<PagingData<FavoriteShowItems>> {
        val result = MutableLiveData<PagingData<FavoriteShowItems>>()
        result.value = localDataSource.getMovieFavorites()
        return result
    }

    override fun getTvFavoritesFromDb(): LiveData<PagingData<FavoriteShowItems>> {
        val result = MutableLiveData<PagingData<FavoriteShowItems>>()
        result.value = localDataSource.getTvFavorites()
        return result
    }

    override fun getSelectedFavorite(id: String, type: Int): LiveData<List<FavoriteShowItems>> {
        return localDataSource.getSelectedFromFavorite(id, type)
    }

}