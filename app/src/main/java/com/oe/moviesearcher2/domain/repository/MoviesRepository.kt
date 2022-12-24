package com.oe.moviesearcher2.domain.repository

import com.oe.moviesearcher2.network.MovieResponse
import com.oe.moviesearcher2.network.OMDBApi
import com.oe.moviesearcher2.network.SearchResponse
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor(val api: OMDBApi) {

    suspend fun searchMovies(searchText: String) : Response<SearchResponse> {
        return api.searchMovies(searchText)
    }

    suspend fun getMovie(movie_id: String) : Response<MovieResponse> {
        return api.getMovie(movie_id)
    }
}