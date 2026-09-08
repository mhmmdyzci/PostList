package com.example.postlist.domain.model

/**
 * Domain Model — uygulamanın iş mantığında kullanılan temiz veri modeli.
 * Herhangi bir framework / kütüphane bağımlılığı içermez (Gson, Room vb.)
 * UseCase ve ViewModel bu modeli kullanır.
 */
data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)
