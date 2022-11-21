package com.example.empowermentlabs.data.remote.network

import com.example.empowermentlabs.utils.APPLICATION_JSON
import com.example.empowermentlabs.utils.CONTENT_TYPE
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .header(CONTENT_TYPE, APPLICATION_JSON)
            .build()
        return chain.proceed(request)
    }
}