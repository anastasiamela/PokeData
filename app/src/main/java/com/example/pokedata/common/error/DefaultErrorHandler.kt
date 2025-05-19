package com.example.pokedata.common.error

import com.example.pokedata.common.exception.NotFoundException
import com.example.pokedata.data.model.ErrorModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DefaultErrorHandler @Inject constructor() : ErrorHandler {
    override fun handleError(throwable: Throwable): ErrorModel {
        return when (throwable) {
            is IOException -> ErrorModel(
                title = "Please check your connection.",
                shouldShowRetry = true
            )
            is HttpException -> when (throwable.code()) {
                404 -> ErrorModel(
                    title = "Oops! No results found",
                    shouldShowRetry = false
                )
                else -> ErrorModel(
                    title = "Something went wrong.",
                    shouldShowRetry = true
                )
            }
            is NotFoundException -> ErrorModel(
                title = "Oops! No results found.",
                shouldShowRetry = false
            )
            else -> ErrorModel(
                title = "Something went wrong",
                shouldShowRetry = true
            )
        }
    }
}
