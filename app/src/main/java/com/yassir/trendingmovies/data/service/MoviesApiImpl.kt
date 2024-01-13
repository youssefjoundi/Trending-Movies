package com.yassir.trendingmovies.data.service

import com.yassir.trendingmovies.BuildConfig
import com.yassir.trendingmovies.data.dto.Movie
import com.yassir.trendingmovies.data.dto.MovieDetail
import com.yassir.trendingmovies.data.dto.MoviesList
import com.yassir.trendingmovies.data.keys.Urls
import io.ktor.client.HttpClient
import io.ktor.client.call.body

import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class MoviesApiImpl @Inject constructor(private val client: HttpClient) : MoviesApi {

    override fun getMovies(): Flow<ResultApi<List<Movie>>> = flow {
        emit(ResultApi.Loading())

        try {

            emit(
                ResultApi.Success(
                    client.get {

                        url(Urls.url)
                        parameter("api_key", BuildConfig.API_KEY)

                    }.body<MoviesList>().results
                )
            )


        } catch (e: Exception) {
            e.printStackTrace()

            emit(ResultApi.Error(e.message ?: "Something went wrong !"))
        }
    }

    override fun getMovieDet(id: String?): Flow<ResultApi<MovieDetail>> {

        return flow {
            emit(ResultApi.Loading())

            try {

                emit(
                    ResultApi.Success(
                        client.get {

                            url(Urls.detail_url + id)
                            parameter("api_key", BuildConfig.API_KEY)

                        }.body<MovieDetail>()
                    )
                )


            } catch (e: Exception) {
                e.printStackTrace()

                emit(ResultApi.Error(e.message ?: "Something went wrong !"))
            }
        }


    }

    override suspend fun getMovieDetail(id: String?): MovieDetail {


        var movieDetail = client.get {

            url(Urls.detail_url + id)
            parameter("api_key", BuildConfig.API_KEY)

        }.body<MovieDetail>()

        return movieDetail

    }


}