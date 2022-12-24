package com.oe.moviesearcher2.navigation.nav_graph

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.oe.moviesearcher2.navigation.MUSICS_GRAPH_ROUTE
import com.oe.moviesearcher2.navigation.Screen
import com.oe.moviesearcher2.ui.MusicInfoScreen
import com.oe.moviesearcher2.ui.MusicsScreen
import com.oe.moviesearcher2.ui.viewmodels.MusicsViewModel

@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.MusicsNavGraph(
    navController: NavHostController
){
    navigation(
        startDestination = Screen.Musics.route,
        route = MUSICS_GRAPH_ROUTE
    ) {
        composable(
            route = Screen.Musics.route
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(route = Screen.Musics.route)
            }
            val viewModel = hiltViewModel<MusicsViewModel>(parentEntry)

            MusicsScreen(
                viewModel,
                { navController.navigateUp() }
            ) { musicId->
                navController.navigate(route = Screen.MusicInfo.passMusicId(musicId))
            }
        }
        composable(
            route = Screen.MusicInfo.route,
            arguments = listOf(navArgument("movieId") { defaultValue = "batman" })
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(route = Screen.Musics.route)
            }
            val viewModel = hiltViewModel<MusicsViewModel>(parentEntry)

            MusicInfoScreen(
                backStackEntry.arguments?.getString("movieId")!!,
                viewModel
            ) {

            }
        }
    }
}