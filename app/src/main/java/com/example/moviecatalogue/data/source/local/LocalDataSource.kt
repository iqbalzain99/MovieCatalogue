package com.example.moviecatalogue.data.source.local

import android.app.Application
import androidx.paging.PagingData
import com.example.moviecatalogue.data.source.local.entity.FavoriteShowItems
import com.example.moviecatalogue.data.source.local.room.FavoriteRoomDatabase
import com.example.moviecatalogue.data.source.local.room.ShowDao
import com.example.moviecatalogue.utils.EspressoIdlingResource
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LocalDataSource private constructor(application: Application) {

    private val mFavoritesDao: ShowDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()


    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(application: Application) : LocalDataSource =
            INSTANCE ?: LocalDataSource(application)
    }

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoritesDao = db.favoriteDao()
    }

    fun insertFavorite(favorite : FavoriteShowItems) {
        EspressoIdlingResource.increment()
        executorService.execute { mFavoritesDao.insert(favorite) }
        EspressoIdlingResource.decrement()
    }

    fun deleteFavorite(favorite : FavoriteShowItems) {
        EspressoIdlingResource.increment()
        executorService.execute {  mFavoritesDao.delete(favorite) }
        EspressoIdlingResource.decrement()
    }

    fun getMovieFavorites() : PagingData<FavoriteShowItems> =
        PagingData.from(mFavoritesDao.getAllMovieFavorite())

    fun getTvFavorites() : PagingData<FavoriteShowItems> =
        PagingData.from(mFavoritesDao.getAllTvFavorite())

    fun getSelectedFromFavorite(id: String, type: Int) =
        mFavoritesDao.getSelectedFromDb(id.toInt(), type)

}