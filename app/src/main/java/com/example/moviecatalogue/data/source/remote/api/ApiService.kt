package com.example.moviecatalogue.data.source.remote.api

import com.example.moviecatalogue.BuildConfig
import com.example.moviecatalogue.data.source.remote.response.MovieDetailResponse
import com.example.moviecatalogue.data.source.remote.response.MovieResponse
import com.example.moviecatalogue.data.source.remote.response.TvDetailResponse
import com.example.moviecatalogue.data.source.remote.response.TvResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("3/discover/tv?api_key=${BuildConfig.KEY}")
    fun getTvDiscover(): Call<TvResponse>

    @GET("3/discover/movie?api_key=${BuildConfig.KEY}")
    fun getMovieDiscover(): Call<MovieResponse>

    @GET("3/tv/{id}?api_key=${BuildConfig.KEY}")
    fun getTvDetail(
        @Path("id") id: Int
    ): Call<TvDetailResponse>

    @GET("3/movie/{id}?api_key=${BuildConfig.KEY}")
    fun getMovieDetail(
        @Path("id") id: Int
    ): Call<MovieDetailResponse>

    // Search

    @GET("3/search/movie?api_key=${BuildConfig.KEY}")
    fun getSearchMovie(
        @Query("query") query: String
    ): Call<MovieResponse>

    @GET("3/search/tv?api_key=${BuildConfig.KEY}")
    fun getSearchTv(
        @Query("query") query: String
    ): Call<TvResponse>
}