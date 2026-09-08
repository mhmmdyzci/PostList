package com.example.postlist.util


sealed class Resource<out T> {

    object Loading : Resource<Nothing>()

    data class Success<out T>(val data: T) : Resource<T>()

    /** Operasyon başarısız, [message] hata mesajını içerir. UI katmanı null ise R.string.error_unknown kullanır. */
    data class Error(val message: String?) : Resource<Nothing>()
}
