package com.example.postlist.data.model

import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object — API'den gelen ham JSON verisini temsil eder.
 * Tüm alanlar nullable: API yanıtı eksik veya null alan içerebilir.
 * Default değer ataması [PostMapper] içinde yapılır.
 */
data class PostDto(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("userId")
    val userId: Int? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("body")
    val body: String? = null
)
