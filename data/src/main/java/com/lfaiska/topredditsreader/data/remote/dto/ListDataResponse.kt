package com.lfaiska.topredditsreader.data.remote.dto

data class ListDataResponse (
    val children: List<ListDataChildrenResponse>,
    val after: String
)