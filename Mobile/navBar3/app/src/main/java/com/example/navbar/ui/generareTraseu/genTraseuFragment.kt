package com.example.navbar.ui.generareTraseu

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.navbar.R
import com.example.navbar.ui.generareMap.MapFragmentTraseu
import com.example.navbar.ui.generareTraseu.genTraseuRepository.genTraseuRepository
import org.json.JSONObject

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

        var necessary = ""

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

                val spinner : Spinner = view.findViewById(R.id.traseuSp1)
                val adapter : ArrayAdapter<String>

                adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, elements)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter

                spinner.onItemSelectedListener = object :

                AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                        val queue : RequestQueue = Volley.newRequestQueue(activity)
                        val string1 = "https://bureaucracyhackshostat.herokuapp.com/user/process/"
                        val string2 = elements[position]

                        var url = "$string1$string2"

                        val stringRequest = StringRequest(Request.Method.GET, url,
                                Response.Listener<String> { response ->
                                    val object1 = JSONObject(response)
                                    for (key in object1.keys()) {
                                        if (key == "institution") {

                                            val string3 = "https://bureaucracyhackshostat.herokuapp.com/user/institution/"
                                            val string4 = object1[key].toString()

                                            url = "$string3$string4"

                                            val stringRequest2 = StringRequest(Request.Method.GET, url,
                                                Response.Listener<String> { response2 ->
                                                    val object2 = JSONObject(response2)
                                                    for (key in object2.keys()) {
                                                        if (key == "id") {
                                                            val id = object2[key]
                                                            Log.d("Id-ul institutiei", id.toString())
                                                        }
                                                    }
                                                },
                                                Response.ErrorListener {Log.d("asd", "no")})
                                            queue.add(stringRequest2)
                                        }
                                        else if (key == "necessary") {
                                            necessary = object1[key].toString()
                                            necessary = necessary.filterNot { it == "]"[0] }
                                            necessary = necessary.filterNot { it == "["[0] }
                                            val arrayActe : Array<String> = necessary.split(",").toTypedArray()
                                            for (i in arrayActe.indices) {
                                                Log.d("acte", arrayActe[i])
                                            }

                                            val mShowAlertDialogBtn = activity?.findViewById<Button>(R.id.grilaTraseu)
                                            val mTxtView = activity?.findViewById<TextView>(R.id.grilaTextView)

                                            if (mShowAlertDialogBtn != null) {
                                                mShowAlertDialogBtn.setOnClickListener {
                                                    val builder = AlertDialog.Builder(activity)

                                                    val checkedActeArray = BooleanArray(arrayActe.size)
                                                    for (i in arrayActe.indices) {
                                                        checkedActeArray[i] = false
                                                    }

                                                    val acteList = listOf(*arrayActe)
                                                    builder.setTitle("Ai ales actele:")

                                                    builder.setMultiChoiceItems(arrayActe, checkedActeArray) {dialog, which, isChecked ->
                                                        checkedActeArray[which] = isChecked
                                                        val currentItem = acteList[which]
                                                        /*Toast.makeText(activity, "$currentItem $isChecked", Toast.LENGTH_SHORT).show()*/
                                                    }

                                                    builder.setPositiveButton("OK") {dialog, which ->
                                                        if (mTxtView != null) {
                                                            mTxtView.text = "Ai ales actele:\n"
                                                        }
                                                        for (i in checkedActeArray.indices) {
                                                            val checked = checkedActeArray[i]
                                                            if (checked) {
                                                                if (mTxtView != null) {
                                                                    mTxtView.text = mTxtView.text.toString() + "►" + acteList[i] + "\n"
                                                                }
                                                            }
                                                        }
                                                    }

                                                    builder.setNeutralButton("Cancel") {dialog, which ->
                                                        dialog.dismiss()
                                                    }
                                                    val dialog = builder.create()
                                                    dialog.show()
                                                }
                                            }
                                        }
                                    }
                                },
                                Response.ErrorListener {Log.d("asd", "no")})
                        queue.add(stringRequest)
                    }
                }
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