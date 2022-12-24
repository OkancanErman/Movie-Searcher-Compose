package com.oe.moviesearcher2.network

import retrofit2.Response
import retrofit2.http.*

const val api_key = "682b7c68"

interface OMDBApi {

    @GET("/")
    suspend fun searchMovies( @Query("s") searchText: String, @Query("apikey") ombd_api_key: String = api_key): Response<SearchResponse>

    @GET("/")
    suspend fun getMovie( @Query("i") movieId: String, @Query("apikey") ombd_api_key: String = api_key): Response<MovieResponse>


}