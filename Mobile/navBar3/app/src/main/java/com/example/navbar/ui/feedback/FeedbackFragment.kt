package com.example.navbar.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.R
import com.example.navbar.ui.feedback.FeedbackViewModel


class FeedbackFragment : Fragment() {

    private lateinit var feedbackViewModel: FeedbackViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        feedbackViewModel = ViewModelProvider(this).get(FeedbackViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_feedback, container, false)

        val textView: TextView = root.findViewById(R.id.text_feedback);
        feedbackViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

}