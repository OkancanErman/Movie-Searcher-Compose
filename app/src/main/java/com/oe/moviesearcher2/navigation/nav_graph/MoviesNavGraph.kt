package com.oe.moviesearcher2.navigation.nav_graph

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.oe.moviesearcher2.navigation.MOVIES_GRAPH_ROUTE
import com.oe.moviesearcher2.navigation.Screen
import com.oe.moviesearcher2.ui.MoviesScreen
import com.oe.moviesearcher2.ui.infoscreens.MovieInfoScreen
import com.oe.moviesearcher2.ui.viewmodels.MoviesViewModel

@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.MoviesNavGraph(
    navController: NavHostController
){
    navigation(
        startDestination = Screen.Movies.route,
        route = MOVIES_GRAPH_ROUTE
    ) {
        composable(
            route = Screen.Movies.route
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(route = Screen.Movies.route)
            }
            val viewModel = hiltViewModel<MoviesViewModel>(parentEntry)

            MoviesScreen(
                viewModel,
                { navController.navigateUp() }
            ) { movieId->
                navController.navigate(route = Screen.MovieInfo.passMovieId(movieId))
            }
        }
        composable(
            route = Screen.MovieInfo.route,
            arguments = listOf(navArgument("movieId") { defaultValue = 0 })
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(route = Screen.Movies.route)
            }
            val viewModel = hiltViewModel<MoviesViewModel>(parentEntry)

            MovieInfoScreen(
                backStackEntry.arguments?.getInt("movieId")!!,
                viewModel,
                { navController.navigateUp() }
            ) {

            }
        }
    }
}