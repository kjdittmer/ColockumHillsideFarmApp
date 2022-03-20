package com.example.colockumhillsidefarmapp.ui.contact_us

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.colockumhillsidefarmapp.MainActivity
import com.example.colockumhillsidefarmapp.R
import com.example.colockumhillsidefarmapp.databinding.FarmInfoBinding
class FarmInfo: AppCompatActivity() {
    private lateinit var binding: FarmInfoBinding
    private lateinit var back: Button

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pork)
        binding = FarmInfoBinding.inflate(layoutInflater)
        val root: View = binding.root
        back = root.findViewById(R.id.backbutton2)
        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}