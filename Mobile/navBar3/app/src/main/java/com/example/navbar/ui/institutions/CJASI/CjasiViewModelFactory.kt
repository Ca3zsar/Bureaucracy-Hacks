package com.example.navbar.ui.institutions.CJASI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.institutions.CJASI.CjasiRepostitory.CjasiRepository

class CjasiViewModelFactory(
        private val repository: CjasiRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CjasiViewModel(repository) as T
    }
}