package com.riahi.hiltsampleproject.di

import com.riahi.hiltsampleproject.repository.MainRepository
import com.riahi.hiltsampleproject.retrofit.BlogRetrofit
import com.riahi.hiltsampleproject.retrofit.NetworkMapper
import com.riahi.hiltsampleproject.room.BlogDao
import com.riahi.hiltsampleproject.room.CacheMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        blogDao: BlogDao,
        retrofit: BlogRetrofit,
        cacheMapper: CacheMapper,
        networkMapper: NetworkMapper
    ) : MainRepository {
        return MainRepository(blogDao, retrofit, cacheMapper, networkMapper)
    }
}