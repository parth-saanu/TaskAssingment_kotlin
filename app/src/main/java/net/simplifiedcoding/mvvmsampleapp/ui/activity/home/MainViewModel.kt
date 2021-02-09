package net.simplifiedcoding.mvvmsampleapp.ui.activity.home

import androidx.lifecycle.ViewModel
import net.simplifiedcoding.mvvmsampleapp.data.repositories.MainRepository
import net.simplifiedcoding.mvvmsampleapp.ui.callback.MovieListener
import net.simplifiedcoding.mvvmsampleapp.util.ApiException
import net.simplifiedcoding.mvvmsampleapp.util.Coroutines
import net.simplifiedcoding.mvvmsampleapp.util.NoInternetException


class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {

    var authListener: MovieListener? = null
    /*call api for listing on dashboard with use Coroutines*/
    fun getMovieList(api_key: String, language: String, page: String){
        authListener?.onStarted()

        Coroutines.main {
            try {
                val movieModel = repository.recommendations(api_key, language, page)
                movieModel.results?.let {
                    authListener?.onSuccess(it)
                    return@main
                }
                authListener?.onFailure(movieModel.message)
            }catch(e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            }
        }

    }

}