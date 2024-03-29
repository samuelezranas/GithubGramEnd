package com.dicoding.GithubGram.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.GithubGram.data.response.ItemsItem
import com.dicoding.GithubGram.ui.main.DetailUserActivity
import com.dicoding.GithubGram.databinding.ItemLayoutBinding

class UserAdapter : ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
    }

    class MyViewHolder(private val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(items: ItemsItem) {
            Glide.with(binding.root)
                .load(items.avatarUrl)
                .into(binding.imUserPicture)

            binding.tvUsername.text = items.login

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailUserActivity::class.java)
                intent.putExtra(EXTRA_USERNAME, items.login)
                intent.putExtra(EXTRA_AVATAR, items.avatarUrl)
                intent.putExtra(EXTRA_ID, items.id)
                binding.root.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
        const val EXTRA_USERNAME = "USERNAME"
        const val EXTRA_AVATAR = "AVATAR"
        const val EXTRA_ID = "ID"
    }
}