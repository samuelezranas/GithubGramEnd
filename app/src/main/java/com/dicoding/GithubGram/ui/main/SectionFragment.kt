package com.dicoding.GithubGram.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.GithubGram.adapter.UserAdapter
import com.dicoding.GithubGram.data.response.ItemsItem
import com.dicoding.GithubGram.ui.viewmodel.DetailViewModel
import com.dicoding.GithubGram.databinding.FragmentSectionBinding

class SectionFragment : Fragment() {

    private lateinit var sectionBinding: FragmentSectionBinding
    private var position: Int? = null
    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        sectionBinding = FragmentSectionBinding.inflate(inflater, container, false)
        return sectionBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        if (position == 1) {
            username?.let { viewModel.getFollowerGithub(it) }
            viewModel.userFollower.observe(viewLifecycleOwner) { followers ->
                viewModel.isLoading.observe(viewLifecycleOwner) { }
                followers?.let { setFollowData(it) }
            }
        } else {
            username?.let { viewModel.getFollowingGithub(it) }
            viewModel.userFollowing.observe(viewLifecycleOwner) { following ->
                viewModel.isLoading.observe(viewLifecycleOwner) { }
                following?.let { setFollowData(it) }
            }
        }
    }

    private fun setFollowData(followers: List<ItemsItem>) {
        sectionBinding.rvSectionPage.layoutManager = LinearLayoutManager(requireContext())
        val adapter = UserAdapter()
        adapter.submitList(followers)
        sectionBinding.rvSectionPage.adapter = adapter
    }

    companion object {
        const val ARG_POSITION: String = "arg_position"
        const val ARG_USERNAME: String = "arg_username"
    }
}
