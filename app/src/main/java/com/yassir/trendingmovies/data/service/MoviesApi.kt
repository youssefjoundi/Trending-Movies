package com.yassir.trendingmovies.data.service

import com.yassir.trendingmovies.data.dto.Movie
import com.yassir.trendingmovies.data.dto.MovieDetail
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow

import kotlinx.serialization.json.Json



interface MoviesApi {


    fun getMovies() : Flow<ResultApi<List<Movie>>>


    fun getMovieDet(id : String?) : Flow<ResultApi<MovieDetail>>

    suspend fun getMovieDetail(id: String?): MovieDetail

    companion object {

        fun create(): MoviesApi {

            return MoviesApiImpl(
                client = HttpClient(Android) {

                    install(ContentNegotiation) {
                        json(Json {
                            prettyPrint = true
                            isLenient = true
                        })
                    }


                    install(DefaultRequest) {
                        header(HttpHeaders.ContentType, ContentType.Application.Json)
                    }


                }
            )

        }


    }

}