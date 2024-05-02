package com.dicoding.GithubGram.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.GithubGram.adapter.SectionPageAdapter
import com.dicoding.GithubGram.data.database.FavoriteUser
import com.dicoding.GithubGram.ui.viewmodel.DetailViewModel
import com.dicoding.GithubGram.ui.viewmodel.ViewModelFactory
import com.dicoding.GithubGram.R
import com.dicoding.GithubGram.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailViewModel : DetailViewModel
    private lateinit var detailBinding: ActivityDetailUserBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        val username = intent.getStringExtra(USER_EXTRA)
        val avatarUrl = intent.getStringExtra(AVATAR_EXTRA)

        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        val sectionPageAdapter = username?.let { SectionPageAdapter(this, it) }
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPageAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        detailViewModel = createViewModel()

        var favoriteUser: FavoriteUser = FavoriteUser().apply {
            if (username != null) {
                this.username = username
            }
            this.avatarUrl = avatarUrl
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.isFavorite.observe(this){
            setIfFavorite(it)
        }

        if (username != null) {
            detailViewModel.getDetail(username)
            detailViewModel.getFollowerGithub(username)
            detailViewModel.getFollowingGithub(username)
            detailViewModel.getReposCount(username)
        }

        detailViewModel.user.observe(this) {
            if (it != null) {
                Glide.with(this@DetailUserActivity)
                    .load(it.avatarUrl)
                    .centerCrop()
                    .into(detailBinding.imUserDetail)
                detailBinding.tvNameDetail.text = it.name
                detailBinding.tvUsernameDetail.text = "@${it.login}"
                detailBinding.tvFollowersDetail.text = "${it.followers} Followers"
                detailBinding.tvFollowingDetail.text = "${it.following} Following"
                detailBinding.tvRepositories.text = "${it.repositories} Repositories"

            }
        }

        detailViewModel.getListFav().observe(this) { favoritedUsers ->
            favoritedUsers?.forEach {
                if (it.username == username) {
                    detailViewModel.setIsFavorite(true)
                    favoriteUser = it
                }
            }
        }

        detailBinding.fabLove.setOnClickListener {
            detailViewModel.updateFavUser(favoriteUser, this)
        }

    }

    private fun showLoading(isLoading: Boolean) { detailBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE }

    private fun setIfFavorite(isFavorite: Boolean){
        if(isFavorite){
            detailBinding.fabLove.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_favorite_24))
        }else{
            detailBinding.fabLove.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_favorite_border_24))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorites -> {
                startActivity(Intent(this@DetailUserActivity, FavoriteActivity::class.java))
                true
            }
            R.id.settings -> {
                startActivity(Intent(this@DetailUserActivity, SettingActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createViewModel(): DetailViewModel {
        val factory = ViewModelFactory.getInstance(application)
        return ViewModelProvider(this, factory)[DetailViewModel::class.java]
    }

    companion object {
        const val USER_EXTRA = "USERNAME"
        const val AVATAR_EXTRA ="AVATAR"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }
}
