package com.lfaiska.topredditsreader.presentation.scenes.posts.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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
        setupObservers()
        loadPosts()
        return binding.root
    }

    private fun setupView() {
        val linearLayoutManager = LinearLayoutManager(context)
        postsListAdapter.setHasStableIds(true)

        binding.viewmodel = postsListViewModel
        binding.postsList.apply {
            this.adapter = postsListAdapter
            this.layoutManager = linearLayoutManager
        }

        binding.postsList.addOnScrollListener(endlessScrollListener)

        binding.swipeContainer.setOnRefreshListener {
            refreshPosts()
        }
    }

    private val endlessScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val visibleItemCount = linearLayoutManager.childCount
            val totalItemCount = linearLayoutManager.itemCount
            val pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition()

            if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                updatePosts()
            }
        }
    }

    private fun setupObservers() {
        postsListViewModel.postsList.observe(viewLifecycleOwner, { postsList ->
            postsListAdapter.updatePosts(postsList.posts)
        })

        postsListViewModel.isLoadingPosts.observe(viewLifecycleOwner, { isLoading ->
            binding.isLoadingPosts = isLoading
        })

        postsListViewModel.isEmptyPosts.observe(viewLifecycleOwner, { isEmpty ->
            binding.isEmptyPosts = isEmpty
        })

        postsListViewModel.hasLoadPostsFailure.observe(viewLifecycleOwner, { hasFailure ->
            binding.hasLoadFailure = hasFailure
        })

        postsListViewModel.hasUpdatePostsFailure.observe(viewLifecycleOwner, { hasFailure ->
            if (hasFailure) {
                AlertDialog.Builder(requireContext())
                    .setMessage(getString(R.string.posts_update_failure_message)).show()
            }
        })
    }

    private fun loadPosts() {
        postsListViewModel.loadPosts()
    }

    private fun updatePosts() {
        postsListViewModel.updatePosts()
    }

    private fun refreshPosts() {
        postsListAdapter.clear()
        postsListViewModel.loadPosts()
        binding.swipeContainer.isRefreshing = false;
    }
}