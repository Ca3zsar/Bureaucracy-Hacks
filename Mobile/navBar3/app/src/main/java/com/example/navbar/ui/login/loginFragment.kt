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
import com.example.navbar.Comunicator
import com.example.navbar.ui.login.loginModel.loginPost
import com.example.navbar.ui.login.loginRepository.lgnRepository
import kotlinx.android.synthetic.main.fragment_login.*

class loginFragment : Fragment() {

    private lateinit var lgnViewModel: loginViewModel
    private lateinit var comunicator: Comunicator

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
            var textBox1 = view.findViewById<EditText>(R.id.loginEmail)
            //var continut1 = textBox1.text.toString()

            var textBox2 = view.findViewById<EditText>(R.id.loginPassword)
            //var continut2 = textBox2.text.toString()

            var continut1 = loginEmail.text.toString()
            var continut2 = loginPassword.text.toString()

            comunicator = activity as Comunicator
            comunicator.passDataCom(loginEmail.text.toString())


            if (continut1.isEmpty()) {
                textBox1.error = "Introdu adresa de email!"
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(continut1).matches()) {
                textBox1.error = "Adresa de email nu este corectă!"
            } else {
                if (continut2.isEmpty()) {
                    textBox2.error = "Introdu noua parolă!"
                } else {
                    if (continut1.isNotEmpty() && continut2.isNotEmpty()) {
                        Toast.makeText(activity, "Autentificare reusita", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(activity, "Ai o problema!", Toast.LENGTH_LONG).show()
                    }
                }
            }

            val textView = view.findViewById<TextView>(R.id.text_login)

            //val myPost = loginPost("agachi.eusebiu@yahoo.com", "asd")
            var myPost = loginPost(email = continut1, password = continut2)
            lgnViewModel.pushPost(myPost)
            lgnViewModel.myResponse.observe(viewLifecycleOwner, Observer {response ->
                if (response.isSuccessful) {
                    Log.d("Response", response.body()?.email.toString())
                    //textView.text = response.body()?.email.toString()
                    Log.d("Response", response.body()?.password.toString())
                    //Toast.makeText(activity, textView.text, Toast.LENGTH_LONG).show()
                    Log.d("Response", response.code().toString())
                    //Toast.makeText(activity, response.body()?.email.toString(), Toast.LENGTH_LONG).show()
                    //Log.d("Response", response.body()?.myUserId.toString())
                    //Log.d("Response", response.body()?.id.toString())
                    //Log.d("Response", response.body()?.title.toString())
                    //textView.text = response.body()?.title!!
                    //Log.d("Response", response.body()?.body.toString())
                    //Log.d("Response", response.code().toString())
                    //Toast.makeText(activity, textView.text, Toast.LENGTH_LONG).show()

                } else {
                    Log.d("Response", response.errorBody().toString())
                    Toast.makeText(activity,continut1, Toast.LENGTH_LONG).show()
                    //Toast.makeText(activity, response.code().toString(), Toast.LENGTH_LONG).show()
                    //textView.text = response.code().toString()
                    //Toast.makeText(activity, textView.text, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}