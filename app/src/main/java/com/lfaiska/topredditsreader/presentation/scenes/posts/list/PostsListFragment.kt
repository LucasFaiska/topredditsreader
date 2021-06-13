package com.lfaiska.topredditsreader.presentation.scenes.posts.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.lfaiska.topredditsreader.R
import com.lfaiska.topredditsreader.databinding.FragmentPostsListBinding

class PostsListFragment : Fragment() {
    lateinit var binding: FragmentPostsListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posts_list, container, false)
        return binding.root
    }
}