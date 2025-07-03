package com.example.moviesapp.ui.movie_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.moviesapp.data.local.MovieEntity
import com.example.moviesapp.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    navController: NavController,
    viewModel: MovieListViewModel = hiltViewModel()
) {
    val movies by viewModel.movies.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Películas Populares") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.MovieDetail.route + "/0") }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Película")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
            if (movies.isEmpty() && !isLoading) {
                Text(
                    text = "No hay películas guardadas. Agrega una o espera a que se carguen de la API.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(movies) { movie ->
                        MovieItem(movie = movie,
                            onDeleteClick = { viewModel.deleteMovie(movie) },
                            onItemClick = { navController.navigate(Screen.MovieDetail.route + "/${movie.id}") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(
    movie: MovieEntity,
    onDeleteClick: () -> Unit,
    onItemClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "[https://image.tmdb.org/t/p/w200$](https://image.tmdb.org/t/p/w200$){movie.posterPath}",
                contentDescription = "Poster de ${movie.title}",
                modifier = Modifier
                    .size(90.dp, 120.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Crop,
                onError = {
                    // Placeholder for broken image
                    Text("No Image", modifier = Modifier.size(90.dp, 120.dp).wrapContentSize(Alignment.Center))
                }
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Fecha de lanzamiento: ${movie.releaseDate}", style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onDeleteClick) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar Película")
            }
        }
    }
}