package com.example.colockumhillsidefarmapp.ui.about_us

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.colockumhillsidefarmapp.databinding.FragmentAboutUsBinding
import com.example.colockumhillsidefarmapp.ui.about_us.AboutUsViewModel
import com.example.colockumhillsidefarmapp.R
import com.google.android.material.snackbar.Snackbar


/**
 * A fragment containing a simple view.
 */
class AboutUsFragment : Fragment() {
    private var _binding: FragmentAboutUsBinding? = null

    private lateinit var poultryandeggs: Button
    private lateinit var pork: Button
    private lateinit var vegetables: Button
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val aboutUsViewModel =
            ViewModelProvider(this).get(AboutUsViewModel::class.java)

        _binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        poultryandeggs = root.findViewById(R.id.poultryandeggs)
        pork = root.findViewById(R.id.pork)
        vegetables = root.findViewById(R.id.vegetables)
        poultryandeggs.setOnClickListener {
            val intent = Intent(activity, PoultryActivity::class.java)
            startActivity(intent)
        }
        pork.setOnClickListener{
            val intent = Intent(activity,PorkActivity::class.java)
            startActivity(intent)
        }
        vegetables.setOnClickListener{
            val intent = Intent(activity, VegetableActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}