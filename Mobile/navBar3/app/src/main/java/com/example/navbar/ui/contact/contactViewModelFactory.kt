package com.example.navbar.ui.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.contact.contactRepository.contactRepository

class contactViewModelFactory (
        private val repository: contactRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return contactViewModel(repository) as T
    }
}