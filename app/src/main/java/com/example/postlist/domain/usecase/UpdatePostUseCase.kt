package com.example.postlist.domain.usecase

import com.example.postlist.data.repository.PostRepository
import com.example.postlist.domain.model.Post
import javax.inject.Inject

class UpdatePostUseCase @Inject constructor(
    private val repository: PostRepository
) {
    suspend operator fun invoke(post: Post) = repository.updatePost(post)
}
