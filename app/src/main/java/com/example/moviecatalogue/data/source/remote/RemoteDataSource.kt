package com.example.moviecatalogue.data.source.remote

import android.util.Log
import androidx.paging.PagingData
import com.example.moviecatalogue.data.source.local.entity.FavoriteShowItems
import com.example.moviecatalogue.data.source.remote.api.ApiConfig
import com.example.moviecatalogue.data.source.remote.response.MovieDetailResponse
import com.example.moviecatalogue.data.source.remote.response.MovieResponse
import com.example.moviecatalogue.data.source.remote.response.TvDetailResponse
import com.example.moviecatalogue.data.source.remote.response.TvResponse
import com.example.moviecatalogue.utils.EspressoIdlingResource
import com.example.moviecatalogue.utils.JsonHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class RemoteDataSource private constructor( private val jsonHelper: JsonHelper) {


    companion object {

        private const val TAG = "RemoteDataSource"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(jsonHelper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(jsonHelper).apply { instance = this }
            }
    }

    fun getListMovie(callback : LoadMovieListCallback ) {
        EspressoIdlingResource.increment()
        val client = ApiConfig.getApiService().getMovieDiscover()

        client.enqueue(object: Callback<MovieResponse> {

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {

                if (response.isSuccessful) {
                    val listData = ArrayList<FavoriteShowItems>()
                    for (movieSimple in response.body()?.results!!) {
                        val year =
                            if (movieSimple?.releaseDate?.length!! > 4) movieSimple.releaseDate.take(
                                4
                            ) else "-"
                        var category = ""
                        for (item in movieSimple.genreIds!!) {
                            category += (jsonHelper.getMovieGenreName(item!!))
                            if (item != movieSimple.genreIds[movieSimple.genreIds.size - 1]) {
                                category += ", "
                            }
                        }
                        listData.add(
                            FavoriteShowItems(
                                type = 0,
                                id = movieSimple.id.toString(),
                                title = movieSimple.title ?: "",
                                category = category,
                                year = year,
                                rating = movieSimple.voteAverage.toString(),
                                imagePath = movieSimple.posterPath ?: ""
                            )
                        )
                    }
                    callback.onAllMovieReceived(PagingData.from(listData))
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getListMovie(query: String, callback : LoadMovieListCallback ) {
        EspressoIdlingResource.increment()
        val client = ApiConfig.getApiService().getSearchMovie(query)

        client.enqueue(object: Callback<MovieResponse> {

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {

                if (response.isSuccessful) {
                    val listData = ArrayList<FavoriteShowItems>()
                    for (movieSimple in response.body()?.results!!) {
                        val year =
                            if (movieSimple?.releaseDate?.length!! > 4) movieSimple.releaseDate.take(
                                4
                            ) else "-"
                        var category = ""
                        for (item in movieSimple.genreIds!!) {
                            category += (jsonHelper.getMovieGenreName(item!!))
                            if (item != movieSimple.genreIds[movieSimple.genreIds.size - 1]) {
                                category += ", "
                            }
                        }
                        listData.add(
                            FavoriteShowItems(
                                type = 0,
                                id = movieSimple.id.toString(),
                                title = movieSimple.title ?: "",
                                category = category,
                                year = year,
                                rating = movieSimple.voteAverage.toString(),
                                imagePath = movieSimple.posterPath ?: ""
                            )
                        )
                    }
                    callback.onAllMovieReceived(PagingData.from(listData))
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


    fun getListTv( callback : LoadTvListCallback ){
        EspressoIdlingResource.increment()
        val client = ApiConfig.getApiService().getTvDiscover()


        client.enqueue(object: Callback<TvResponse> {

            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                if (response.isSuccessful) {
                    val listData = ArrayList<FavoriteShowItems>()

                    for (movieSimple in response.body()?.results!!) {
                        val year = if (movieSimple?.firstAirDate?.length!! > 4) movieSimple.firstAirDate.take(4) else "-"
                        var category = ""
                        for(item in movieSimple.genreIds!!) {
                            category += (jsonHelper.getTvGenreName(item!!))
                            if (item != movieSimple.genreIds[movieSimple.genreIds.size - 1]){
                                category += ", "
                            }
                        }
                        listData.add(FavoriteShowItems(
                            type = 1,
                            id = movieSimple.id.toString(),
                            title = movieSimple.name ?: "",
                            category = category,
                            year = year,
                            rating = movieSimple.voteAverage.toString(),
                            imagePath = movieSimple.posterPath ?: ""
                        ))
                    }

                    callback.onAllTvReceived(PagingData.from(listData))
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getListTv(query: String, callback : LoadTvListCallback ){
        EspressoIdlingResource.increment()
        val client = ApiConfig.getApiService().getSearchTv(query)


        client.enqueue(object: Callback<TvResponse> {

            override fun onResponse(call: Call<TvResponse>, response: Response<TvResponse>) {
                if (response.isSuccessful) {
                    val listData = ArrayList<FavoriteShowItems>()

                    for (movieSimple in response.body()?.results!!) {
                        val year = if (movieSimple?.firstAirDate?.length!! > 4) movieSimple.firstAirDate.take(4) else "-"
                        var category = ""
                        for(item in movieSimple.genreIds!!) {
                            category += (jsonHelper.getTvGenreName(item!!))
                            if (item != movieSimple.genreIds[movieSimple.genreIds.size - 1]){
                                category += ", "
                            }
                        }
                        listData.add(FavoriteShowItems(
                            type = 1,
                            id = movieSimple.id.toString(),
                            title = movieSimple.name ?: "",
                            category = category,
                            year = year,
                            rating = movieSimple.voteAverage.toString(),
                            imagePath = movieSimple.posterPath ?: ""
                        ))
                    }

                    callback.onAllTvReceived(PagingData.from(listData))
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDetailMovie(id: Int, callback: LoadDetailMovie) {
        EspressoIdlingResource.increment()
        val client = ApiConfig.getApiService().getMovieDetail(id)
        client.enqueue(object: Callback<MovieDetailResponse> {

            override fun onResponse(call: Call<MovieDetailResponse>, response: Response<MovieDetailResponse>) {
                if (response.isSuccessful) {
                    callback.onDetailMovieReceived(response.body() as MovieDetailResponse)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDetailTv(id: Int, callback: LoadDetailTv) {
        EspressoIdlingResource.increment()
        val client = ApiConfig.getApiService().getTvDetail(id)
        client.enqueue(object: Callback<TvDetailResponse> {

            override fun onResponse(call: Call<TvDetailResponse>, response: Response<TvDetailResponse>) {
                if (response.isSuccessful) {
                    callback.onDetailTvReceived(response.body() as TvDetailResponse)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<TvDetailResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    interface LoadMovieListCallback {
        fun onAllMovieReceived(movieListResponse: PagingData<FavoriteShowItems>)
    }

    interface LoadTvListCallback {
        fun onAllTvReceived(tvListResponse: PagingData<FavoriteShowItems>)
    }

    interface LoadDetailMovie {
        fun onDetailMovieReceived(detailMovie: MovieDetailResponse)
    }

    interface LoadDetailTv {
        fun onDetailTvReceived(detailTv: TvDetailResponse)
    }

}