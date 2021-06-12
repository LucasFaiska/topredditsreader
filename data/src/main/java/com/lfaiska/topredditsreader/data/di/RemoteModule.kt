package com.lfaiska.topredditsreader.data.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import com.lfaiska.topredditsreader.data.BuildConfig
import com.lfaiska.topredditsreader.data.remote.service.RedditService
import com.lfaiska.topredditsreader.data.repository.RedditRepository
import com.lfaiska.topredditsreader.data.repository.RedditRepositoryImpl
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single { provideGson() }
    single { provideHttpClient() }
    single { provideRetrofit(get(), get()) }
    single { provideRedditService(get()) }
    single { provideRedditRepository(get()) }
}

fun provideGson(): Gson {
    return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create()
}

fun provideHttpClient(): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()
    return okHttpClientBuilder.build()
}

fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(factory))
        .client(client)
        .build()
}

fun provideRedditService(retrofit: Retrofit): RedditService {
    return retrofit.create(RedditService::class.java)
}

fun provideRedditRepository(service: RedditService) : RedditRepository {
    return RedditRepositoryImpl(service)
}