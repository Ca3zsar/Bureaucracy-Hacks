package com.example.navbar.ui.institutions.DLEPI

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.R
import com.example.navbar.ui.institutions.DGpP.DGpPRepository.DGpPRepository
import com.example.navbar.ui.institutions.DGpP.DGpPViewModel
import com.example.navbar.ui.institutions.DGpP.DGpPViewModelFactory
import com.example.navbar.ui.institutions.DLEPI.DLEPIRepository.DLEPIRepository

class DLEPI: AppCompatActivity() {

    private lateinit var viewModel: DLEPIViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item1)

        val repository = DLEPIRepository()
        val viewModelFactory = DLEPIViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DLEPIViewModel::class.java)
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

                val departments : List<Map<String, Any>>? = response.body()?.departments

                if (departments != null) {
                    for (i in departments.indices) {
                        val name = departments[i].getValue("name")
                        departamente.append("\n\n\u25CF $name\n")
                        val program = departments[i].getValue("program") as Map<String, Any>
                        if (program.isEmpty()) {
                            departamente.append("\u25BA Program: indisponibil" + "\n\n")
                        } else {
                            for (key in program.keys) {
                                val orar = program.getValue(key) as Map<String, String>
                                departamente.append("\n \u25BA $key  :\n")
                                for (key2 in orar.keys) {
                                    departamente.append("$key2  : ")
                                    departamente.append("${orar.getValue(key2)} \n")
                                }
                            }
                        }
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
        titlu.text = "Direcția Locală pentru Evidența Persoanelor Iași (DLEPI)"

        viewModel.getProcessesListPost()
        viewModel.myResponse2.observe(this, Observer { response2 ->
            if (response2.isEmpty()) {
                Log.d("Response", "Procese indisponibile!")
            }
            else {
                Log.d("asd" ,response2.toString())
                val procese : TextView = findViewById(R.id.procese)
                procese.setText(R.string.procese_institutii)
                for (element in response2) {
                    val proces = element.getValue("name").toString()
                    procese.append("\u25BA $proces\n\n")
                }
            }
        })
    }
}