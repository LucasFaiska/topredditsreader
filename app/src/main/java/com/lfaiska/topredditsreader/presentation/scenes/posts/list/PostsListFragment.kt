package com.lfaiska.topredditsreader.presentation.scenes.posts.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lfaiska.topredditsreader.R
import com.lfaiska.topredditsreader.databinding.FragmentPostsListBinding
import org.koin.androidx.viewmodel.compat.ViewModelCompat

class PostsListFragment : Fragment() {
    lateinit var binding: FragmentPostsListBinding

    private val postsListViewModel: PostsListViewModel by ViewModelCompat.viewModel(
        this,
        PostsListViewModel::class.java
    )

    private val postsListAdapter: PostsListAdapter =
        PostsListAdapter { post ->
            Toast.makeText(
                this.requireActivity(),
                post.title,
                Toast.LENGTH_LONG
            ).show()
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_posts_list, container, false)
        setupView()
        loadPosts()
        return binding.root
    }

    private fun setupView() {
        val layoutManager = LinearLayoutManager(context)
        postsListAdapter.setHasStableIds(true)

        binding.viewmodel = postsListViewModel
        binding.postsList.apply {
            this.adapter = postsListAdapter
            this.layoutManager = layoutManager
        }

        binding.postsList.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        val visibleItemCount = layoutManager.childCount
                        val totalItemCount = layoutManager.itemCount
                        val pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()

                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loadPosts()
                        }
                    }
                }
            }
        )
    }

    private fun loadPosts() {
        postsListViewModel.loadPosts()
        postsListViewModel.postsList.observe(viewLifecycleOwner, Observer { postsList ->
            postsListAdapter.updateMatches(postsList.posts)
        })
    }
}