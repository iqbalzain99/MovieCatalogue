package com.example.moviecatalogue.di

import android.app.Application
import android.content.Context
import com.example.moviecatalogue.data.ShowRepository
import com.example.moviecatalogue.data.source.local.LocalDataSource
import com.example.moviecatalogue.data.source.remote.RemoteDataSource
import com.example.moviecatalogue.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context, application: Application): ShowRepository {

        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        val localDataSource = LocalDataSource.getInstance(application)

        return ShowRepository.getInstance(remoteDataSource, localDataSource)
    }
}