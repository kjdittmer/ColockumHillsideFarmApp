package com.example.colockumhillsidefarmapp.customer.contact_us

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.colockumhillsidefarmapp.databinding.FragmentContactUsBinding
import kotlinx.android.synthetic.main.fragment_contact_us.*
import com.example.colockumhillsidefarmapp.R
import com.mailchimp.sdk.api.model.Contact
import com.mailchimp.sdk.api.model.ContactStatus
import com.mailchimp.sdk.main.Mailchimp

class ContactUsFragment : Fragment() {

    private var _binding: FragmentContactUsBinding? = null
    private lateinit var btnSignUpForNewsletter : Button
    private lateinit var editTextTextEmailAddress : EditText
    private lateinit var editTextFirstName : EditText
    private lateinit var editTextLastName : EditText

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
            editTextFirstName = root.findViewById(R.id.editTextFirstName)
            editTextLastName = root.findViewById(R.id.editTextLastName)

            btnSignUpForNewsletter.setOnClickListener {
                var input : String = editTextTextEmailAddress.toString()
                var fInput = editTextFirstName.toString()
                var lInput = editTextLastName.toString()
                if(input.contains("@") && !lInput.equals("") && !fInput.equals("")) {
                    val newContact = Contact.Builder(input)
                        .setMergeField("FNAME", fInput)
                        .setMergeField("LNAME", lInput)
                        .setContactStatus(ContactStatus.SUBSCRIBED)
                        .build()
                    val sdk = Mailchimp.sharedInstance()
                    sdk.createOrUpdateContact(newContact)
                    var message = "Added " + input + " to our list :)"
                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                    editTextTextEmailAddress.text.clear()
                } else {
                    Toast.makeText(activity, "Please enter a valid email address :)", Toast.LENGTH_SHORT).show()
                    editTextTextEmailAddress.text.clear()
                }
            }


            return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}