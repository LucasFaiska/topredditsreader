package com.lfaiska.topredditsreader.domain.usecases

import com.lfaiska.topredditsreader.data.repository.RedditRepository
import com.lfaiska.topredditsreader.domain.mappers.PostMapper.Companion.toPostList
import com.lfaiska.topredditsreader.domain.model.PostList

class GetTopRedditsPostsUseCaseImpl(private val repository: RedditRepository) : GetTopRedditsPostsUseCase {
    override suspend fun execute(after: String): PostList = repository.getTopPosts(after).toPostList()
}