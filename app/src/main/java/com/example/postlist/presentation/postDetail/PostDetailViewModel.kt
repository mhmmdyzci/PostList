package com.example.postlist.presentation.postDetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postlist.domain.usecase.GetPostByIdUseCase
import com.example.postlist.domain.usecase.UpdatePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val updatePostUseCase: UpdatePostUseCase
) : ViewModel() {

    private val postId: Int = PostDetailFragmentArgs
        .fromSavedStateHandle(savedStateHandle)
        .postId

    private val _uiState = MutableStateFlow(PostDetailUiState())
    val uiState = _uiState.asStateFlow()

    private val _saveCompleted = MutableSharedFlow<Unit>()
    val saveCompleted: SharedFlow<Unit> = _saveCompleted.asSharedFlow()



    init {
        loadPost()
    }

    private fun loadPost() {
        viewModelScope.launch {
            try {
                val post = getPostByIdUseCase(postId)
                _uiState.update {
                    it.copy(
                        post = post,
                        isLoading = false,
                        errorMessage = if (post == null) "Post not found" else null
                    )
                }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    fun updatePost(title: String, body: String) {
        if (_uiState.value.isSaving) return

        val normalizedTitle = title.trim()
        val normalizedBody = body.trim()
        val validationError = when {
            normalizedTitle.isEmpty() -> PostDetailValidationError.EMPTY_TITLE
            normalizedBody.isEmpty() -> PostDetailValidationError.EMPTY_BODY
            else -> null
        }

        if (validationError != null) {
            _uiState.update {
                it.copy(validationError = validationError, errorMessage = null)
            }
            return
        }

        val currentPost = _uiState.value.post ?: return
        _uiState.update {
            it.copy(
                isSaving = true,
                validationError = null,
                errorMessage = null
            )
        }

        viewModelScope.launch {
            try {
                updatePostUseCase(
                    currentPost.copy(
                        title = normalizedTitle,
                        body = normalizedBody
                    )
                )
                _saveCompleted.emit(Unit)
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(errorMessage = exception.message ?: "Unknown error")
                }
            } finally {
                _uiState.update { it.copy(isSaving = false) }
            }
        }
    }
}
