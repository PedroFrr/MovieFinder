package com.raywenderlich.example.moviesapp.di

import android.net.ConnectivityManager
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.raywenderlich.example.moviesapp.networking.BASE_URL
import com.raywenderlich.example.moviesapp.networking.NetworkStatusChecker
import com.raywenderlich.example.moviesapp.networking.PokemonApi
import com.raywenderlich.example.moviesapp.networking.PokemonApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit


val networkModule = module {
    single {
        okHttp()
    }

    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        interceptor
    }

    single {
        retrofit(BASE_URL)
    }

    single {
        get<Retrofit>().create(PokemonApiService::class.java)
    }

    single {
        PokemonApi(get())
    }

}

private fun okHttp() = OkHttpClient.Builder()
    .build()

private fun retrofit(baseUrl: String) = Retrofit.Builder()
    .callFactory(OkHttpClient.Builder().build())
    .baseUrl(baseUrl)
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))  // 6
    .build()
