package com.example.navbar.ui.institutions.DGpP

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.R
import com.example.navbar.ui.institutions.DGASPCI.DgaspciRepository.DgaspciRepository
import com.example.navbar.ui.institutions.DGASPCI.DgaspciViewModel
import com.example.navbar.ui.institutions.DGASPCI.DgaspciViewModelFactory
import com.example.navbar.ui.institutions.DGpP.DGpPRepository.DGpPRepository

class DGpP: AppCompatActivity() {

    private lateinit var viewModel: DGpPViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item1)

        val repository = DGpPRepository()
        val viewModelFactory = DGpPViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DGpPViewModel::class.java)
        viewModel.getPost()
        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                Log.d("Response", response.body()?.userId.toString())
                Log.d("Response", response.body()?.id.toString())
                Toast.makeText(this, response.code().toString(), Toast.LENGTH_LONG).show()
                Log.d("Response", response.body()?.body.toString())
            } else {
                Log.d("Response", response.errorBody().toString())
                Toast.makeText(this, response.code().toString(), Toast.LENGTH_LONG).show()
            }
        })

        val titlu : TextView = findViewById(R.id.titluInstitutie)
        titlu.text = "Direcția Generală pentru Pașapoarte (DGpP)"
    }
}