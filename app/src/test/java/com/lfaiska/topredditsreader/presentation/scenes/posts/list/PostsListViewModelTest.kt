package com.lfaiska.topredditsreader.presentation.scenes.posts.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lfaiska.topredditsreader.CoroutinesMainDispatcherRule
import com.lfaiska.topredditsreader.data.repository.RepositoryException
import com.lfaiska.topredditsreader.domain.model.PostData
import com.lfaiska.topredditsreader.domain.model.PostList
import com.lfaiska.topredditsreader.domain.usecase.GetTopRedditsPostsUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PostsListViewModelTest {

    lateinit var viewModel: PostsListViewModel

    @MockK
    lateinit var getTopRedditsPostsUseCase: GetTopRedditsPostsUseCase

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var coroutinesMainDispatcherRule = CoroutinesMainDispatcherRule(testDispatcher)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = PostsListViewModel(getTopRedditsPostsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when view model load posts successfully from usecase with a list of posts should emit the posts list in observer`() {
        val mockedPostsList = PostList("", listOf(PostData("kind1", "title1", "thumb1")))
        val isLoadingPostsObserver = mockk<Observer<Boolean>>(relaxUnitFun = true)
        val postsListObserver = mockk<Observer<PostList>>(relaxUnitFun = true)

        runBlocking {
            coEvery {
                getTopRedditsPostsUseCase.execute(any())
            } returns mockedPostsList

            viewModel.isLoadingPosts.observeForever(isLoadingPostsObserver)
            viewModel.postsList.observeForever(postsListObserver)
            viewModel.loadPosts()

            coVerify(Ordering.SEQUENCE) {
                isLoadingPostsObserver.onChanged(true)
                getTopRedditsPostsUseCase.execute(any())
                postsListObserver.onChanged(mockedPostsList)
                isLoadingPostsObserver.onChanged(false)
            }
        }
    }

    @Test
    fun `when view model load posts successfully from usecase with a empty list of posts should emit the posts list in observer`() {
        val mockedPostsList = PostList("", emptyList())
        val isLoadingPostsObserver = mockk<Observer<Boolean>>(relaxUnitFun = true)
        val isEmptyPostsObserver = mockk<Observer<Boolean>>(relaxUnitFun = true)

        runBlocking {
            coEvery {
                getTopRedditsPostsUseCase.execute(any())
            } returns mockedPostsList

            viewModel.isLoadingPosts.observeForever(isLoadingPostsObserver)
            viewModel.isEmptyPosts.observeForever(isEmptyPostsObserver)
            viewModel.loadPosts()

            coVerify(Ordering.SEQUENCE) {
                isEmptyPostsObserver.onChanged(false)
                isLoadingPostsObserver.onChanged(true)
                getTopRedditsPostsUseCase.execute(any())
                isEmptyPostsObserver.onChanged(true)
                isLoadingPostsObserver.onChanged(false)
            }
        }
    }

    @Test
    fun `when view model load posts fails from usecase should emit the error in observer`() {
        val isLoadingPostsObserver = mockk<Observer<Boolean>>(relaxUnitFun = true)
        val hasLoadPostsFailureObserver = mockk<Observer<Boolean>>(relaxUnitFun = true)

        runBlocking {
            coEvery {
                getTopRedditsPostsUseCase.execute(any())
            } throws RepositoryException(mockk())

            viewModel.isLoadingPosts.observeForever(isLoadingPostsObserver)
            viewModel.hasLoadPostsFailure.observeForever(hasLoadPostsFailureObserver)
            viewModel.loadPosts()

            coVerify(Ordering.SEQUENCE) {
                hasLoadPostsFailureObserver.onChanged(false)
                isLoadingPostsObserver.onChanged(true)
                getTopRedditsPostsUseCase.execute(any())
                hasLoadPostsFailureObserver.onChanged(true)
                isLoadingPostsObserver.onChanged(false)
            }
        }
    }

    @Test
    fun `when view model update posts successfully from usecase with a list of posts should emit the posts list in observer`() {
        val mockedPostsList = PostList("bla", listOf(PostData("kind1", "title1", "thumb1")))
        val isLoadingPostsObserver = mockk<Observer<Boolean>>(relaxUnitFun = true)
        val postsListObserver = mockk<Observer<PostList>>(relaxUnitFun = true)

        runBlocking {
            coEvery {
                getTopRedditsPostsUseCase.execute(any())
            } returns mockedPostsList

            viewModel.isLoadingPosts.observeForever(isLoadingPostsObserver)
            viewModel.postsList.observeForever(postsListObserver)

            viewModel.loadPosts()
            viewModel.updatePosts()

            coVerify(Ordering.SEQUENCE) {
                isLoadingPostsObserver.onChanged(true)
                getTopRedditsPostsUseCase.execute(any())
                postsListObserver.onChanged(mockedPostsList)
                isLoadingPostsObserver.onChanged(false)
                isLoadingPostsObserver.onChanged(true)
                getTopRedditsPostsUseCase.execute(any())
                postsListObserver.onChanged(mockedPostsList)
                isLoadingPostsObserver.onChanged(false)
            }
        }
    }

    @Test
    fun `when view model update posts fails from usecase should emit the error in observer`() {
        val currentPostsList = PostList("after0", listOf(PostData("kind0", "title0", "thumb0")))
        val isLoadingPostsObserver = mockk<Observer<Boolean>>(relaxUnitFun = true)
        val hasUpdatePostsFailureObserver = mockk<Observer<Boolean>>(relaxUnitFun = true)

        runBlocking {
            coEvery {
                getTopRedditsPostsUseCase.execute(any())
            } returns currentPostsList

            coEvery {
                getTopRedditsPostsUseCase.execute("after0")
            } throws RepositoryException(mockk())

            viewModel.isLoadingPosts.observeForever(isLoadingPostsObserver)
            viewModel.hasUpdatePostsFailure.observeForever(hasUpdatePostsFailureObserver)

            viewModel.loadPosts()
            viewModel.updatePosts()

            coVerify(Ordering.SEQUENCE) {
                hasUpdatePostsFailureObserver.onChanged(false)
                isLoadingPostsObserver.onChanged(true)
                getTopRedditsPostsUseCase.execute(any())
                isLoadingPostsObserver.onChanged(false)
                isLoadingPostsObserver.onChanged(true)
                getTopRedditsPostsUseCase.execute(any())
                hasUpdatePostsFailureObserver.onChanged(true)
                isLoadingPostsObserver.onChanged(false)
            }
        }
    }
}