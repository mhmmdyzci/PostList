package com.example.postlist.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
    @Query("SELECT * FROM posts ORDER BY id")
    fun observePosts(): Flow<List<PostEntity>>

    /**
     * Post listesini tabloya ekler.
     * REPLACE stratejisi: aynı id varsa üzerine yazar (güncelleme gibi davranır).
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    /**
     * Tüm post'ları siler.
     * Yeni veri çekilmeden önce temizleme için kullanılır.
     */
    @Query("DELETE FROM posts")
    suspend fun clearPosts()

    @Query("DELETE FROM posts WHERE id = :postId")
    suspend fun deletePost(postId: Int)
}
