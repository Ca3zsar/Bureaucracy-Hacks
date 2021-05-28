package com.example.navbar.ui.feedback

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.navbar.R
import com.example.navbar.ui.feedback.feedbackModel.feedbackPost
import com.example.navbar.ui.feedback.feedbackRepository.fdbRepository
import com.example.navbar.ui.home.HomeFragment


class FeedbackFragment : Fragment() {

    private lateinit var feedbackViewModel: FeedbackViewModel
    private lateinit var username: String
    private lateinit var comment: String


    var process : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val feedbackRepository = fdbRepository()
        val viewModelFactory = FeedbackMainViewModelFactory(feedbackRepository)
        feedbackViewModel = ViewModelProvider(this, viewModelFactory).get(FeedbackViewModel::class.java)


        val root = inflater.inflate(R.layout.fragment_feedback, container, false)
        val textView: TextView = root.findViewById(R.id.text_feedback);
        feedbackViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        process = arguments?.getString("process").toString()
        Log.d("feedback", process)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonConfirma = view.findViewById<Button>(R.id.feedbackConfirma)

        buttonConfirma.setOnClickListener {
            val textBox1 = view.findViewById<EditText>(R.id.feedbackNume)
            val continut1 = textBox1.text.toString()
            Log.d("test", continut1)


            val textBox2 = view.findViewById<EditText>(R.id.feedbackMessage)
            val continut2 = textBox2.text.toString()
            Log.d("test", continut2)

            /**/
            val spinner1 = view.findViewById<Spinner>(R.id.q1Feedback_Sp)
            var continut3 = spinner1.selectedItem
            var continut32 : Boolean = false

            val spinner2 = view.findViewById<Spinner>(R.id.q2Feedback_Sp)
            var continut4 = spinner2.selectedItem
            var continut42 : Int = 0

            val spinner3 = view.findViewById<Spinner>(R.id.q3Feedback_Sp)
            var continut5 = spinner3.selectedItem.toString()

            val rbar = view.findViewById<RatingBar>(R.id.rBarFeedback)
            var continut6 = rbar.rating.toInt()

            val languages = resources.getStringArray(R.array.servicii)
            val languages2 = resources.getStringArray(R.array.institutii)
            val languagessp1 = resources.getStringArray(R.array.q1FeedbackStrings)
            val languagessp2 = resources.getStringArray(R.array.q2FeedbackStrings)
            val languagessp3 = resources.getStringArray(R.array.q3FeedbackStrings)


            if (spinner1 != null){
                val adapter = ArrayAdapter<String>(view.context,
                    android.R.layout.simple_spinner_item, languagessp1)
                spinner1.adapter = adapter

                spinner1.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        Log.d("test", languagessp1[position])
                        continut32 = languagessp1[position]=="Da"
                    }
                }
            }


            if (spinner2 != null){
                val adapter = ArrayAdapter<String>(view.context,
                    android.R.layout.simple_spinner_item, languagessp2)
                spinner2.adapter = adapter

                spinner2.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        Log.d("test", languagessp2[position])
                        continut42 = languagessp2[position].first().toInt()
                    }
                }
            }


            if (spinner3 != null){
                val adapter = ArrayAdapter<String>(view.context,
                    android.R.layout.simple_spinner_item, languagessp3)
                spinner3.adapter = adapter

                spinner3.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        Log.d("test", languagessp3[position])
                        continut5 = languagessp3[position].toString()
                        continut5 = continut5.replace("minute", "")
                        continut5 = continut5.replace(" ", "")
                    }
                }
            }


            if (rbar != null){
                rbar.setOnRatingBarChangeListener (object :
                    RatingBar.OnRatingBarChangeListener {
                    override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
                        Log.d("test", p1.toString())
                        continut6 = p1.toInt()
                    }
                })
            }

            if(TextUtils.isEmpty(continut1)){
                textBox1.error = "Scrie-ti numele!"
                textBox1.requestFocus()
            }

            if (continut1.isEmpty()) {
                textBox1.error = "Introdu username-ul"
            } else {
                if (continut2.isEmpty()) {
                    textBox2.error = "Introdu mesajul!"
                } else {
                    if (continut1.isNotEmpty() && continut2.isNotEmpty()) {
                        val nextFrag = HomeFragment()
                        Toast.makeText(activity, "Mesajul tau a fost trimis", Toast.LENGTH_LONG).show()
                        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment, nextFrag)?.addToBackStack(null)?.commit()
                    }
                }
            }

            val textView = view.findViewById<TextView>(R.id.text_feedback)

            val myPost = feedbackPost(username = continut1, comment = continut2, q1 = continut32 as Boolean, q2 = 1 as Int, q3 = "0-30", q4 = continut6)
            feedbackViewModel.pushPost(myPost)
            feedbackViewModel.myResponse.observe(viewLifecycleOwner, Observer {response ->
                if (response.isSuccessful) {
                    Log.d("Response", response.code().toString())

                } else {
                    Log.d("Response", response.errorBody().toString())
                    textView.setText(response.code().toString())
                }
            })

        }

    }
}