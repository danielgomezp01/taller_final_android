package com.example.moviesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.moviesapp.ui.movie_detail.MovieDetailScreen
import com.example.moviesapp.ui.movie_list.MovieListScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.MovieList.route) {
        composable(Screen.MovieList.route) {
            MovieListScreen(navController = navController)
        }
        composable(
            route = Screen.MovieDetail.route + "/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType; defaultValue = 0 })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            MovieDetailScreen(navController = navController)
        }
    }
}