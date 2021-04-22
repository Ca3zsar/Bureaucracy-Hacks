package com.example.navbar.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.R
import androidx.lifecycle.Observer
import com.example.navbar.ui.login.loginModel.loginPost
import com.example.navbar.ui.login.loginRepository.lgnRepository

class loginFragment : Fragment() {

    private lateinit var lgnViewModel: loginViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val repository = lgnRepository()
        val viewModelFactory = loginViewModelFactory(repository)
        lgnViewModel = ViewModelProvider(this, viewModelFactory).get(loginViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_login, container, false)
        val textView: TextView = root.findViewById(R.id.text_login);
        lgnViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonConfirma = view.findViewById<Button>(R.id.loginSignIn)
        buttonConfirma.setOnClickListener {
            val textBox1 = view.findViewById<EditText>(R.id.loginEmail)
            val continut1 = textBox1.text.toString()

            val textBox2 = view.findViewById<EditText>(R.id.loginPassword)
            val continut2 = textBox2.text.toString()

            if (continut1.isEmpty()) {
                textBox1.error = "Introdu adresa de email!"
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(continut1).matches()) {
                textBox1.error = "Adresa de email nu este corectă!"
            } else {
                if (continut2.isEmpty()) {
                    textBox2.error = "Introdu parola!"
                } else {
                    if (continut1.isNotEmpty() && continut2.isNotEmpty()) {
                        Toast.makeText(activity, "Autentificare reusita", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(activity, "Ai o problema!", Toast.LENGTH_LONG).show()
                    }
                }
            }

            val textView = view.findViewById<TextView>(R.id.text_login)

           // val myPost = loginPost("agachi.eusebiu@yahoo.com", "asd")
            val myPost = loginPost(email = continut1,password = continut2)
            lgnViewModel.pushPost(myPost)
            lgnViewModel.myResponse.observe(viewLifecycleOwner, Observer {response ->
                if (response.isSuccessful) {
                    Log.d("Response", response.body()?.email.toString())
                    textView.text = response.body()?.email.toString()
                    Log.d("Response", response.body()?.password.toString())
                    Toast.makeText(activity, textView.text, Toast.LENGTH_LONG).show()
                    Log.d("Response", response.code().toString())

                    //Log.d("Response", response.body()?.myUserId.toString())
                    //Log.d("Response", response.body()?.id.toString())
                    //Log.d("Response", response.body()?.title.toString())
                    //textView.text = response.body()?.title!!
                    //Log.d("Response", response.body()?.body.toString())
                    //Log.d("Response", response.code().toString())
                    //Toast.makeText(activity, textView.text, Toast.LENGTH_LONG).show()

                } else {
                    Log.d("Response", response.errorBody().toString())
                    textView.text = response.code().toString()
                    Toast.makeText(activity, textView.text, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}