package com.lfaiska.topredditsreader.data.repository

import com.lfaiska.topredditsreader.data.remote.dto.ListResponse

interface RedditRepository {
    suspend fun getTopPosts(after: String): ListResponse
}