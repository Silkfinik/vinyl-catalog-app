package com.silkfinik.vinylcatalog.data.remote

import com.silkfinik.vinylcatalog.data.remote.dto.DiscogsSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscogsApi {
    @GET("database/search")
    suspend fun searchReleases(
        @Query("q") query: String,
        @Query("type") type: String = "release",
        @Query("genre") genre: String? = null,
        @Query("per_page") perPage: Int = 20
    ): DiscogsSearchResponse
}
