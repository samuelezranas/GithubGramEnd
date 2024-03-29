package com.dicoding.GithubGram.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.GithubGram.data.database.Repository
import com.dicoding.GithubGram.data.database.FavoriteUser

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: Repository = Repository(application)

    fun getAllFavorite(): LiveData<List<FavoriteUser>> = repository.getAllFavorite()
}
