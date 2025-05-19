package com.example.pokedata.common.error

import com.example.pokedata.common.exception.NotFoundException
import com.example.pokedata.data.model.ErrorModel
import com.example.pokedata.util.Constants.NO_CONNECTION_MESSAGE
import com.example.pokedata.util.Constants.NO_RESULTS_FOUND_MESSAGE
import com.example.pokedata.util.Constants.UNKNOWN_ERROR_MESSAGE
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DefaultErrorHandler @Inject constructor() : ErrorHandler {
    override fun handleError(throwable: Throwable): ErrorModel {
        return when (throwable) {
            is IOException -> ErrorModel(
                title = NO_CONNECTION_MESSAGE,
                shouldShowRetry = true
            )
            is HttpException -> when (throwable.code()) {
                404 -> ErrorModel(
                    title = NO_RESULTS_FOUND_MESSAGE,
                    shouldShowRetry = false
                )
                else -> ErrorModel(
                    title = UNKNOWN_ERROR_MESSAGE,
                    shouldShowRetry = true
                )
            }
            is NotFoundException -> ErrorModel(
                title = NO_RESULTS_FOUND_MESSAGE,
                shouldShowRetry = false
            )
            else -> ErrorModel(
                title = UNKNOWN_ERROR_MESSAGE,
                shouldShowRetry = true
            )
        }
    }
}
