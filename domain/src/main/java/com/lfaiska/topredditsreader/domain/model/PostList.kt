package com.lfaiska.topredditsreader.domain.model

data class PostList(
    val after: String,
    val posts: List<PostData>
)