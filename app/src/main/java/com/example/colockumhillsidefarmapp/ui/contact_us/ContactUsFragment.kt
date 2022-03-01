package com.example.colockumhillsidefarmapp.ui.contact_us

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.colockumhillsidefarmapp.databinding.FragmentContactUsBinding
import kotlinx.android.synthetic.main.fragment_contact_us.*

class ContactUsFragment : Fragment() {

    private var _binding: FragmentContactUsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    /**
     * When created, the hyperlink will be established
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpEmail()
        }


        override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(ContactUsViewModel::class.java)

        _binding = FragmentContactUsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    /**
     * Will create the hyperlink that is used to email the vendor
     */
    fun setUpEmail()
    {
        //val feedback = findViewById<TextView>(R.id.FarmEmail)
        FarmEmail.text = Html.fromHtml("<a href=\"mailto:suzanne@colockumhillsidefarm.com\">Email Us!</a>")
        FarmEmail.movementMethod = LinkMovementMethod.getInstance()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}