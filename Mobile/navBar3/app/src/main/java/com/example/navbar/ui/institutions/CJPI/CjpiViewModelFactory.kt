package com.example.navbar.ui.institutions.CJPI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.institutions.CJPI.CjpiRepository.CjpiRepository

class CjpiViewModelFactory(
        private val repository: CjpiRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CjpiViewModel(repository) as T
    }
}