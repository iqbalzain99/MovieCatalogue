package com.example.moviecatalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.moviecatalogue.data.source.local.LocalDataSource
import com.example.moviecatalogue.data.source.local.entity.FavoriteShowItems
import com.example.moviecatalogue.data.source.remote.RemoteDataSource
import com.example.moviecatalogue.utils.DataDummy
import com.example.moviecatalogue.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class ShowRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val showRepository = FakeShowRepository(remote,local)

    private val movieResponses = DataDummy.generateMoviesNew()
    private val movieId = movieResponses[0].id as Int
    private val tvResponse = DataDummy.generateTvNew()
    private val tvId = tvResponse[0].id as Int
    private val movieDetailResponses = DataDummy.generateDetailMovie()
    private val tvDetailResponse = DataDummy.generateDetailTv()
    private val dataListResponse = DataDummy.generateItems()


    @Test
    fun getListMovie() {
        val data = PagingData.from(dataListResponse)
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadMovieListCallback)
                .onAllMovieReceived(data)
            null
        }.`when`(remote).getListMovie(any())
        val movieEntities = LiveDataTestUtil.getValue(showRepository.getListMovie())
        verify(remote).getListMovie(any())
        assertNotNull(movieEntities)
        assertEquals(data, movieEntities)
    }

    @Test
    fun getListMovieWithQuery() {
        val data = PagingData.from(dataListResponse)
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadMovieListCallback)
                .onAllMovieReceived(data)
            null
        }.`when`(remote).getListMovie(eq("venom"),any())
        val movieEntities = LiveDataTestUtil.getValue(showRepository.getListMovie("venom"))
        verify(remote).getListMovie(eq("venom"),any())
        assertNotNull(movieEntities)
        assertEquals(data, movieEntities)
    }


    @Test
    fun getListTv() {
        val data = PagingData.from(dataListResponse)
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadTvListCallback)
                .onAllTvReceived(data)
            null
        }.`when`(remote).getListTv(any())
        val tvEntities = LiveDataTestUtil.getValue(showRepository.getListTv())
        verify(remote).getListTv(any())
        assertNotNull(tvEntities)
        assertEquals(data, tvEntities)
    }

    @Test
    fun getListTvWithQuery() {
        val data = PagingData.from(dataListResponse)
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadTvListCallback)
                .onAllTvReceived(data)
            null
        }.`when`(remote).getListTv(eq("game of thrones"),any())
        val tvEntities = LiveDataTestUtil.getValue(showRepository.getListTv("game of thrones"))
        verify(remote).getListTv(eq("game of thrones"),any())
        assertNotNull(tvEntities)
        assertEquals(data, tvEntities)
    }



    @Test
    fun getDetailMovie() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadDetailMovie)
                .onDetailMovieReceived(movieDetailResponses)
            null
        }.`when`(remote).getDetailMovie(eq(movieId), any())
        val movieEntities = LiveDataTestUtil.getValue(showRepository.getDetailMovie(movieId))
        verify(remote).getDetailMovie(eq(movieId), any())
        assertNotNull(movieEntities)
        assertEquals(movieDetailResponses, movieEntities)
    }

    @Test
    fun getDetailTv() {
        doAnswer { invocation ->
            (invocation.arguments[1] as RemoteDataSource.LoadDetailTv)
                .onDetailTvReceived(tvDetailResponse)
            null
        }.`when`(remote).getDetailTv(eq(tvId), any())
        val tvEntities = LiveDataTestUtil.getValue(showRepository.getDetailTv(tvId))
        verify(remote).getDetailTv(eq(tvId), any())
        assertNotNull(tvEntities)
        assertEquals(tvDetailResponse, tvEntities)
    }

    @Test
    fun getMovieFavoritesFromDb() {
        val data = PagingData.from(dataListResponse)
        val result = MutableLiveData<PagingData<FavoriteShowItems>>()
        result.value = data
        `when`(local.getMovieFavorites()).thenReturn(data)
        val movieEntities  = showRepository.getMovieFavoritesFromDb()
        verify(local).getMovieFavorites()
        assertNotNull(movieEntities)
        assertEquals(result.value, movieEntities.value)
    }

    @Test
    fun getTvFavoritesFromDb() {
        val data = PagingData.from(dataListResponse)
        val result = MutableLiveData<PagingData<FavoriteShowItems>>()
        result.value = data
        `when`(local.getTvFavorites()).thenReturn(data)
        val tvEntities  = showRepository.getTvFavoritesFromDb()
        verify(local).getTvFavorites()
        assertNotNull(tvEntities)
        assertEquals(result.value, tvEntities.value)
    }

    @Test
    fun getSelectedFavorite() {
        val result = MutableLiveData<List<FavoriteShowItems>>()
        result.value = dataListResponse
        `when`(local.getSelectedFromFavorite(movieId.toString(), 0)).thenReturn(result)
        val responseEntity  = showRepository.getSelectedFavorite(movieId.toString(), 0)
        verify(local).getSelectedFromFavorite(movieId.toString(), 0)
        assertNotNull(responseEntity)
        assertEquals(result.value, responseEntity.value)
    }


}