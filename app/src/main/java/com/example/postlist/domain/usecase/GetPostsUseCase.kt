package com.example.postlist.domain.usecase

import com.example.postlist.data.repository.PostRepository
import com.example.postlist.domain.model.Post
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetPostsUseCase @Inject constructor(
    private val repository: PostRepository
) {
    operator fun invoke(): Flow<List<Post>> = repository.observePosts()
}
