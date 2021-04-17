package com.example.navbar.ui.contact

import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.R
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ContactFragment : Fragment() {

    private lateinit var contactViewModel: ContactViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        contactViewModel =
                ViewModelProvider(this).get(ContactViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_contact, container, false)
        val textView: TextView = root.findViewById(R.id.text_contact)
        contactViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        var et_user_name : TextView = root.findViewById(R.id.et_user_name) as EditText
        var et_email : TextView = root.findViewById(R.id.et_email) as EditText
        var et_phone : TextView = root.findViewById(R.id.et_phone) as EditText
        var et_message : TextView = root.findViewById(R.id.et_message) as EditText
        var btn_submit : TextView = root.findViewById(R.id.btn_submit) as Button

        btn_submit.setOnClickListener {
            val user_name = et_user_name.text;
            val user_email = et_email.text;
            val user_phone = et_phone.text;
            val user_message = et_message.text;
            Toast.makeText(activity, user_name, Toast.LENGTH_LONG).show()

            if(TextUtils.isEmpty(user_name)){
                et_user_name.error = "Scrie-ti numele!"
                et_user_name.requestFocus()
            }

            if(TextUtils.isEmpty(user_email)){
                et_email.error = "Scrie-ti email-ul!"
                et_email.requestFocus()
            }
            if(TextUtils.isEmpty(user_phone)){
                et_phone.error = "Scrie-ti numarul de telefon!"
                et_phone.requestFocus()
            }

            if(TextUtils.isEmpty(user_message)){
                et_message.error = "Introdu mesajul tau!"
                et_message.requestFocus()
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user_email).matches()) {
                et_email.error = "Introdu o adresa valida de email"
                et_email.requestFocus()

            }
            else{
                et_user_name.setText("");
                et_email.setText("");
                et_phone.setText("");
                et_message.setText("");
            }
        }

        return root
    }


}