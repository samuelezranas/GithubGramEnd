package com.dicoding.GithubGram.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.GithubGram.R
import com.dicoding.GithubGram.adapter.UserAdapter
import com.dicoding.GithubGram.data.datastore.SettingPreferences
import com.dicoding.GithubGram.data.datastore.dataStore
import com.dicoding.GithubGram.data.response.ItemsItem
import com.dicoding.GithubGram.databinding.ActivityMainBinding
import com.dicoding.GithubGram.ui.viewmodel.MainViewModel
import com.dicoding.GithubGram.ui.viewmodel.SettingViewModel
import com.dicoding.GithubGram.ui.viewmodel.SettingViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModelFactory = SettingViewModelFactory(pref)
        val settingViewModel = ViewModelProvider(this, settingViewModelFactory)[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        with(binding) {
            svUser.setupWithSearchBar(searchUser)
            svUser
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchUser.text = svUser.text
                    mainViewModel.findGithub(svUser.text.toString())
                    svUser.hide()
                    showLoading(true)
                    false
                }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvMain.layoutManager = layoutManager

        mainViewModel.listUser.observe(this) { user ->
            showUserData(user)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showUserData(user: List<ItemsItem>?) {
        val adapter = UserAdapter()
        adapter.submitList(user)
        binding.rvMain.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorites -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.settings -> {
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
