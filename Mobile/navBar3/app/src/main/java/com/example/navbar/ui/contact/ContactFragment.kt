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

        var et_user_name : TextView = root.findViewById(R.id.contactNume) as EditText
        var et_message : TextView = root.findViewById(R.id.contactMessage) as EditText
        var btn_submit : TextView = root.findViewById(R.id.contactConfirma) as Button

        btn_submit.setOnClickListener {
            val user_name = et_user_name.text;
            val user_message = et_message.text;
            Toast.makeText(activity, user_name, Toast.LENGTH_LONG).show()

            if(TextUtils.isEmpty(user_name)){
                et_user_name.error = "Scrie-ti numele!"
                et_user_name.requestFocus()
            }

            if(TextUtils.isEmpty(user_message)){
                et_message.error = "Introdu mesajul tau!"
                et_message.requestFocus()
            }
            else{
                et_user_name.text = "";
                et_message.text = "";
            }
        }

        return root
    }


}