package com.example.navbar.ui.institutions.ANAF

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.R
import com.example.navbar.ui.institutions.ANAF.AnafRepository.AnafRepository

class ANAF: AppCompatActivity() {

    private lateinit var viewModel: AnafViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item1)

        val repository = AnafRepository()
        val viewModelFactory = AnafViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AnafViewModel::class.java)
        viewModel.getPost()
        viewModel.myResponse.observe(this, Observer { response ->
            if (response.isSuccessful) {
                val informatii : TextView = findViewById(R.id.informatiiGenerale)
                informatii.setText(R.string.informatii_genelate_institutii)
                informatii.append("Adresa: " + response.body()?.address.toString() + "\n\n")
                informatii.append("Numărul de telefon: " + response.body()?.phone.toString() + "\n\n")
                informatii.append("Email: " + response.body()?.email.toString())

                val linkWeb : TextView = findViewById(R.id.link_institutie)
                linkWeb.text = "Site-ul instituției: " + response.body()?.url.toString()
                linkWeb.movementMethod = LinkMovementMethod.getInstance()

                val departamente : TextView = findViewById(R.id.departamente)
                departamente.setText(R.string.departamente_institutii)

                val departments : List<Map<String, String>>? = response.body()?.departments

                /*if (array != null) {
                    for (i in array.indices) {
                        departamente.append("\u25CF " + array[i].getValue("name") + "\n")
                        if (array[i].containsKey("program")) {
                            if (array[i].getValue("program").isEmpty()) {
                                departamente.append("\u25BA Program: " + array[i].getValue("program") + "\n\n")
                            } else {
                                departamente.append("\u25BA Program: indisponibil" + "\n\n")
                            }
                        } else {
                            departamente.append("\n")
                        }
                    }
                }*/

                if (departments != null) {
                    for (i in departments.indices) {
                        val name = departments[i].getValue("name")
                        departamente.append("\u25CF $name\n")
                        val program = departments[i].getValue("program")
                        if (program == "{}") {
                            departamente.append("\u25BA Program: indisponibil" + "\n\n")
                        } /*else {
                            val mapa : Map<String, Map<String, String>> = program as Map<String, Map<String, String>>
                        }*/
                    }
                }

                Log.d("Response", response.body()?.address.toString())
                Log.d("Response", response.body()?.phone.toString())
                Log.d("Response", response.body()?.name.toString())
                //Toast.makeText(this, response.code().toString(), Toast.LENGTH_LONG).show()
                Log.d("Response", response.body()?.id.toString())
            } else {
                Log.d("Response", response.errorBody().toString())
                //Toast.makeText(this, response.code().toString(), Toast.LENGTH_LONG).show()
            }
        })

        val titlu : TextView = findViewById(R.id.titluInstitutie)
        titlu.text = "Agenția Națională de Administrare Fiscală (ANAF)"
    }
}