package com.example.postlist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.postlist.data.local.entity.PostEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PostDao {


    @Query("SELECT * FROM posts WHERE isDeleted = 0 ORDER BY id")
    fun observePosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE id = :postId AND isDeleted = 0 LIMIT 1")
    suspend fun getPost(postId: Int): PostEntity?

    @Query("SELECT * FROM posts")
    suspend fun getAllPosts(): List<PostEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Query(
        "UPDATE posts " +
            "SET title = :title, body = :body, " +
            "isLocallyModified = 1, isDeleted = 0 " +
            "WHERE id = :postId"
    )
    suspend fun updatePost(postId: Int, title: String, body: String)

    @Query(
        "UPDATE posts " +
            "SET isDeleted = 1, isLocallyModified = 1 " +
            "WHERE id = :postId"
    )
    suspend fun deletePost(postId: Int)

    @Query("DELETE FROM posts WHERE id = :postId")
    suspend fun permanentlyDeletePost(postId: Int)

    @Transaction
    suspend fun syncPosts(remotePosts: List<PostEntity>) {
        val localPosts = getAllPosts()

        val postsToUpsert = mutableListOf<PostEntity>()

        remotePosts.forEach { remotePost ->

            val localPost = localPosts.find { it.id == remotePost.id }
            if (localPost != null && (localPost.isLocallyModified || localPost.isDeleted)) {
                postsToUpsert.add(localPost)
            } else {
                postsToUpsert.add(remotePost.copy(isLocallyModified = false, isDeleted = false))
            }
        }

        if (postsToUpsert.isNotEmpty()) {
            insertPosts(postsToUpsert)
        }


        localPosts.forEach { localPost ->

            val existsInRemote = remotePosts.any { it.id == localPost.id }
            if (!existsInRemote && !localPost.isLocallyModified && !localPost.isDeleted) {
                permanentlyDeletePost(localPost.id)
            }
        }
    }
}
