package com.lfaiska.topredditsreader

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.lfaiska.topredditsreader.data.repository.RepositoryException
import com.lfaiska.topredditsreader.domain.model.PostData
import com.lfaiska.topredditsreader.domain.model.PostList
import com.lfaiska.topredditsreader.domain.usecases.GetTopRedditsPostsUseCase
import com.lfaiska.topredditsreader.presentation.scenes.posts.list.PostsListFragment
import com.lfaiska.topredditsreader.presentation.scenes.posts.list.PostsListViewModel
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertListItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertContains
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import com.schibsted.spain.barista.interaction.BaristaSleepInteractions.sleep
import com.schibsted.spain.barista.interaction.BaristaSwipeRefreshInteractions.refresh
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
@LargeTest
class PostsListTest : KoinTest {

    private lateinit var scenario: FragmentScenario<PostsListFragment>
    private val useCase = mockk<GetTopRedditsPostsUseCase>()
    private val viewModel = PostsListViewModel(useCase)

    @Before
    fun setup() {
        loadKoinModules(module(override = true) {
            viewModel {
                viewModel
            }
        })
    }

    @Test
    fun testLoadPostsSuccessfully() {
        val postListMock = PostList(
            "",
            listOf(
                PostData("kind1", "title1", "thumb1"),
                PostData("kind2", "title2", "thumb2"),
                PostData("kind3", "title3", "thumb3"),
                PostData("kind4", "title4", "thumb4"),
                PostData("kind5", "title5", "thumb5"),
            )
        )

        coEvery {
            useCase.execute(any())
        } coAnswers {
            delay(1000)
            postListMock
        }

        scenario = launchFragmentInContainer(themeResId = R.style.Theme_Topredditsreader)

        assertDisplayed(R.id.loading)

        sleep(1000)

        assertNotDisplayed(R.id.loading)

        assertListItemCount(R.id.posts_list, 5)

        //@TODO miss the thumb verification
        assertDisplayedAtPosition(R.id.posts_list, 0, "title1")
        assertDisplayedAtPosition(R.id.posts_list, 1, "title2")
        assertDisplayedAtPosition(R.id.posts_list, 2, "title3")
        assertDisplayedAtPosition(R.id.posts_list, 3, "title4")
        assertDisplayedAtPosition(R.id.posts_list, 4, "title5")
    }

    @Test
    fun testLoadPostsEmpty() {
        val postListMock = PostList(
            "",
            emptyList()
        )

        coEvery {
            useCase.execute(any())
        } coAnswers {
            delay(1000)
            postListMock
        }

        scenario = launchFragmentInContainer(themeResId = R.style.Theme_Topredditsreader)

        assertDisplayed(R.id.loading)

        sleep(1000)

        assertNotDisplayed(R.id.loading)
        assertDisplayed(R.id.posts_list_empty_message)
        assertContains(R.id.posts_list_empty_message, "No posts were found.")
    }

    @Test
    fun testLoadPostsFailure() {
        coEvery {
            useCase.execute(any())
        } coAnswers {
            delay(1000)
            throw RepositoryException(NullPointerException())
        }

        scenario = launchFragmentInContainer(themeResId = R.style.Theme_Topredditsreader)

        assertDisplayed(R.id.loading)

        sleep(1000)

        assertNotDisplayed(R.id.loading)
        assertDisplayed(R.id.posts_list_error_message)
        assertContains(R.id.posts_list_error_message, "There was an error fetching the posts.")
    }

    @Test
    fun testRefreshListWhenPullSwipe() {
        val postListMock = PostList(
            "",
            listOf(
                PostData("kind1", "title1", "thumb1"),
                PostData("kind2", "title2", "thumb2"),
                PostData("kind3", "title3", "thumb3"),
                PostData("kind4", "title4", "thumb4"),
                PostData("kind5", "title5", "thumb5"),
            )
        )

        coEvery {
            useCase.execute(any())
        } coAnswers {
            delay(1000)
            postListMock
        }

        scenario = launchFragmentInContainer(themeResId = R.style.Theme_Topredditsreader)

        assertDisplayed(R.id.loading)
        sleep(1000)
        assertNotDisplayed(R.id.loading)
        assertListItemCount(R.id.posts_list, 5)
        refresh(R.id.swipe_container)
        assertDisplayed(R.id.loading)
        sleep(1000)
        assertNotDisplayed(R.id.loading)
        assertListItemCount(R.id.posts_list, 5)
    }

    @Test
    fun testUpdatePostsWhenScrollToTheEnd() {
        //@TODO need to build this test
    }
}