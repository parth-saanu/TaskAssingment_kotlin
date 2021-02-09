package net.simplifiedcoding.mvvmsampleapp.ui.activity.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.simplifiedcoding.mvvmsampleapp.data.repositories.MainRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val repository: MainRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(
            repository
        ) as T
    }
}