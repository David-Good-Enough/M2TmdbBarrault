package com.example.m2tmdbbarrault.data.remote;

import retrofit2.http.GET;
import retrofit2.http.Query

public interface ITmdbAPI {
    @GET("person/popular")
    suspend fun getPopularPersons(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "fr-FR"
    ) : PopularPersonsResponse
}