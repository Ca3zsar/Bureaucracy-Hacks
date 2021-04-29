package com.example.navbar.ui.generareTraseu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.generareTraseu.genTraseuRepository.genTraseuRepository

class genTraseuViewModelFactory(
        private val genTrRepository: genTraseuRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return genTraseuViewModel(genTrRepository) as T
    }
}