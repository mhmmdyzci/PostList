package com.example.postlist.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.postlist.data.local.dao.PostDao
import com.example.postlist.data.local.entity.PostEntity

/**
 * Room veritabanı tanımı.
 *
 * @Database annotation:
 *  - entities : veritabanındaki tablolar
 *  - version  : şema değişikliklerinde artırılır (migration gerektirir)
 *  - exportSchema: şema geçmişini dışa aktarır (CI'da schema uyumu için true önerilir)
 */
@Database(
    entities = [PostEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PostDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao

    companion object {
        const val DATABASE_NAME = "post_database"
    }
}
