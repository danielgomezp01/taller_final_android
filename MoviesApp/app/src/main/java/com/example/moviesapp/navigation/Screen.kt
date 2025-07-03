package com.example.moviesapp.navigation

sealed class Screen(val route: String) {
    object MovieList : Screen("movie_list_screen")
    object MovieDetail : Screen("movie_detail_screen")
}
