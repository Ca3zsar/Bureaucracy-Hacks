package com.example.navbar.ui.generareTraseu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.R
import com.example.navbar.ui.generareTraseu.TraseuViewModel


class TraseuFragment : Fragment() {

    private lateinit var traseuViewModel: TraseuViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        traseuViewModel = ViewModelProvider(this).get(TraseuViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_traseu, container, false)

        val textView: TextView = root.findViewById(R.id.text_traseu);
        traseuViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

}