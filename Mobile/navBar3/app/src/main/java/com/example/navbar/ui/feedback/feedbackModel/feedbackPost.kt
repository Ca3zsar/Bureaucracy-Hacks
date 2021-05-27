package com.example.navbar.ui.feedback.feedbackModel

data class feedbackPost(
    val username: String,
    val comment: String,
    val q1: Boolean,
    val q2: Int,
    val q3: String,
    val q4: Int
)