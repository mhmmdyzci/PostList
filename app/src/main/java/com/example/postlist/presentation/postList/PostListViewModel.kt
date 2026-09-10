package com.example.postlist.presentation.postList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postlist.domain.model.Post
import com.example.postlist.domain.usecase.DeletePostUseCase
import com.example.postlist.domain.usecase.GetPostsUseCase
import com.example.postlist.domain.usecase.RefreshPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val refreshPostsUseCase: RefreshPostsUseCase,
    private val deletePostUseCase: DeletePostUseCase
) : ViewModel() {


    private val _uiState = MutableStateFlow(PostListUiState(isLoading = true))
    val uiState: StateFlow<PostListUiState> = _uiState.asStateFlow()



    init {
        observePosts()
        refresh()
    }

    private fun observePosts() {
        viewModelScope.launch {
            getPostsUseCase().collect { currentPosts ->
                _uiState.update { state ->
                    state.copy(
                        posts = currentPosts,
                        isLoading = state.isRefreshing && currentPosts.isEmpty()
                    )
                }
            }
        }
    }

    fun refresh() {
        if (_uiState.value.isRefreshing) return
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = it.posts.isEmpty(),
                    isRefreshing = true,
                    errorMessage = null
                )
            }
            try {
                refreshPostsUseCase()
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(errorMessage = exception.message ?: "Unknown error")
                }
            } finally {
                _uiState.update {
                    it.copy(isLoading = false, isRefreshing = false)
                }
            }
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch {
            try {
                deletePostUseCase(post.id)
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(errorMessage = exception.message ?: "Unknown error")
                }
            }
        }
    }


}