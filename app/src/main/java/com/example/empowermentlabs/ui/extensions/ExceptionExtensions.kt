package com.example.empowermentlabs.ui.extensions

import com.example.empowermentlabs.data.remote.network.Resource
import com.example.empowermentlabs.data.remote.network.ResponseHandler

inline fun <T> tryOrDefault(f: () -> T, defaultValue: T): T {
    return try {
        f()
    } catch (e: Exception) {
        e.printStackTrace()
        defaultValue
    }
}

inline fun <T> tryOrDefaultException(f: () -> Any, handler: ResponseHandler): Resource<Any> {
    return try {
        handler.handleSuccess(f())
    } catch (e: Exception) {
        e.printStackTrace()
        handler.handleException(e)
    }
}

inline fun tryOrPrintException(f: () -> Unit) {
    return try {
        f()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}