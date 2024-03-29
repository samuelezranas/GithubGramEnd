package com.dicoding.GithubGram.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.GithubGram.ui.main.SectionFragment

class SectionPageAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment = SectionFragment()
        fragment.arguments = Bundle().apply {
            putInt(SectionFragment.ARG_POSITION, position + 1)
            putString(SectionFragment.ARG_USERNAME, username)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}