package com.lfaiska.topredditsreader.di

import com.lfaiska.topredditsreader.data.repository.RedditRepository
import com.lfaiska.topredditsreader.domain.usecases.GetTopRedditsPostsUseCase
import com.lfaiska.topredditsreader.domain.usecases.GetTopRedditsPostsUseCaseImpl
import org.koin.dsl.module

val appModule = module {
    single { provideGetTopRedditsPostsUseCase(get()) }
}

fun provideGetTopRedditsPostsUseCase(repository: RedditRepository) : GetTopRedditsPostsUseCase {
    return GetTopRedditsPostsUseCaseImpl(repository)
}