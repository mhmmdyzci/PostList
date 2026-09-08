package com.example.postlist.domain.usecase

import com.example.postlist.data.repository.PostRepository
import com.example.postlist.domain.model.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Room'daki post listesini sürekli gözlemleyen UseCase.
 */
class GetPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(): Flow<List<Post>> = repository.observePosts()
}
