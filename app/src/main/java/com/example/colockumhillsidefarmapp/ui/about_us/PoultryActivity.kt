package com.example.colockumhillsidefarmapp.ui.about_us

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.colockumhillsidefarmapp.MainActivity
import com.example.colockumhillsidefarmapp.R
import com.example.colockumhillsidefarmapp.databinding.PoultryActivityBinding
class PoultryActivity: AppCompatActivity() {
    private lateinit var backbutton: Button
    private lateinit var turkey: Button
    private lateinit var chicken: Button
    private lateinit var eggs: Button
    private lateinit var practices: Button
    private lateinit var binding: PoultryActivityBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.poultry_activity)
        binding = PoultryActivityBinding.inflate(layoutInflater)
        val root: View = binding.root
        backbutton = root.findViewById(R.id.backbutton)
        turkey = root.findViewById(R.id.turkey)
        chicken = root.findViewById(R.id.chicken)
        eggs = root.findViewById(R.id.eggs)
        practices = root.findViewById(R.id.practices)
        backbutton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
        }
        turkey.setOnClickListener{
            Toast.makeText(this, "We raise specialty “Midget White” turkeys each year for Thanksgiving. " +
                    "Midget Whites love to spend their day foraging, so on our farm they enjoy a diet of free range shrub steppe findings supplemented with locally sourced, non-GMO, corn- and soy-free feed. " +
                    "This sought after breed takes about 7 months to grow to a compact 8-18 pounds, but rewards the chef with rich and juicy flavor. " +
                    "Watch our store for the opportunity to reserve your Thanksgiving bird!", Toast.LENGTH_LONG ).show()
        }
        chicken.setOnClickListener{
        }

    }
}