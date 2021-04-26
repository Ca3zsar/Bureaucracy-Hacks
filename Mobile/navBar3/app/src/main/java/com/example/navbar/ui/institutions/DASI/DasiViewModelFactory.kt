package com.example.navbar.ui.institutions.DASI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.institutions.DASI.DasiRepository.DasiRepository

class DasiViewModelFactory (
        private val repository: DasiRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DasiViewModel(repository) as T
    }
}