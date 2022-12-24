package com.oe.moviesearcher2.navigation.nav_graph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.oe.moviesearcher2.navigation.ROOT_GRAPH_ROUTE
import com.oe.moviesearcher2.navigation.Screen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        route = ROOT_GRAPH_ROUTE
    ) {
        MainNavGraph(navController)
        MoviesNavGraph(navController)
        SeriesNavGraph(navController)
        MusicsNavGraph(navController)
    }
}