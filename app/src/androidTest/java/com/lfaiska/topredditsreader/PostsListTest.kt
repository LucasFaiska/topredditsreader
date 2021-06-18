package com.lfaiska.topredditsreader

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.lfaiska.topredditsreader.presentation.scenes.posts.list.PostsListFragment
import com.lfaiska.topredditsreader.presentation.scenes.posts.list.PostsListViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
@LargeTest
class PostsListTest : KoinTest {

    private lateinit var scenario: FragmentScenario<PostsListFragment>
    private val viewModel = mockk<PostsListViewModel>(relaxed = true, relaxUnitFun = true)

    private val isEmptyPostsMock = MutableLiveData<Boolean>()
    private val hasLoadErrorMock = MutableLiveData<Boolean>()

    @Before
    fun setup() {
        every { viewModel.isEmptyPosts } returns isEmptyPostsMock
        every { viewModel.hasLoadPostsFailure } returns hasLoadErrorMock

        loadKoinModules(module(override = true) {
            viewModel {
                viewModel
            }
        })

        scenario = launchFragmentInContainer(themeResId = R.style.Theme_Topredditsreader)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testPostsListScreenShowingCorrectMessages() {
        onView(ViewMatchers.withId(R.id.posts_list_empty_message)).check(
            matches(
                withText(
                    "No posts were found."
                )
            )
        )

        onView(ViewMatchers.withId(R.id.posts_list_error_message)).check(
            matches(
                withText(
                    "There was an error fetching the posts."
                )
            )
        )
    }

    @Test
    fun testPostsListScreenShowCorrectMessageWhenHasNoPost() {
        isEmptyPostsMock.postValue(true)

        onView(ViewMatchers.withId(R.id.swipe_container)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.loading)).check(matches(not(isDisplayed())))
        onView(ViewMatchers.withId(R.id.posts_list_error_message)).check(matches(not(isDisplayed())))
        onView(ViewMatchers.withId(R.id.posts_list_empty_message)).check(matches(isDisplayed()))
    }

    @Test
    fun testPostsListScreenShowCorrectMessageWhenHasLoadingError() {
        hasLoadErrorMock.postValue(true)

        onView(ViewMatchers.withId(R.id.swipe_container)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.loading)).check(matches(not(isDisplayed())))
        onView(ViewMatchers.withId(R.id.posts_list_error_message)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.posts_list_empty_message)).check(matches(not(isDisplayed())))
    }
}