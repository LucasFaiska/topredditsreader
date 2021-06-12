package com.lfaiska.topredditsreader.domain.usecases

import com.lfaiska.topredditsreader.domain.model.PostList

interface GetTopRedditsPostsUseCase {
    suspend fun execute(after: String) : PostList
}