package com.example.m2tmdbbarrault.data.remote;

import retrofit2.http.GET;
import retrofit2.http.Query

public interface ITmdbAPI {
    @GET("person/popular")
    suspend fun getPopularPersons(
        @Query("language") language: String = "fr-FR",
        @Query("page") page: Int = 1
    ): PopularPersonsResponse
}