package net.simplifiedcoding.mvvmsampleapp.data.repositories

import net.simplifiedcoding.mvvmsampleapp.data.network.MyApi
import net.simplifiedcoding.mvvmsampleapp.data.network.SafeApiRequest
import net.simplifiedcoding.mvvmsampleapp.data.network.responses.MovieModel

class MainRepository(
    private val api: MyApi
) : SafeApiRequest() {

    suspend fun recommendations(api_key: String, language: String, page: String): MovieModel {
        return apiRequest { api.recommendations(api_key, language, page) }
    }

}