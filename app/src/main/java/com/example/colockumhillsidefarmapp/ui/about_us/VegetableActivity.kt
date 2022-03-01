package com.example.colockumhillsidefarmapp.ui.about_us

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.colockumhillsidefarmapp.databinding.VegetableActivityBinding
import com.example.colockumhillsidefarmapp.R

class VegetableActivity: AppCompatActivity() {
    private lateinit var binding: VegetableActivityBinding
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vegetable_activity)
    }
}