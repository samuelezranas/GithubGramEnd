package com.dicoding.GithubGram.ui.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.GithubGram.data.database.Repository
import com.dicoding.GithubGram.data.database.FavoriteUser
import com.dicoding.GithubGram.data.response.DetailGithubResponse
import com.dicoding.GithubGram.data.response.ItemsItem
import com.dicoding.GithubGram.data.retrofit.ApiConfig
import com.dicoding.GithubGram.ui.main.DetailUserActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {

    private val mRepository: Repository = Repository(application)

    private val _user = MutableLiveData<DetailGithubResponse?>()
    val user: LiveData<DetailGithubResponse?> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userFollower = MutableLiveData<List<ItemsItem>?>()
    val userFollower: LiveData<List<ItemsItem>?> = _userFollower

    private val _userFollowing = MutableLiveData<List<ItemsItem>?>()
    val userFollowing: LiveData<List<ItemsItem>?> = _userFollowing

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite : LiveData<Boolean> = _isFavorite

    companion object {
        private const val TAG = "DetailViewModel"
    }


    fun getDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetail(username)
        client.enqueue(object : Callback<DetailGithubResponse> {
            override fun onResponse(
                call: Call<DetailGithubResponse>,
                response: Response<DetailGithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                } else {
                    Log.e(TAG, response.message())
                }
            }

            override fun onFailure(call: Call<DetailGithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, t.message ?: "Unknown error")
            }
        })
    }

    fun getFollowerGithub(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userFollower.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getFollowingGithub(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getReposGithub(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userFollowing.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun getListFav(): LiveData<List<FavoriteUser>> = mRepository.getAllFavorite()

    fun setIsFavorite(isFavorite: Boolean){
        _isFavorite.value = isFavorite
    }

    private fun addFavorite(favorite: FavoriteUser){
        setIsFavorite(true)
        mRepository.insert(favorite)
    }

    private fun removeFavorite(favorite: FavoriteUser){
        setIsFavorite(false)
        mRepository.delete(favorite)
    }

    fun updateFavUser(favUser: FavoriteUser, activity: DetailUserActivity){
        val failMessage =  "Success Remove from Favorite"
        val succesMessage =  "Success Add to Favorite"
        if( isFavorite.value != true ){
            addFavorite(favUser)
            Toast.makeText(activity, succesMessage, Toast.LENGTH_SHORT).show()
        }else{
            removeFavorite(favUser)
            Toast.makeText(activity, failMessage, Toast.LENGTH_SHORT).show()
        }
    }

}

