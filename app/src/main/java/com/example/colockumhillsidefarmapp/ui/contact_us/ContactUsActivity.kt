package com.example.colockumhillsidefarmapp.ui.contact_us

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.colockumhillsidefarmapp.MainActivity
import com.example.colockumhillsidefarmapp.R
import com.example.colockumhillsidefarmapp.databinding.FragmentContactUsBinding

class ContactUsActivity: AppCompatActivity() {
    private lateinit var binding: FragmentContactUsBinding
    private lateinit var back: Button

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setUpEmail()
        setContentView(R.layout.activity_pork)
        binding = FragmentContactUsBinding.inflate(layoutInflater)
        val root: View = binding.root
        back = root.findViewById(R.id.backbutton2)
        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Will create the hyperlink that is used to email the vendor
     */
    fun setUpEmail()
    {
        val feedback = findViewById<TextView>(R.id.FarmEmail)
        feedback.text = Html.fromHtml("<a href=\"mailto:suzanne@colockumhillsidefarm.com\">Email Us!</a>")
        feedback.movementMethod = LinkMovementMethod.getInstance()
        feedback.setLinkTextColor(Color.BLUE)
    }
}