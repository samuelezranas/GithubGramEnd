package com.dicoding.GithubGram.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.GithubGram.adapter.FavoriteAdapter
import com.dicoding.GithubGram.ui.viewmodel.FavoriteViewModel
import com.dicoding.GithubGram.ui.viewmodel.ViewModelFactory
import com.dicoding.GithubGram.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager

        viewModel = createViewModel()

        viewModel.getAllFavorite().observe(this) {
            val adapter = FavoriteAdapter()
            adapter.submitList(it)
            binding.rvFavorite.adapter = adapter
        }
    }

    private fun createViewModel(): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(application)
        return ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
    }
}
