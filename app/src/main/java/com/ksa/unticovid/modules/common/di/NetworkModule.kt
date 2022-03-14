package com.ksa.unticovid.modules.common.di

import android.content.Context
import com.ksa.unticovid.BuildConfig.DEBUG
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun okHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        cache: Cache
    ): OkHttpClient =
        OkHttpClient.Builder()
            .followRedirects(false)
            .addInterceptor(loggingInterceptor)
            .cache(cache)
            .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        if (DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }

    @Provides
    @Singleton
    fun cacheFile(@ApplicationContext context: Context): File =
        File(context.cacheDir, "okHttp_cache")

    @Provides
    @Singleton
    fun cache(file: File?): Cache =
        Cache(file!!, (10 * 1000 * 1000)) // 10MB cache
}
