package com.example.navbar.ui.institutions.SALUBRIS

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.institutions.SALUBRIS.SalubrisRepository.SalubrisRepository

class SalubrisViewModelFactory(
    private val repository: SalubrisRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SalubrisViewModel(repository) as T
    }
}