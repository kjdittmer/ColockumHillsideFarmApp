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
import com.example.colockumhillsidefarmapp.R

class ContactUsFragment : Fragment() {

    private var _binding: FragmentContactUsBinding? = null

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

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}