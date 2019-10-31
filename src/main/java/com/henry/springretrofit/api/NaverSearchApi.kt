package com.henry.springretrofit.api

import org.springframework.http.ResponseEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE = "/v1/search/"

interface NaverSearchApi {

    @GET("$BASE/blog")
    fun blogSearch(
        @Query("query") text: String
    ): Call<ResponseEntity<String>>

    /**
     * request type @Body, @Path Example
     *
    @POST("$BASE/news")
    fun newsSearch(
        @Body request: NewsSearchRequest
    ): Call<ResponseEntity<...>>

    @POST("$BASE/cafe/{query}")
    fun put(
        @Path("query") query: String
    ): Call<ResponseEntity<...>>

     **/
}