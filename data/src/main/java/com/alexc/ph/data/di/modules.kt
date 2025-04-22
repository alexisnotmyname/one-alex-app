package com.alexc.ph.data.di

import androidx.room.Room
import com.alexc.ph.data.BuildConfig
import com.alexc.ph.data.db.OneAlexDatabase
import com.alexc.ph.data.network.datasource.MovieDataSource
import com.alexc.ph.data.network.datasource.MovieDataSourceImpl
import com.alexc.ph.data.network.retrofit.MoviesRetrofit
import com.alexc.ph.data.repository.MoviesRepositoryImpl
import com.alexc.ph.data.repository.SearchRepositoryImpl
import com.alexc.ph.data.repository.TodoListRepositoryImpl
import com.alexc.ph.data.repository.TvSeriesRepositoryImpl
import com.alexc.ph.domain.repository.MoviesRepository
import com.alexc.ph.domain.repository.SearchRepository
import com.alexc.ph.domain.repository.TodoListRepository
import com.alexc.ph.domain.repository.TvSeriesRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

val dataModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            OneAlexDatabase::class.java,
            "OneAlexDatabase"
        ).build()
    }

    single { get<OneAlexDatabase>().todoListDao() }

    single {
        Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
    }

    single<Call.Factory> {
        OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                },
        )
            .addInterceptor(Interceptor {
                chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", BuildConfig.AUTHORIZATION)
                    .build()
                chain.proceed(request)
            })
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .callFactory { get<Call.Factory>().newCall(it) }
            .addConverterFactory(
                get<Json>().asConverterFactory("application/json".toMediaType()),
            )
            .build()
    }

    single { get<Retrofit>().create(MoviesRetrofit::class.java)}

    single<MovieDataSource> { MovieDataSourceImpl(get()) }
    single<TodoListRepository> { TodoListRepositoryImpl(get()) }
    single<MoviesRepository> { MoviesRepositoryImpl(get()) }
    single<TvSeriesRepository> { TvSeriesRepositoryImpl(get()) }
    single<SearchRepository> { SearchRepositoryImpl(get()) }

}