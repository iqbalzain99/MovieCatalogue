package com.example.moviecatalogue.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.moviecatalogue.data.ShowRepository
import com.example.moviecatalogue.data.source.local.entity.FavoriteShowItems
import com.example.moviecatalogue.data.source.remote.response.MovieDetailResponse
import com.example.moviecatalogue.data.source.remote.response.TvDetailResponse
import com.example.moviecatalogue.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    private val dummyTvData = DataDummy.generateDetailTv()
    private val dummyTvId = dummyTvData.id as Int

    private val dummyMovieData = DataDummy.generateDetailMovie()
    private val dummyMovieId = dummyTvData.id as Int

    private val dummyListFavorite = DataDummy.generateItems()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var showRepository: ShowRepository

    @Mock
    private lateinit var tvObserver: Observer<TvDetailResponse>

    @Mock
    private lateinit var movieObserver: Observer<MovieDetailResponse>

    @Mock
    private lateinit var favoriteObserver: Observer<List<FavoriteShowItems>>



    @Before
    fun setUp() {
        viewModel = DetailViewModel(showRepository)
    }

    @Test
    fun getTvShow() {
        val tv = MutableLiveData<TvDetailResponse>()
        tv.value = dummyTvData

        Mockito.`when`(showRepository.getDetailTv(dummyTvId)).thenReturn(tv)
        val tvEntities  = viewModel.getTvShow(dummyTvId).value
        verify(showRepository).getDetailTv(dummyTvId)
        assertNotNull(tvEntities)
        assertEquals(dummyTvData.genres, tvEntities?.genres)
        assertEquals(dummyTvData.numberOfSeasons, tvEntities?.numberOfSeasons)
        assertEquals(dummyTvData.backdropPath, tvEntities?.backdropPath)
        assertEquals(dummyTvData.homepage, tvEntities?.homepage)
        assertEquals(dummyTvData.firstAirDate, tvEntities?.firstAirDate)
        assertEquals(dummyTvData.overview, tvEntities?.overview)
        assertEquals(dummyTvData.voteAverage, tvEntities?.voteAverage)
        assertEquals(dummyTvData.id, tvEntities?.id)


        viewModel.getTvShow(dummyTvId).observeForever(tvObserver)
        verify(tvObserver).onChanged(dummyTvData)
    }

    @Test
    fun getBlankTvShow() {
        val tv = MutableLiveData<TvDetailResponse>()
        tv.value = TvDetailResponse()

        Mockito.`when`(showRepository.getDetailTv(dummyTvId)).thenReturn(tv)
        val tvEntities  = viewModel.getTvShow(dummyTvId).value
        verify(showRepository).getDetailTv(dummyTvId)
        assertNotNull(tvEntities)
        assertEquals(TvDetailResponse().genres, tvEntities?.genres)
        assertEquals(TvDetailResponse().numberOfSeasons, tvEntities?.numberOfSeasons)
        assertEquals(TvDetailResponse().backdropPath, tvEntities?.backdropPath)
        assertEquals(TvDetailResponse().homepage, tvEntities?.homepage)
        assertEquals(TvDetailResponse().firstAirDate, tvEntities?.firstAirDate)
        assertEquals(TvDetailResponse().overview, tvEntities?.overview)
        assertEquals(TvDetailResponse().voteAverage, tvEntities?.voteAverage)
        assertEquals(TvDetailResponse().id, tvEntities?.id)


        viewModel.getTvShow(dummyTvId).observeForever(tvObserver)
        verify(tvObserver).onChanged(TvDetailResponse())
    }

    @Test
    fun getEmptyTvShow() {
        val tv = MutableLiveData<TvDetailResponse>()
        tv.value = null

        Mockito.`when`(showRepository.getDetailTv(dummyTvId)).thenReturn(tv)
        val tvEntities  = viewModel.getTvShow(dummyTvId).value
        verify(showRepository).getDetailTv(dummyTvId)
        assertNull(tvEntities)

        viewModel.getTvShow(dummyTvId).observeForever(tvObserver)
        verify(tvObserver).onChanged(null)
    }



    @Test
    fun getSelectedFavoriteTv() {
        val tv = MutableLiveData<List<FavoriteShowItems>>()
        tv.value = dummyListFavorite

        Mockito.`when`(showRepository.getSelectedFavorite(dummyTvId.toString(), 1)).thenReturn(tv)
        val movieEntities  = viewModel.getSelectedFavoriteTv(dummyTvId.toString()).value
        verify(showRepository).getSelectedFavorite(dummyTvId.toString(), 1)
        assertNotNull(movieEntities)
        assertEquals(5, movieEntities?.size)


        viewModel.getSelectedFavoriteTv(dummyTvId.toString()).observeForever(favoriteObserver)
        verify(favoriteObserver).onChanged(dummyListFavorite)
    }

    @Test
    fun getBlankSelectedFavoriteTv() {
        val tv = MutableLiveData<List<FavoriteShowItems>>()
        tv.value = listOf()

        Mockito.`when`(showRepository.getSelectedFavorite(dummyTvId.toString(), 1)).thenReturn(tv)
        val movieEntities  = viewModel.getSelectedFavoriteTv(dummyTvId.toString()).value
        verify(showRepository).getSelectedFavorite(dummyTvId.toString(), 1)
        assertNotNull(movieEntities)
        assertEquals(0, movieEntities?.size)


        viewModel.getSelectedFavoriteTv(dummyTvId.toString()).observeForever(favoriteObserver)
        verify(favoriteObserver).onChanged(listOf())
    }


    @Test
    fun getEmptySelectedFavoriteTv() {
        val tv = MutableLiveData<List<FavoriteShowItems>>()
        tv.value = emptyList()

        Mockito.`when`(showRepository.getSelectedFavorite(dummyTvId.toString(), 1)).thenReturn(tv)
        val movieEntities  = viewModel.getSelectedFavoriteTv(dummyTvId.toString()).value
        verify(showRepository).getSelectedFavorite(dummyTvId.toString(), 1)
        assertNotNull(movieEntities)
        assertEquals(0, movieEntities?.size)


        viewModel.getSelectedFavoriteTv(dummyTvId.toString()).observeForever(favoriteObserver)
        verify(favoriteObserver).onChanged(emptyList())
    }


    @Test
    fun getMovie() {
        val movie = MutableLiveData<MovieDetailResponse>()
        movie.value = dummyMovieData

        Mockito.`when`(showRepository.getDetailMovie(dummyMovieId)).thenReturn(movie)
        val movieEntities  = viewModel.getMovies(dummyMovieId).value
        verify(showRepository).getDetailMovie(dummyMovieId)
        assertNotNull(movieEntities)
        assertEquals(dummyMovieData.genres, movieEntities?.genres)
        assertEquals(dummyMovieData.releaseDate, movieEntities?.releaseDate)
        assertEquals(dummyMovieData.backdropPath, movieEntities?.backdropPath)
        assertEquals(dummyMovieData.homepage, movieEntities?.homepage)
        assertEquals(dummyMovieData.imdbId, movieEntities?.imdbId)
        assertEquals(dummyMovieData.overview, movieEntities?.overview)
        assertEquals(dummyMovieData.voteAverage, movieEntities?.voteAverage)
        assertEquals(dummyMovieData.id, movieEntities?.id)


        viewModel.getMovies(dummyMovieId).observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovieData)
    }

    @Test
    fun getBlankMovie() {
        val movie = MutableLiveData<MovieDetailResponse>()
        movie.value = MovieDetailResponse()

        Mockito.`when`(showRepository.getDetailMovie(dummyMovieId)).thenReturn(movie)
        val movieEntities  = viewModel.getMovies(dummyMovieId).value
        verify(showRepository).getDetailMovie(dummyMovieId)
        assertNotNull(movieEntities)
        assertEquals(MovieDetailResponse().genres, movieEntities?.genres)
        assertEquals(MovieDetailResponse().releaseDate, movieEntities?.releaseDate)
        assertEquals(MovieDetailResponse().backdropPath, movieEntities?.backdropPath)
        assertEquals(MovieDetailResponse().homepage, movieEntities?.homepage)
        assertEquals(MovieDetailResponse().imdbId, movieEntities?.imdbId)
        assertEquals(MovieDetailResponse().overview, movieEntities?.overview)
        assertEquals(MovieDetailResponse().voteAverage, movieEntities?.voteAverage)
        assertEquals(MovieDetailResponse().id, movieEntities?.id)


        viewModel.getMovies(dummyMovieId).observeForever(movieObserver)
        verify(movieObserver).onChanged(MovieDetailResponse())
    }

    @Test
    fun getEmptyMovie() {
        val movie = MutableLiveData<MovieDetailResponse>()
        movie.value = null

        Mockito.`when`(showRepository.getDetailMovie(dummyMovieId)).thenReturn(movie)
        val movieEntities  = viewModel.getMovies(dummyMovieId).value
        verify(showRepository).getDetailMovie(dummyMovieId)
        assertNull(movieEntities)


        viewModel.getMovies(dummyMovieId).observeForever(movieObserver)
        verify(movieObserver).onChanged(null)
    }

    @Test
    fun getSelectedFavoriteMovie() {
        val movie = MutableLiveData<List<FavoriteShowItems>>()
        movie.value = dummyListFavorite

        Mockito.`when`(showRepository.getSelectedFavorite(dummyTvId.toString(), 0)).thenReturn(movie)
        val movieEntities  = viewModel.getSelectedFavoriteMovie(dummyMovieId.toString()).value
        verify(showRepository).getSelectedFavorite(dummyTvId.toString(), 0)
        assertNotNull(movieEntities)
        assertEquals(5, movieEntities?.size)


        viewModel.getSelectedFavoriteMovie(dummyMovieId.toString()).observeForever(favoriteObserver)
        verify(favoriteObserver).onChanged(dummyListFavorite)
    }

    @Test
    fun getBlankSelectedFavoriteMovie() {
        val movie = MutableLiveData<List<FavoriteShowItems>>()
        movie.value = listOf()

        Mockito.`when`(showRepository.getSelectedFavorite(dummyTvId.toString(), 0)).thenReturn(movie)
        val movieEntities  = viewModel.getSelectedFavoriteMovie(dummyMovieId.toString()).value
        verify(showRepository).getSelectedFavorite(dummyTvId.toString(), 0)
        assertNotNull(movieEntities)
        assertEquals(0, movieEntities?.size)


        viewModel.getSelectedFavoriteMovie(dummyMovieId.toString()).observeForever(favoriteObserver)
        verify(favoriteObserver).onChanged(listOf())
    }

    @Test
    fun getEmptySelectedFavoriteMovie() {
        val movie = MutableLiveData<List<FavoriteShowItems>>()
        movie.value = emptyList()

        Mockito.`when`(showRepository.getSelectedFavorite(dummyTvId.toString(), 0)).thenReturn(movie)
        val movieEntities  = viewModel.getSelectedFavoriteMovie(dummyMovieId.toString()).value
        verify(showRepository).getSelectedFavorite(dummyTvId.toString(), 0)
        assertNotNull(movieEntities)
        assertEquals(0, movieEntities?.size)


        viewModel.getSelectedFavoriteMovie(dummyMovieId.toString()).observeForever(favoriteObserver)
        verify(favoriteObserver).onChanged(emptyList())
    }


}