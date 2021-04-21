package com.example.navbar.ui.contact

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
import com.example.navbar.ui.contact.contactModel.contactPost
import com.example.navbar.ui.contact.contactRepository.contactRepository

class contactFragment : Fragment() {

    private lateinit var cntViewModel: contactViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val repository = contactRepository()
        val viewModelFactory = contactViewModelFactory(repository)
        cntViewModel = ViewModelProvider(this, viewModelFactory).get(contactViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_contact, container, false)
        val textView: TextView = root.findViewById(R.id.text_contact)
        cntViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textBox1 = view.findViewById<EditText>(R.id.contactNume)
        val continut1 = textBox1.text.toString()

        val textBox2 = view.findViewById<EditText>(R.id.contactMessage)
        val continut2 = textBox2.text.toString()

        val rBar = view.findViewById<RatingBar>(R.id.rBar)
        val continut3 = rBar.rating.toInt()

        if (continut1.isEmpty()) {
            textBox1.error = "Introdu numele de utilizator!"
        } else {
            if (continut2.isEmpty()) {
                textBox2.error = "Introdu mesajul!"
            } else {
                if (continut2.isNotEmpty() && continut1.isNotEmpty()) {
                    //val nextFrag = HomeFragment()
                    Toast.makeText(activity, "Mesajul tau a fost trimis!", Toast.LENGTH_LONG).show()
                    //activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment, nextFrag)?.addToBackStack(null)?.commit()
                } else {
                    Toast.makeText(activity, "A aparut o problema!", Toast.LENGTH_LONG).show()
                }
            }
        }

        val buttonConfirma = view.findViewById<Button>(R.id.contactConfirma)
        buttonConfirma.setOnClickListener() {
            val textView = view.findViewById<TextView>(R.id.text_contact)

            val myPost = contactPost("Gigi", "test", 5)
            //val myPost = contactPost(username = continut1, comment = continut2, rating = continut3)
            cntViewModel.pushPost(myPost)
            cntViewModel.myResponse.observe(viewLifecycleOwner, Observer {response ->
                if (response.isSuccessful) {
                    //Log.d("Response", response.body()?.username.toString())
                    textView.text = response.body()?.username.toString()
                    //Log.d("Response", response.body()?.comment.toString())
                    //Log.d("Response", response.body()?.rating.toString())
                    Toast.makeText(activity, textView.text, Toast.LENGTH_LONG).show()
                    Log.d("Response", response.code().toString())
                } else {
                    Log.d("Response", response.errorBody().toString())
                    textView.text = response.code().toString()
                    //Toast.makeText(activity, textView.text, Toast.LENGTH_LONG).show()
                    Toast.makeText(activity, continut1, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}