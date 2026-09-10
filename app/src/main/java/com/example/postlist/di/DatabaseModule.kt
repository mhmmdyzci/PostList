package com.example.postlist.di

import android.content.Context
import androidx.room.Room
import com.example.postlist.data.local.dao.PostDao
import com.example.postlist.data.local.database.PostDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePostDatabase(
        @ApplicationContext context: Context
    ): PostDatabase = Room.databaseBuilder(
        context,
        PostDatabase::class.java,
        PostDatabase.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun providePostDao(database: PostDatabase): PostDao = database.postDao()
}
