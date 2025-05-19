package com.example.pokedata.common.error

import com.example.pokedata.data.model.ErrorModel

interface ErrorHandler {
    fun handleError(throwable: Throwable): ErrorModel
}