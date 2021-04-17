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

        val buttonConfirma = view.findViewById<Button>(R.id.registerIn)
        buttonConfirma.setOnClickListener {
            val textBox1 = view.findViewById<EditText>(R.id.username)
            val continut1 = textBox1.text.toString()

            val textBox2 = view.findViewById<EditText>(R.id.firstName)
            val continut2 = textBox2.text.toString()

            val textBox3 = view.findViewById<EditText>(R.id.secondName)
            val continut3 = textBox3.text.toString()

            val textBox4 = view.findViewById<EditText>(R.id.emailRegister)
            val continut4 = textBox4.text.toString()

            val textBox5 = view.findViewById<EditText>(R.id.passwordRegister)
            val continut5 = textBox5.text.toString()

            val textView = view.findViewById<TextView>(R.id.text_register)

            val myPost = registerPost("agachi.eusebiu@yahoo.com", "Gigi", "Agachi", "Eusebiu", "asd")
            rgstrViewModel.pushPost(myPost)
            rgstrViewModel.myResponse.observe(viewLifecycleOwner, Observer {response ->
                if (response.isSuccessful) {
                    Log.d("Response", response.body()?.email.toString())
                    textView.text = response.body()?.email.toString()
                    Log.d("Response", response.body()?.username.toString())
                    Log.d("Response", response.body()?.name.toString())
                    Log.d("Response", response.body()?.surname.toString())
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