package com.example.navbar.ui.institutions.DGASPCI

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.R
import com.example.navbar.ui.institutions.DGASPCI.DgaspciRepository.DgaspciRepository

class DGASPCI: AppCompatActivity() {

    private lateinit var viewModel: DgaspciViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item1)

        val repository = DgaspciRepository()
        val viewModelFactory = DgaspciViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DgaspciViewModel::class.java)
        viewModel.getPost()
        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                Log.d("Response", response.body()?.address.toString())
                Log.d("Response", response.body()?.phone.toString())
                Log.d("Response", response.body()?.name.toString())
                Toast.makeText(this, response.code().toString(), Toast.LENGTH_LONG).show()
                Log.d("Response", response.body()?.id.toString())
            } else {
                Log.d("Response", response.errorBody().toString())
                Toast.makeText(this, response.code().toString(), Toast.LENGTH_LONG).show()
            }
        })

        val titlu : TextView = findViewById(R.id.titluInstitutie)
        titlu.text = "Direcția Generală de Asistență Socială și Protecția Copilului Iași (DGASPCI)"

    }
}