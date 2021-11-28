package com.example.moviecatalogue.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import com.example.moviecatalogue.data.ShowRepository
import com.example.moviecatalogue.data.source.local.entity.FavoriteShowItems
import com.example.moviecatalogue.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    private lateinit var viewModel: FavoriteViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var showRepository: ShowRepository

    @Mock
    private lateinit var observer: Observer<PagingData<FavoriteShowItems>>

    @Before
    fun setUp() {
        viewModel = FavoriteViewModel(showRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovie = DataDummy.generateItems()
        val movie = MutableLiveData<PagingData<FavoriteShowItems>>()
        movie.value = PagingData.from(dummyMovie)

        Mockito.`when`(showRepository.getMovieFavoritesFromDb()).thenReturn(movie)
        val movieEntities  = viewModel.getMovies()
        Mockito.verify(showRepository).getMovieFavoritesFromDb()
        assertNotNull(movieEntities)
        assertEquals(movie, movieEntities)
        assertNotNull(movie.value)
        assertEquals(movie.value, movieEntities.value)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(movie.value)
    }

    @Test
    fun getBlankMovies() {
        val dummyMovie = listOf<FavoriteShowItems>()
        val movie = MutableLiveData<PagingData<FavoriteShowItems>>()
        movie.value = PagingData.from(dummyMovie)

        Mockito.`when`(showRepository.getMovieFavoritesFromDb()).thenReturn(movie)
        val movieEntities  = viewModel.getMovies()
        Mockito.verify(showRepository).getMovieFavoritesFromDb()
        assertNotNull(movieEntities)
        assertEquals(movie, movieEntities)
        assertNotNull(movie.value)
        assertEquals(movie.value, movieEntities.value)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(movie.value)
    }

    @Test
    fun getEmptyMovies() {
        val dummyMovie = emptyList<FavoriteShowItems>()
        val movie = MutableLiveData<PagingData<FavoriteShowItems>>()
        movie.value = PagingData.from(dummyMovie)

        Mockito.`when`(showRepository.getMovieFavoritesFromDb()).thenReturn(movie)
        val movieEntities  = viewModel.getMovies()
        Mockito.verify(showRepository).getMovieFavoritesFromDb()
        assertNotNull(movieEntities)
        assertEquals(movie, movieEntities)
        assertNotNull(movie.value)
        assertEquals(movie.value, movieEntities.value)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(movie.value)
    }

    @Test
    fun getTvShow() {
        val dummyMovie = DataDummy.generateItems()
        val tv = MutableLiveData<PagingData<FavoriteShowItems>>()
        tv.value = PagingData.from(dummyMovie)

        Mockito.`when`(showRepository.getTvFavoritesFromDb()).thenReturn(tv)
        val tvEntities  = viewModel.getTvShow()
        Mockito.verify(showRepository).getTvFavoritesFromDb()
        assertNotNull(tvEntities)
        assertEquals(tv, tvEntities)
        assertNotNull(tv.value)
        assertEquals(tv.value, tvEntities.value)

        viewModel.getTvShow().observeForever(observer)
        verify(observer).onChanged(tv.value)
    }

    @Test
    fun getBlankTvShow() {
        val dummyMovie = listOf<FavoriteShowItems>()
        val tv = MutableLiveData<PagingData<FavoriteShowItems>>()
        tv.value = PagingData.from(dummyMovie)

        Mockito.`when`(showRepository.getTvFavoritesFromDb()).thenReturn(tv)
        val tvEntities  = viewModel.getTvShow()
        Mockito.verify(showRepository).getTvFavoritesFromDb()
        assertNotNull(tvEntities)
        assertEquals(tv, tvEntities)
        assertNotNull(tv.value)
        assertEquals(tv.value, tvEntities.value)

        viewModel.getTvShow().observeForever(observer)
        verify(observer).onChanged(tv.value)
    }

    @Test
    fun getEmptyTvShow() {
        val dummyMovie = emptyList<FavoriteShowItems>()
        val tv = MutableLiveData<PagingData<FavoriteShowItems>>()
        tv.value = PagingData.from(dummyMovie)

        Mockito.`when`(showRepository.getTvFavoritesFromDb()).thenReturn(tv)
        val tvEntities  = viewModel.getTvShow()
        Mockito.verify(showRepository).getTvFavoritesFromDb()
        assertNotNull(tvEntities)
        assertEquals(tv, tvEntities)
        assertNotNull(tv.value)
        assertEquals(tv.value, tvEntities.value)

        viewModel.getTvShow().observeForever(observer)
        verify(observer).onChanged(tv.value)
    }


}