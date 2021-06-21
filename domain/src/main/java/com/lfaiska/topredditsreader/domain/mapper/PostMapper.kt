package com.lfaiska.topredditsreader.domain.mapper

import com.lfaiska.topredditsreader.data.remote.dto.ListResponse
import com.lfaiska.topredditsreader.domain.model.PostData
import com.lfaiska.topredditsreader.domain.model.PostList

class PostMapper {
    companion object {
        fun ListResponse.toPostList(): PostList {
            return PostList(
                this.data.after,
                this.data.children.map { listChildrenResponse ->
                    PostData(
                        listChildrenResponse.kind,
                        listChildrenResponse.data.title,
                        listChildrenResponse.data.thumbnail
                    )
                }
            )
        }
    }
}