package com.lfaiska.topredditsreader.domain

import com.lfaiska.topredditsreader.data.remote.dto.ListDataChildrenDataResponse
import com.lfaiska.topredditsreader.data.remote.dto.ListDataChildrenResponse
import com.lfaiska.topredditsreader.data.remote.dto.ListDataResponse
import com.lfaiska.topredditsreader.data.remote.dto.ListResponse
import com.lfaiska.topredditsreader.data.repository.RedditRepository
import com.lfaiska.topredditsreader.domain.model.PostData
import com.lfaiska.topredditsreader.domain.model.PostList
import com.lfaiska.topredditsreader.domain.usecase.GetTopRedditsPostsUseCase
import com.lfaiska.topredditsreader.domain.usecase.GetTopRedditsPostsUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetTopRedditsPostsUseCaseTest {

    private lateinit var useCase: GetTopRedditsPostsUseCase

    @MockK
    private lateinit var repository: RedditRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = GetTopRedditsPostsUseCaseImpl(repository)
    }

    @Test
    fun `when execute use case successfully should retrieve a post list from repository and returns it`() {
        val mockedListResponse = ListResponse(
            ListDataResponse(
                listOf(
                    ListDataChildrenResponse(
                        "kind1",
                        ListDataChildrenDataResponse("Title1", "thumb1")
                    )
                ),
                "after1"
            )
        )

        val expectedResult = PostList("after1", listOf(PostData("kind1", "Title1", "thumb1")))

        runBlocking {
            coEvery { repository.getTopPosts(any()) } returns mockedListResponse

            val result = useCase.execute("")

            coVerify(Ordering.SEQUENCE) {
                repository.getTopPosts("")
            }

            assertEquals(expectedResult, result)
        }
    }
}