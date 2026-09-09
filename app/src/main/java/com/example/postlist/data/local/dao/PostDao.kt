package com.example.postlist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.postlist.data.local.entity.PostEntity
import kotlinx.coroutines.flow.Flow

/**
 * Room Data Access Object — posts tablosu için CRUD operasyonları.
 */
@Dao
interface PostDao {

    /**
     * Room tablosunu gözlemler. Her insert/update sonrasında yeni liste yayınlanır.
     */
    @Query("SELECT * FROM posts WHERE isDeleted = 0 ORDER BY id")
    fun observePosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE id = :postId AND isDeleted = 0 LIMIT 1")
    suspend fun getPost(postId: Int): PostEntity?

    @Query("SELECT * FROM posts")
    suspend fun getAllPosts(): List<PostEntity>

    /**
     * Post listesini tabloya ekler.
     * REPLACE stratejisi: aynı id varsa üzerine yazar (güncelleme gibi davranır).
     */
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
        val localPosts = getAllPosts().associateBy { it.id }
        val remoteIds = remotePosts.mapTo(mutableSetOf()) { it.id }

        val postsToUpsert = remotePosts.map { remotePost ->
            val localPost = localPosts[remotePost.id]

            if (localPost?.isLocallyModified == true || localPost?.isDeleted == true) {
                localPost
            } else {
                remotePost.copy(
                    isLocallyModified = false,
                    isDeleted = false
                )
            }
        }

        if (postsToUpsert.isNotEmpty()) {
            insertPosts(postsToUpsert)
        }

        localPosts.values
            .filter { localPost ->
                localPost.id !in remoteIds &&
                    !localPost.isLocallyModified &&
                    !localPost.isDeleted
            }
            .forEach { localPost -> permanentlyDeletePost(localPost.id) }
    }
}
