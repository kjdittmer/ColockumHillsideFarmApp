package com.example.colockumhillsidefarmapp.ui

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.colockumhillsidefarmapp.R
import com.example.colockumhillsidefarmapp.databinding.FragmentVendorBinding
import com.example.colockumhillsidefarmapp.ui.about_us.AboutUsViewModel

class VendorFragment : Fragment() {
    private var _binding: FragmentVendorBinding? = null

    private lateinit var btnLogin : Button
    private lateinit var btnForgotPaswd : Button
    private lateinit var txtUsername : EditText
    private lateinit var txtUserID : EditText

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(VendorViewModel::class.java)

        _binding = FragmentVendorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textAboutUs
        //galleryViewModel.text.observe(viewLifecycleOwner) {
        //    textView.text = it
        //}

        btnLogin = root.findViewById(R.id.btnLogin)
        btnForgotPaswd = root.findViewById(R.id.btnForgotPasswd)
        txtUsername = root.findViewById(R.id.txtUsername)
        txtUserID = root.findViewById(R.id.txtUserID)

        btnForgotPaswd.setOnClickListener {
            Toast.makeText(activity?.applicationContext ?: null, "Please contact the system admin for your login credentials", Toast.LENGTH_LONG).show()
        }

        btnLogin.setOnClickListener {
            if(txtUsername.text.toString().equals("nicobradley") && txtUserID.text.toString().equals("1234")){
                Toast.makeText(activity, "Welcome nicobradley", Toast.LENGTH_LONG).show()

                val intent = Intent(activity, VendorChoiceActivity::class.java)
                startActivity(intent)

            } else {
                Toast.makeText(activity, "Your credentials are INCORRECT", Toast.LENGTH_LONG).show()
            }
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}