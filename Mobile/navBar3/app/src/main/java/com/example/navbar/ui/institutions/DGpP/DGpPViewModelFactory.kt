package com.example.navbar.ui.institutions.DGpP

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.institutions.DGpP.DGpPRepository.DGpPRepository

class DGpPViewModelFactory (
        private val repository: DGpPRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DGpPViewModel(repository) as T
    }
}