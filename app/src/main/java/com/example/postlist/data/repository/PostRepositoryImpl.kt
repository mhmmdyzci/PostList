package com.example.postlist.data.repository

import com.example.postlist.data.local.dao.PostDao
import com.example.postlist.data.mapper.toDomain
import com.example.postlist.data.mapper.toEntity
import com.example.postlist.data.remote.PostApiService
import com.example.postlist.domain.model.Post
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class PostRepositoryImpl @Inject constructor(
    private val apiService: PostApiService,
    private val postDao: PostDao
) : PostRepository {

    override fun observePosts(): Flow<List<Post>> =
        postDao.observePosts().map { entities ->
            entities.map { it.toDomain() }
        }

    override suspend fun refreshPosts() {
        val posts = apiService.getPosts().map { it.toDomain() }
        postDao.clearPosts()
        postDao.insertPosts(posts.map { it.toEntity() })
    }

    override suspend fun deletePost(postId: Int) {
        postDao.deletePost(postId)
    }
}
