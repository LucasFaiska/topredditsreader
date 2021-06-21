package com.lfaiska.topredditsreader.di

import com.lfaiska.topredditsreader.data.repository.RedditRepository
import com.lfaiska.topredditsreader.domain.usecase.GetTopRedditsPostsUseCase
import com.lfaiska.topredditsreader.domain.usecase.GetTopRedditsPostsUseCaseImpl
import com.lfaiska.topredditsreader.presentation.scenes.posts.list.PostsListViewModel
import com.lfaiska.topredditsreader.presentation.scenes.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { SplashViewModel() }
    viewModel { PostsListViewModel(get()) }
    single { provideGetTopRedditsPostsUseCase(get()) }
}

fun provideGetTopRedditsPostsUseCase(repository: RedditRepository) : GetTopRedditsPostsUseCase {
    return GetTopRedditsPostsUseCaseImpl(repository)
}