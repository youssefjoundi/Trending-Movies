package com.yassir.trendingmovies.data.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yassir.trendingmovies.data.dto.MovieDetail
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
class DetailViewModel @Inject constructor(

    private val movieService : MoviesApi,
    private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _moviedetail = MutableStateFlow<ResultApi<MovieDetail>>(ResultApi.Loading())


    val moviedetails = _moviedetail.asStateFlow()


    fun fetchDetailMovie(id : String?) {

        viewModelScope.launch {

            movieService.getMovieDet(id = id)
                .flowOn(defaultDispatcher)
                .catch {
                    _moviedetail.value = ResultApi.Error(it.message ?: "Error")
                }
                .collect {
                    _moviedetail.value = it
                }

        }

    }


}