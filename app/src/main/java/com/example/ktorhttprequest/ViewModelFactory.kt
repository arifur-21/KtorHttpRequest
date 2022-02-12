package com.example.ktorhttprequest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ktorhttprequest.repository.MainRepository
import com.example.ktorhttprequest.ui.MainViewModel

class ViewModelFactory (private val  repository: MainRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository)as T
    }
}