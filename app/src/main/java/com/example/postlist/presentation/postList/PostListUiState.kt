package com.example.postlist.presentation.postList

import com.example.postlist.domain.model.Post

data class PostListUiState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null
)
