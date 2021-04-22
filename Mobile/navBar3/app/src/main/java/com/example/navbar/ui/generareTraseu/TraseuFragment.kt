package com.example.navbar.ui.generareTraseu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.R
import com.example.navbar.ui.generareTraseu.generareTraseuModel.departmentsListPost
import com.example.navbar.ui.generareTraseu.generareTraseuRepository.generareTraseuRepository

class TraseuFragment : Fragment() {

    private lateinit var traseuViewModel: TraseuViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val repository = generareTraseuRepository()
        val viewModelFactory = departmentsListViewModelFactory(repository)
        traseuViewModel = ViewModelProvider(this, viewModelFactory).get(TraseuViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_traseu, container, false)

        val textView: TextView = root.findViewById(R.id.text_traseu);
        traseuViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner1 = view.findViewById<Spinner>(R.id.spInstitutii)

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            val textView = view.findViewById<TextView>(R.id.text_traseu)

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //traseuViewModel.getDepartmentsPost()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                traseuViewModel.getDepartmentsPost()
                traseuViewModel.myResponse1.observe(viewLifecycleOwner, Observer { response ->
                    if (response.isSuccessful) {
                        Log.d("Response", response.body()?.institutionName.toString())
                        textView.text = response.body()?.institutionName.toString()
                    }
                    else{
                        Log.d("Response", response.errorBody().toString())
                        textView.text = response.code().toString()
                        Toast.makeText(activity, textView.text, Toast.LENGTH_LONG).show()
                    }
                })
            }
        }

//            val textView = view.findViewById<TextView>(R.id.text_traseu)
//
//            //val myPost = departmentsListPost("asd");
//            traseuViewModel.getDepartmentsPost()
//
//           traseuViewModel.myResponse1.observe(viewLifecycleOwner, Observer { response ->
//                if (response.isSuccessful) {
//                    Log.d("Response", response.body()?.institutionName.toString())
//                    textView.text = response.body()?.institutionName.toString()
//              }
//                 else{
//                   Log.d("Response", response.errorBody().toString())
//                   textView.text = response.code().toString()
//                   Toast.makeText(activity, textView.text, Toast.LENGTH_LONG).show()
//            }
//        })

    }
  }






