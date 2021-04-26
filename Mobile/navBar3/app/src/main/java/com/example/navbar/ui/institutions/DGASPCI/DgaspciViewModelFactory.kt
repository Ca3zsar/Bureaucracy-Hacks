package com.example.navbar.ui.institutions.DGASPCI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.institutions.DGASPCI.DgaspciRepository.DgaspciRepository

class DgaspciViewModelFactory (
        private val repository: DgaspciRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DgaspciViewModel(repository) as T
    }
}