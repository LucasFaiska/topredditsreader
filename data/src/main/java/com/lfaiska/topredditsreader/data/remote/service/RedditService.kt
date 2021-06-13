package com.lfaiska.topredditsreader.data.remote.service

import com.lfaiska.topredditsreader.data.remote.dto.ListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {
    @GET("/top.json")
    suspend fun getTopPosts(@Query("after") after: String): ListResponse
}