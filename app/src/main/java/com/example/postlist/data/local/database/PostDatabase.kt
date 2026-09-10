package com.example.postlist.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.postlist.data.local.dao.PostDao
import com.example.postlist.data.local.entity.PostEntity


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
