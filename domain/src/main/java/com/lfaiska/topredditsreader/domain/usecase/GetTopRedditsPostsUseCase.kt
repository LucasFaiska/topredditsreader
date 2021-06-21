package com.lfaiska.topredditsreader.domain.usecase

import com.lfaiska.topredditsreader.domain.model.PostList

interface GetTopRedditsPostsUseCase {
    suspend fun execute(after: String) : PostList
}