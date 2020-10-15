package com.riahi.hiltsampleproject.repository

import com.riahi.hiltsampleproject.model.Blog
import com.riahi.hiltsampleproject.retrofit.BlogRetrofit
import com.riahi.hiltsampleproject.retrofit.NetworkMapper
import com.riahi.hiltsampleproject.room.BlogDao
import com.riahi.hiltsampleproject.room.CacheMapper
import com.riahi.hiltsampleproject.util.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MainRepository
constructor(
    private val blogDao: BlogDao,
    private val blogRetrofit: BlogRetrofit,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {
    suspend fun getBlogs(): Flow<DataState<List<Blog>>> = flow {
        emit(DataState.Loading)
        delay(1000)
        try {
            val networkBlogs = blogRetrofit.get()
            val blogs = networkMapper.mapFromEntityList(networkBlogs)
            for (blog in blogs) {
                blogDao.insert(cacheMapper.mapToEntity(blog))
            }
            val cachedBlog = blogDao.get()
            emit(DataState.Success(cacheMapper.mapFromEntityList(cachedBlog)))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}