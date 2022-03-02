package com.example.colockumhillsidefarmapp.ui.contact_us

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.colockumhillsidefarmapp.databinding.FragmentContactUsBinding
import kotlinx.android.synthetic.main.fragment_contact_us.*
import com.example.colockumhillsidefarmapp.R

class ContactUsFragment : Fragment() {

    private var _binding: FragmentContactUsBinding? = null
    private lateinit var btnSignUpForNewsletter : Button
    private lateinit var editTextTextEmailAddress : EditText

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

        override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val contactUsViewModel =
            ViewModelProvider(this).get(ContactUsViewModel::class.java)

            _binding = FragmentContactUsBinding.inflate(inflater, container, false)
            val root: View = binding.root

            btnSignUpForNewsletter = root.findViewById(R.id.btnSignUpForNewsletter)
            editTextTextEmailAddress = root.findViewById(R.id.editTextTextEmailAddress)

            btnSignUpForNewsletter.setOnClickListener {
                var input = editTextTextEmailAddress.text
                if(input.contains("@")){
                    var message = "Added " + input + " to our list :)"
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "Please enter a valid email address :)", Toast.LENGTH_SHORT).show()
                }
            }


            return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}