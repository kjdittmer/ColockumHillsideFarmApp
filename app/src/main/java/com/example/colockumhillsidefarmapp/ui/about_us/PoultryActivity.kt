package com.example.colockumhillsidefarmapp.ui.about_us

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.colockumhillsidefarmapp.MainActivity
import com.example.colockumhillsidefarmapp.R
import com.example.colockumhillsidefarmapp.databinding.PoultryActivityBinding
import com.google.android.material.snackbar.Snackbar

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
            this?.let {
                    it1 -> Snackbar.make(it1.findViewById(R.id.poultryandeggs),
                "We raise specialty “Midget White” turkeys each year for Thanksgiving. Midget Whites love to spend their day foraging, so on our farm they enjoy a diet of free range shrub steppe findings supplemented with locally sourced, non-GMO, corn- and soy-free feed. This sought after breed takes about 7 months to grow to a compact 8-18 pounds, but rewards the chef with rich and juicy flavor. Watch our store for the opportunity to reserve your Thanksgiving bird!",
                Snackbar.LENGTH_LONG).apply{view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines=15 }
                .show()
            }
            }
        eggs.setOnClickListener{
            this?.let {
                    it1 -> Snackbar.make(it1.findViewById(R.id.poultryandeggs),
               "What makes our Shrub Steppe eggs special? Our hens enjoy the freedom to forage on our 80 acres of pristine shrub steppe land. They spend their days scratching for shoots, leaves and insects. When you crack the eggs open, the benefits of their footloose and fancy-free lifestyle is obvious: the eggs’ yolks are a deep golden orange, and the whites are firm, not runny.",
                Snackbar.LENGTH_LONG).apply{view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines=15 }
                .show()
            }
        }
        chicken.setOnClickListener{
            this?.let {
                    it1 -> Snackbar.make(it1.findViewById(R.id.poultryandeggs),
                "Our Freedom Ranger chickens are raised on pasture and fed locally-sourced corn-, soy-, and GMO-free feed. This breed is said to have been preferred by Julia Child, and we agree with Julia! Freedom Rangers have a natural balance between white and dark meat and a deep, rich chicken flavor. The meat is juicy and firm–but not the least bit tough",
                Snackbar.LENGTH_LONG).apply{view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines=10 }
                .show()
            }
        }
        practices.setOnClickListener {
            val intent = Intent(this, PoultryActivity::class.java)
            startActivity(intent)
        }

    }
}