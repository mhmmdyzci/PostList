package com.example.postlist.data.mapper

import com.example.postlist.data.model.PostDto
import com.example.postlist.data.local.entity.PostEntity
import com.example.postlist.domain.model.Post

/**
 * PostDto → Post domain modeline dönüşüm.
 *
 * Null gelen alanlar için default değer ataması burada yapılır.
 * Domain modeli her zaman geçerli (non-null) bir state'te olur.
 *
 *  id     → 0       (geçersiz id için)
 *  userId → 0       (geçersiz kullanıcı için)
 *  title  → ""      (başlık yoksa boş string)
 *  body   → ""      (içerik yoksa boş string)
 */
fun PostDto.toDomain(): Post = Post(
    id = id ?: 0,
    userId = userId ?: 0,
    title = title.orEmpty(),
    body = body.orEmpty()
)

fun Post.toEntity(): PostEntity = PostEntity(
    id = id,
    userId = userId,
    title = title,
    body = body
)

fun PostEntity.toDomain(): Post = Post(
    id = id,
    userId = userId,
    title = title,
    body = body
)
