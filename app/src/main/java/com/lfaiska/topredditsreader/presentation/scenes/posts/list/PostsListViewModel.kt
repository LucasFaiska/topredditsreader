package com.lfaiska.topredditsreader.presentation.scenes.posts.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lfaiska.topredditsreader.data.repository.RepositoryException
import com.lfaiska.topredditsreader.domain.model.PostList
import com.lfaiska.topredditsreader.domain.usecases.GetTopRedditsPostsUseCase
import kotlinx.coroutines.launch

class PostsListViewModel constructor(
    private val getTopRedditsPostsUseCase: GetTopRedditsPostsUseCase
) : ViewModel() {

    private val _postsList = MutableLiveData<PostList>()
    val postsList: LiveData<PostList> = _postsList

    private val _isLoadingPosts = MutableLiveData<Boolean>()
    val isLoadingPosts: LiveData<Boolean> = _isLoadingPosts

    private val _isEmptyPosts = MutableLiveData(false)
    val isEmptyPosts: LiveData<Boolean> = _isEmptyPosts

    private val _hasLoadPostsFailure = MutableLiveData(false)
    val hasLoadPostsFailure: LiveData<Boolean> = _hasLoadPostsFailure

    private val _hasUpdatePostsFailure = MutableLiveData(false)
    val hasUpdatePostsFailure: LiveData<Boolean> = _hasUpdatePostsFailure

    fun loadPosts() {
        viewModelScope.launch {
            _isLoadingPosts.value = true
            performLoadPosts()
            _isLoadingPosts.value = false
        }
    }

    private suspend fun performLoadPosts() {
        try {
            val postList = getTopRedditsPostsUseCase.execute("")
            if (postList.posts.isEmpty()) {
                _isEmptyPosts.value = true
            } else {
                _postsList.value = postList
            }
        } catch (repositoryException: RepositoryException) {
            _hasLoadPostsFailure.value = true
        }
    }

    fun updatePosts() {
        viewModelScope.launch {
            _isLoadingPosts.value = true
            performUpdatePosts(_postsList.value?.after ?: "")
            _isLoadingPosts.value = false
        }
    }

    private suspend fun performUpdatePosts(after: String) {
        if (after.isNotBlank()) {
            try {
                _postsList.value = getTopRedditsPostsUseCase.execute(after)
            } catch (repositoryException: RepositoryException) {
                _hasUpdatePostsFailure.value = true
            }
        }
    }
}