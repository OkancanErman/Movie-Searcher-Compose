package com.oe.moviesearcher2.navigation

const val ROOT_GRAPH_ROUTE = "root"
const val MOVIES_GRAPH_ROUTE = "movie_graph"
const val SERIES_GRAPH_ROUTE = "series_graph"
const val MUSICS_GRAPH_ROUTE = "musics_graph"

sealed class Screen(val route: String) {
    object Main : Screen(route = "main_screen")
    object Movies: Screen(route = "movies_screen")
    object MovieInfo : Screen(route = "movie_info_screen?movieId={movieId}") {
        fun passMovieId(
            movieId: Int = 0
        ): String {
            return "movie_info_screen?movieId=$movieId"
        }
    }
    object Series: Screen(route = "series_screen")
    object SeriesInfo : Screen(route = "series_info_screen?seriesId={seriesId}") {
        fun passSeriesId(
            seriesId: String = "1"
        ): String {
            return "series_info_screen?seriesId=$seriesId"
        }
    }
    object Musics: Screen(route = "musics_screen")
    object MusicInfo : Screen(route = "music_info_screen?musicId={musicId}") {
        fun passMusicId(
            musicId: String = "1"
        ): String {
            return "music_info_screen?musicId=$musicId"
        }
    }
}