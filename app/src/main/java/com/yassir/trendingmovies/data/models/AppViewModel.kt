package com.yassir.trendingmovies.data.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yassir.trendingmovies.data.dto.Movie
import com.yassir.trendingmovies.data.service.MoviesApi
import com.yassir.trendingmovies.data.service.ResultApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AppViewModel @Inject constructor(
    private val movieService : MoviesApi,
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {


    private val _movies = MutableStateFlow<ResultApi<List<Movie>>>(ResultApi.Loading())

    val movies = _movies.asStateFlow()


    init {
        fetchMovies()
    }

    private fun fetchMovies() {

        viewModelScope.launch {

            movieService.getMovies()
                .flowOn(defaultDispatcher)
                .catch {
                    _movies.value = ResultApi.Error(it.message ?: "Error")
                }
                .collect{
                    _movies.value = it
                }

        }

    }


}