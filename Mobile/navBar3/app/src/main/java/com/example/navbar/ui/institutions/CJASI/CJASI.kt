package com.example.navbar.ui.institutions.CJASI

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.R
import com.example.navbar.ui.institutions.CJASI.CjasiRepostitory.CjasiRepository

class CJASI: AppCompatActivity() {

    private lateinit var viewModel: CjasiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item1)

        val repository = CjasiRepository()
        val viewModelFactory = CjasiViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(CjasiViewModel::class.java)
        viewModel.getPost()
        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                val informatii : TextView = findViewById(R.id.informatiiGenerale)
                informatii.setText("Informatii Generale si de Contact:\n")
                informatii.append(response.body()?.address.toString())
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
        titlu.text = "Casa Județeană de Asigurări de Sănătate Iași (CJASI)"
    }
}