package com.dicoding.GithubGram.data.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.GithubGram.data.database.FavoriteDao
import com.dicoding.GithubGram.data.database.FavoriteRoomDatabase
import com.dicoding.GithubGram.data.database.FavoriteUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(application: Application) {
    private val mFavoriteDao: FavoriteDao

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoriteDao = db.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<FavoriteUser>> = mFavoriteDao.getFavorite()

    fun insert(favorite: FavoriteUser) {
        executorService.execute { mFavoriteDao.insert(favorite) }
    }

    fun delete(favorite: FavoriteUser) {
        executorService.execute { mFavoriteDao.delete(favorite) }
    }
}