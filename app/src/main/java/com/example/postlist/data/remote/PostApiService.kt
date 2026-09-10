package com.example.postlist.data.remote

import com.example.postlist.data.model.PostDto
import retrofit2.http.GET


interface PostApiService {


    @GET("posts")
    suspend fun getPosts(): List<PostDto>
}
