package com.lfaiska.topredditsreader.data.remote.dto

data class ListResponse(
    val children: List<ListChildrenResponse>,
    val after: String
)