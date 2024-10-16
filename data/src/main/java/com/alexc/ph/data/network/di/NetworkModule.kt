package com.alexc.ph.data.network.di

import com.alexc.ph.data.BuildConfig
import com.alexc.ph.data.network.datasource.MovieDataSource
import com.alexc.ph.data.network.datasource.MovieDataSourceImpl
import com.alexc.ph.data.network.retrofit.MoviesRetrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", BuildConfig.AUTHORIZATION)
                .build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(
        authInterceptor: Interceptor
    ): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                },
        )
        .addInterceptor(authInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        networkJson: Json,
        okhttpCallFactory: dagger.Lazy<Call.Factory>,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesRetrofit(
        retrofit: Retrofit
    ): MoviesRetrofit {
        return retrofit.create(MoviesRetrofit::class.java)
    }
    @Provides
    @Singleton
    fun providesMovieDataSource(
        moviesRetrofit: MoviesRetrofit
    ): MovieDataSource {
        return MovieDataSourceImpl(moviesRetrofit)
    }

}