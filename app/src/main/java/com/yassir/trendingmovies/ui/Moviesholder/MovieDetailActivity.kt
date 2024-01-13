package com.yassir.trendingmovies.ui.Moviesholder


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yassir.trendingmovies.data.dto.MovieDetail
import com.yassir.trendingmovies.data.models.DetailViewModel
import com.yassir.trendingmovies.data.service.ResultApi
import com.yassir.trendingmovies.ui.ErrorPage
import com.yassir.trendingmovies.ui.Loading

@Composable
fun MovieDetailActivity(movieId: String?) {

    val viewModelDetailMovie  = hiltViewModel<DetailViewModel>()

    viewModelDetailMovie.fetchDetailMovie(movieId)

    val details by viewModelDetailMovie.moviedetails.collectAsState()


//    val context = LocalContext.current

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background

        ) {

            when(details) {
                is ResultApi.Loading->{
                    Loading()
                }

                is ResultApi.Error->{
                    details.error?.let { ErrorPage(errorMessage = it) }
                }

                is ResultApi.Success->{
                    val moviesData = details.data
                    if (moviesData != null) {
                        DetailMovie(moviesData)
                    }

                }

            }

        }
}

@Composable
fun DetailMovie(movieDetails : MovieDetail) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(movieDetails.image_url)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
        }

        item {
            movieDetails.title?.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    text = it,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        item {

            movieDetails.releaseDate?.let {
                if (it.isNotEmpty()) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        text = getYearFromDate(it),
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.displayMedium
                    )
                }
            }
        }

        item {
            movieDetails.overview?.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    text = it,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }


    }
}
