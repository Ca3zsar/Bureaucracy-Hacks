package com.example.navbar.ui.generareTraseu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.generareTraseu.generareTraseuRepository.generareTraseuRepository

class processesListViewModelFactory (
        private val repository: generareTraseuRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TraseuViewModel(repository) as T
    }
}