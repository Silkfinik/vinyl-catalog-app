package com.silkfinik.vinylcatalog.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Discogs token=$token")
            .addHeader("User-Agent", "VinylCatalogApp/1.0")
            .build()
        return chain.proceed(newRequest)
    }
}
