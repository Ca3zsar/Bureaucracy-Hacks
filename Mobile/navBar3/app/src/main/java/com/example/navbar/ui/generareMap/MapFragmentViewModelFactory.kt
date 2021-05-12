package com.example.navbar.ui.generareMap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.generareMap.mapRepository.mpRepository

class MapFragmentViewModelFactory(
        private val mapRepository: mpRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MapFragmentTraseuViewModel(mapRepository) as T
    }
}