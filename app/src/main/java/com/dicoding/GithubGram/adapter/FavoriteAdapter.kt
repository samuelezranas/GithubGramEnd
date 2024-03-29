package com.dicoding.GithubGram.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.GithubGram.data.database.FavoriteUser
import com.dicoding.GithubGram.ui.main.DetailUserActivity
import com.dicoding.GithubGram.databinding.ItemLayoutBinding

class FavoriteAdapter : ListAdapter<FavoriteUser, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
    }
    class MyViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(items: FavoriteUser){
            binding.tvUsername.text = items.username
            Glide.with(binding.root)
                .load(items.avatarUrl)
                .into(binding.imUserPicture)

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailUserActivity::class.java)
                intent.putExtra(EXTRA_USERNAME, items.username)
                intent.putExtra(EXTRA_AVATAR, items.avatarUrl)
                binding.root.context.startActivity(intent)
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUser>() {
            override fun areItemsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: FavoriteUser, newItem: FavoriteUser): Boolean {
                return oldItem == newItem
            }
        }
        const val EXTRA_USERNAME = "USERNAME"
        const val EXTRA_AVATAR = "AVATAR"
    }
}