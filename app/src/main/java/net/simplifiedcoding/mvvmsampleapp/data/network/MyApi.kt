package net.simplifiedcoding.mvvmsampleapp.data.network

import net.simplifiedcoding.mvvmsampleapp.data.network.responses.MovieModel
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    @GET("recommendations")
    suspend fun recommendations(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: String
    ): Response<MovieModel>

    companion object {
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ): MyApi {

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("https://api.themoviedb.org/3/movie/458220/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}

