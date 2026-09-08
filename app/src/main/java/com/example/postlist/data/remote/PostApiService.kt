package com.example.postlist.data.remote

import com.example.postlist.data.model.PostDto
import retrofit2.http.GET

/**
 * Retrofit API arayüzü.
 * Tüm fonksiyonlar suspend ile işaretlenmiş — Coroutine uyumlu.
 * Dönüş tipi [PostDto] — ham API verisi, domain'e mapper ile iletilir.
 */
interface PostApiService {

    /**
     * Tüm post'ları getirir.
     * GET https://jsonplaceholder.typicode.com/posts
     */
    @GET("posts")
    suspend fun getPosts(): List<PostDto>
}
