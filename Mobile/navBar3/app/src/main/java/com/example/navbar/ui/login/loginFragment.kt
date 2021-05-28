package com.example.navbar.ui.login

import android.content.Intent
import android.os.Build
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
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.R
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.navbar.Comunicator
import com.example.navbar.MainActivity
import com.example.navbar.ui.ComunicatorStaticClass
import com.example.navbar.ui.login.loginApi.loginRetrofitInstance
import com.example.navbar.ui.login.loginApi.loginSimpleApi
import com.example.navbar.ui.login.loginModel.loginPost
import com.example.navbar.ui.login.loginRepository.lgnRepository
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.Response

@Suppress("NAME_SHADOWING")
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
            var textBox1 = view.findViewById<EditText>(R.id.loginEmail)

            var textBox2 = view.findViewById<EditText>(R.id.loginPassword)

            var continut1 = loginEmail.text.toString()
            var continut2 = loginPassword.text.toString()


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

            var myPost = loginPost(email = continut1, password = continut2)
            lgnViewModel.pushPost(myPost)
            lgnViewModel.myResponse.observe(viewLifecycleOwner, Observer {response ->
                if (response.isSuccessful) {
                    Log.d("Response", response.body()?.message.toString())
                    Log.d("Response", response.code().toString())
                    val map: Map<String, Any>? = response.body()?.user
                    var name = ""
                    var username = ""
                    if(map!=null){
                        if(map.containsKey("name")){
                            name = map.getValue("name").toString()
                        }
                        if(map.containsKey("username")){
                            username = map.getValue("username").toString()
                        }
                    }
                    Log.d("Name", name + " " + username)
                    ComunicatorStaticClass.email = loginEmail.text.toString()
                    ComunicatorStaticClass.nume = name
                    ComunicatorStaticClass.prenume = username
                } else {
                    Log.d("Response", response.errorBody().toString())
                    Toast.makeText(activity,continut1, Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}

