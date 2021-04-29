package com.example.navbar.ui.generareTraseu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.R
import com.example.navbar.ui.generareMap.MapFragmentTraseu
import com.example.navbar.ui.generareTraseu.genTraseuRepository.genTraseuRepository

class genTraseuFragment : Fragment() {

    private lateinit var genTrViewModel: genTraseuViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val genTrRepository = genTraseuRepository()
        val viewModelFactory = genTraseuViewModelFactory(genTrRepository)
        genTrViewModel = ViewModelProvider(this, viewModelFactory).get(genTraseuViewModel::class.java)


        val root = inflater.inflate(R.layout.fragment_traseu, container, false)
        val textView: TextView = root.findViewById(R.id.text_traseu);
        genTrViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        genTrViewModel.getPost()
        genTrViewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
            if(response.isSuccessful) {
                val array  = response.body()
                val elements : ArrayList<String> = ArrayList()

                if (array != null) {
                    for (i in array.indices) {
                        elements.add(array[i].name)
                    }
                }

                //Toast.makeText(activity, elements.toString(), Toast.LENGTH_LONG).show()

                val spinner : Spinner = view.findViewById(R.id.traseuSp1)
                val adapter : ArrayAdapter<String>

                adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, elements)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter

            }
            else {
                Toast.makeText(activity, response.code().toString(), Toast.LENGTH_LONG).show()
                Log.d("Response", response.code().toString())
            }

        })

        val button = view.findViewById<Button>(R.id.traseuConfirma)
        button.setOnClickListener {
            val nextFrag = MapFragmentTraseu()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment, nextFrag)?.addToBackStack(null)?.commit()
        }

    }
}