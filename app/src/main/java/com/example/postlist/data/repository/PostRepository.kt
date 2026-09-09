package com.example.postlist.data.repository

import com.example.postlist.domain.model.Post
import kotlinx.coroutines.flow.Flow


interface PostRepository {

    fun observePosts(): Flow<List<Post>>

    suspend fun refreshPosts()

    suspend fun deletePost(postId: Int)
}
