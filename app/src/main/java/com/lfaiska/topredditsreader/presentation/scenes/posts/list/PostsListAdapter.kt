package com.lfaiska.topredditsreader.presentation.scenes.posts.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.lfaiska.topredditsreader.R
import com.lfaiska.topredditsreader.databinding.ViewPostsListItemBinding
import com.lfaiska.topredditsreader.domain.model.PostData

class PostsListAdapter(
    private val onPostSelected: (matchId: PostData) -> Unit
) : RecyclerView.Adapter<PostsListAdapter.PostListViewHolder>() {

    private val posts: MutableList<PostData> = mutableListOf()

    fun clear() {
        posts.clear()
        this.notifyDataSetChanged()
    }

    fun updatePosts(newPosts: List<PostData>) {
        posts.addAll(newPosts)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        val binding: ViewPostsListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.view_posts_list_item,
            parent,
            false
        )

        return PostListViewHolder(
            binding
        )
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount() = posts.size

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        holder.binding.post = posts[position]
        holder.binding.root.setOnClickListener { onPostSelected.invoke(posts[position]) }
    }

    class PostListViewHolder(val binding: ViewPostsListItemBinding) : ViewHolder(binding.root)
}