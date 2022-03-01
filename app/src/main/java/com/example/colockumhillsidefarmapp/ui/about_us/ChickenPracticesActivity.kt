package com.example.colockumhillsidefarmapp.ui.about_us

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.colockumhillsidefarmapp.databinding.ChickenPracticesActivityBinding
import com.example.colockumhillsidefarmapp.R


class ChickenPracticesActivity:AppCompatActivity() {
    private lateinit var binding: ChickenPracticesActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chicken_practices_activity)
    }

}