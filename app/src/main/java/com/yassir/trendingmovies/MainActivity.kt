package com.yassir.trendingmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yassir.trendingmovies.data.models.AppViewModel
import com.yassir.trendingmovies.data.service.ResultApi
import com.yassir.trendingmovies.ui.ErrorPage
import com.yassir.trendingmovies.ui.Loading
import com.yassir.trendingmovies.ui.Moviesholder.MovieDetailActivity
import com.yassir.trendingmovies.ui.Moviesholder.Movies
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            val viewModel= viewModel<AppViewModel>()


            val movies by viewModel.movies.collectAsState()



            NavHost(navController = navController, startDestination = "Movies") {
                composable("Movies" ) {
                    when(movies) {
                        is ResultApi.Loading-> {
                            Loading()
                        }

                        is ResultApi.Error->{
                            movies.error?.let { it1 -> ErrorPage(errorMessage = it1) }
                        }

                        is ResultApi.Success->{
                            val moviesData = movies.data ?: emptyList()
                            Movies(moviesData, navController)
                        }
                    }


                }

                composable("MovieDetailActivity/{movieId}") { backStackEntry ->
                    val movieId = backStackEntry.arguments?.getString("movieId")
                    MovieDetailActivity(movieId = movieId)
                }
            }
        }
    }
}



