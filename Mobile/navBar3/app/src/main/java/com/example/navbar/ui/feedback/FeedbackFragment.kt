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
import com.example.navbar.ui.feedback.FeedbackViewModel
import com.example.navbar.ui.feedback.feedbackModel.feedbackPost
import com.example.navbar.ui.feedback.feedbackRepository.fdbRepository
import com.example.navbar.ui.home.HomeFragment


class FeedbackFragment : Fragment() {

    private lateinit var feedbackViewModel: FeedbackViewModel

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
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /**
        val buttonConfirma = view.findViewById<Button>(R.id.feedbackConfirma)

        buttonConfirma.setOnClickListener {
            val textBox1 = view.findViewById<EditText>(R.id.feedbackNume)
            val continut1 = textBox1.text.toString()

            val textBox2 = view.findViewById<EditText>(R.id.feedbackMessage)
            val continut2 = textBox2.text.toString()

            /*val languages = resources.getStringArray(R.array.servicii)
            val spinner = view.findViewById<Spinner>(R.id.traseuSp2)
            if (spinner != null){
                val adapter = ArrayAdapter<String>(view.context,
                    android.R.layout.simple_spinner_item, languages)
                spinner.adapter = adapter

                spinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        Toast.makeText(activity, getString(R.string.selected_item) + " "
                        + languages[position], Toast.LENGTH_SHORT).show();
                    }
                }
            }*/

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
            val myPost = feedbackPost("Gigi", "Testul suprem")


            feedbackViewModel.pushPost(myPost)
            feedbackViewModel.myResponse.observe(viewLifecycleOwner, Observer {response ->
                if (response.isSuccessful) {
                    Log.d("Response", response.body()?.username.toString())
                    Log.d("Response", response.body()?.comment.toString())
                    Log.d("Response", response.code().toString())

                } else {
                    Log.d("Response", response.errorBody().toString())
                    textView.setText(response.code().toString())
                    Toast.makeText(activity, textView.text, Toast.LENGTH_LONG).show()
                }
            })

        }
         */
    }
}