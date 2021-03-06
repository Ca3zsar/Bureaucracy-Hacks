package com.example.navbar.ui.register

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
import com.example.navbar.ui.register.registerModel.registerPost
import com.example.navbar.ui.register.registerRepostory.rgstrRepostory
import kotlinx.android.synthetic.main.fragment_register.*

class registerFragment : Fragment() {

    private lateinit var rgstrViewModel: registerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val repository = rgstrRepostory()
        val viewModelFactory = registerViewModelFactory(repository)
        rgstrViewModel = ViewModelProvider(this, viewModelFactory).get(registerViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_register, container, false)

        val textView: TextView = root.findViewById(R.id.text_register);
        rgstrViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonConfirma = view.findViewById<Button>(R.id.registerSignIn)
        buttonConfirma.setOnClickListener {
            view.findViewById<EditText>(R.id.registerUsername)
            var continut1 = registerUsername.text.toString()

            view.findViewById<EditText>(R.id.registerFirstName)
            var continut2 = registerFirstName.text.toString()

            view.findViewById<EditText>(R.id.registerSecondName)
            var continut3 = registerSecondName.text.toString()

            view.findViewById<EditText>(R.id.registerEmail)
            var continut4 = registerEmail.text.toString()

            view.findViewById<EditText>(R.id.registerPassword)
            var continut5 = registerPassword.text.toString()

            val textView = view.findViewById<TextView>(R.id.text_register)

            var myPost = registerPost(email = continut4, username = continut1, name = continut2, surname = continut3, password = continut5)

            rgstrViewModel.pushPost(myPost)
            rgstrViewModel.myResponse.observe(viewLifecycleOwner, Observer {response ->
                if (response.isSuccessful) {
                    Log.d("Response", response.body()?.email.toString())
                    textView.text = response.body()?.email.toString()
                    Log.d("Response", response.body()?.username.toString())
                    Log.d("Response", response.body()?.name.toString())
                    Log.d("Response", response.body()?.surname.toString())
                    Log.d("Response", response.body()?.password.toString())
                    Log.d("Response", response.code().toString())


                } else {
                    Log.d("Response", response.errorBody().toString())
                    textView.text = response.code().toString()
                }
            })
        }
    }

}