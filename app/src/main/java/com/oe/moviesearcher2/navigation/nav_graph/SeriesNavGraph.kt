package com.oe.moviesearcher2.navigation.nav_graph

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.oe.moviesearcher2.navigation.SERIES_GRAPH_ROUTE
import com.oe.moviesearcher2.navigation.Screen
import com.oe.moviesearcher2.ui.SeriesInfoScreen
import com.oe.moviesearcher2.ui.SeriesScreen
import com.oe.moviesearcher2.ui.viewmodels.SeriesViewModel

@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.SeriesNavGraph(
    navController: NavHostController
){
    navigation(
        startDestination = Screen.Series.route,
        route = SERIES_GRAPH_ROUTE
    ) {
        composable(
            route = Screen.Series.route
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(route = Screen.Series.route)
            }
            val viewModel = hiltViewModel<SeriesViewModel>(parentEntry)

            SeriesScreen(
                viewModel,
                { navController.navigateUp() }
            ) { seriesId->
                navController.navigate(route = Screen.SeriesInfo.passSeriesId(seriesId))
            }

        }
        composable(
            route = Screen.SeriesInfo.route,
            arguments = listOf(navArgument("movieId") { defaultValue = "batman" })
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(route = Screen.Series.route)
            }
            val viewModel = hiltViewModel<SeriesViewModel>(parentEntry)

            SeriesInfoScreen(
                backStackEntry.arguments?.getString("movieId")!!,
                viewModel
            ) {

            }
        }
    }
}