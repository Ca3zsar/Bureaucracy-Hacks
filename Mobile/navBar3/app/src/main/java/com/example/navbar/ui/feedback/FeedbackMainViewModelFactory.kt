package com.example.navbar.ui.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.ui.feedback.feedbackRepository.fdbRepository

class FeedbackMainViewModelFactory(
    private val feedbackRepository: fdbRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FeedbackViewModel(feedbackRepository) as T
    }
}