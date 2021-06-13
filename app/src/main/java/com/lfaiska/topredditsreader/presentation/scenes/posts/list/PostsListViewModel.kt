package com.lfaiska.topredditsreader.presentation.scenes.posts.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lfaiska.topredditsreader.domain.model.PostData
import com.lfaiska.topredditsreader.domain.model.PostList
import com.lfaiska.topredditsreader.domain.usecases.GetTopRedditsPostsUseCase
import kotlinx.coroutines.launch

class PostsListViewModel constructor(
    private val getTopRedditsPostsUseCase: GetTopRedditsPostsUseCase
) : ViewModel() {

    private val _postsList = MutableLiveData<PostList>()
    val postsList: LiveData<PostList> = _postsList

    fun loadPosts() {
        viewModelScope.launch {
            val after = _postsList.value?.after ?: ""
            _postsList.value = getTopRedditsPostsUseCase.execute(after)
        }
    }
}