package com.lfaiska.topredditsreader.data.repository

import com.lfaiska.topredditsreader.data.remote.dto.ListResponse
import com.lfaiska.topredditsreader.data.remote.service.RedditService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RedditRepository(private val service: RedditService) {
    suspend fun getTopPosts(after: String): ListResponse {
        return withContext(Dispatchers.IO) {
            try {
                service.getTopPosts(after)
            } catch (exception: Exception) {
                throw RepositoryException(exception)
            }
        }
    }
}