package com.example.postlist.presentation.postDetail

import com.example.postlist.domain.model.Post

enum class PostDetailValidationError {
    EMPTY_TITLE,
    EMPTY_BODY
}

data class PostDetailUiState(
    val post: Post? = null,
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val validationError: PostDetailValidationError? = null,
    val errorMessage: String? = null
)
