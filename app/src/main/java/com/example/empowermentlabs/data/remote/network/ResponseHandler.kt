package com.example.empowermentlabs.data.remote.network

import com.example.empowermentlabs.utils.EMPTY_STRING
import retrofit2.HttpException

open class ResponseHandler {

    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(exception: Exception): Resource<T> {
        return when (exception) {
            is HttpException -> Resource.error(
                getErrorMessage(
                    exception.code(),
                    exception.response()?.message().orEmpty()
                ), null
            )
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE, EMPTY_STRING), null)
        }
    }

    private fun getErrorMessage(code: Int, message: String): String {
        return when (code) {
            401 -> "No está autorizado para esta acción. Comuniquese con soporte."
            404 -> "No hay resultados para su búsqueda."
            else -> "$code: $message"
        }
    }
}