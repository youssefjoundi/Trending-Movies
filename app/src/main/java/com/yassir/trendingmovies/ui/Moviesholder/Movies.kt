package com.yassir.trendingmovies.ui.Moviesholder


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.yassir.trendingmovies.data.dto.Movie


@Composable
fun Movies(movies: List<Movie>, navController: NavController) {


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn {
            
            items(movies) { item ->
                Movieholder(navController , movie = item)
            }
        }
    }

}