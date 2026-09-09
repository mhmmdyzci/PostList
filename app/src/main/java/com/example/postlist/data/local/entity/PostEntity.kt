package com.example.postlist.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Room veritabanı entity'si.
 * API'den gelen veri önce bu formata dönüştürülür, ardından Room'a kaydedilir.
 * UI her zaman Room'dan okur (offline-first).
 */
@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
    val isLocallyModified: Boolean = false,
    val isDeleted: Boolean = false
)
