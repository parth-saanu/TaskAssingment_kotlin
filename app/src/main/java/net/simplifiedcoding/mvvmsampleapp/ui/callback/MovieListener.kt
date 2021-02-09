package net.simplifiedcoding.mvvmsampleapp.ui.callback

import net.simplifiedcoding.mvvmsampleapp.data.network.responses.MovieModel

interface MovieListener {
    fun onStarted()
    fun onSuccess(results: MutableList<MovieModel.MovieResponse>?)
    fun onFailure(message: String)
    fun onApplyFilter(radioButtonId: Int) {}
}