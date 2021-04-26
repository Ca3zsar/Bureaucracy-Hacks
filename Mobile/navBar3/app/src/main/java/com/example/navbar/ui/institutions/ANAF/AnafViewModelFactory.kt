package com.example.navbar.ui.institutions.ANAF

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.institutions.ANAF.AnafRepository.AnafRepository

class AnafViewModelFactory(
        private val repository: AnafRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AnafViewModel(repository) as T
    }
}