package com.example.navbar.ui.feedback


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.navbar.ui.feedback.feedbackModel.feedbackPost
import com.example.navbar.ui.feedback.feedbackRepository.fdbRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class FeedbackViewModel(private val feedbackRepository: fdbRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is feedback Fragment"
    }
    val text: LiveData<String> = _text

    val myResponse: MutableLiveData<Response<feedbackPost>> = MutableLiveData()

    fun pushPost(post : feedbackPost) {
        viewModelScope.launch {
            val response = feedbackRepository.pushPost(post)
            myResponse.value = response;

        }
    }
}