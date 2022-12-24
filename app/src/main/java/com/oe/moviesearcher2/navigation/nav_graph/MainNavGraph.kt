package com.oe.moviesearcher2.navigation.nav_graph

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.oe.moviesearcher2.navigation.Screen
import com.oe.moviesearcher2.ui.MainScreen
import com.oe.moviesearcher2.ui.viewmodels.MainViewModel

@OptIn(ExperimentalFoundationApi::class)
fun NavGraphBuilder.MainNavGraph(
    navController: NavHostController
){
    composable(
        route = Screen.Main.route
    ) { backStackEntry ->
        val parentEntry = remember(backStackEntry) {
            navController.getBackStackEntry(Screen.Main.route)
        }
        val viewModel = hiltViewModel<MainViewModel>(parentEntry)

        MainScreen(
            viewModel,
            { navController.navigateUp() }
        ) {
            when(it){
                0 -> navController.navigate(route = Screen.Movies.route)
                1 -> navController.navigate(route = Screen.Series.route)
                2 -> navController.navigate(route = Screen.Musics.route)
//                3 -> navController.navigate(route = Screen.Favorites.route)
//                4 -> navController.navigate(route = Screen.Uploads.route)
            }

        }

    }
}