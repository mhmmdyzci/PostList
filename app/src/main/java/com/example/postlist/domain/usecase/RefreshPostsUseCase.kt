package com.example.postlist.domain.usecase

import com.example.postlist.data.repository.PostRepository
import javax.inject.Inject

class RefreshPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke() = repository.refreshPosts()
}
