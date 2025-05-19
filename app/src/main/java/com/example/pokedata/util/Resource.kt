package com.example.pokedata.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val throwable: Throwable? = null
) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: String, throwable: Throwable? = null): Resource<T>(null, message, throwable)
}