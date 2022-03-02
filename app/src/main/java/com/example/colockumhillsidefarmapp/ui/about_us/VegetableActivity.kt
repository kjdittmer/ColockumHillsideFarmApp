package com.example.colockumhillsidefarmapp.ui.about_us

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.colockumhillsidefarmapp.databinding.VegetableActivityBinding
import com.example.colockumhillsidefarmapp.R
import com.google.android.material.snackbar.Snackbar

class VegetableActivity: AppCompatActivity() {
    private lateinit var binding: VegetableActivityBinding
    private lateinit var veggies: Button
    private lateinit var microgreens: Button
    private lateinit var plantstarts: Button
    private lateinit var practices: Button
    private lateinit var back: Button

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.vegetable_activity)
        binding = VegetableActivityBinding.inflate(layoutInflater)
        var root: View = binding.root
        veggies = root.findViewById(R.id.veggies)
        microgreens = root.findViewById(R.id.microgreens)
        plantstarts = root.findViewById(R.id.plantstarts)
        practices = root.findViewById(R.id.veggiepractices)
        back = root.findViewById(R.id.back3)
     veggies.setOnClickListener {
        this?.let {
               it1 -> Snackbar.make(it1.findViewById(R.id.veggies),
        "You can taste the difference! We use organic practices to grow beautiful, nutrient-rich produce. Compost built on our farm nourishes the soil, and we employ crop rotation, interplanting and water-wise practices to conserve water and nurture our plants and growing beds.    ",
        Snackbar.LENGTH_LONG).apply{view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines=15 }
        .show()
       }
        }
        microgreens.setOnClickListener{
            this?.let {
                    it2 -> Snackbar.make(it2.findViewById(R.id.microgreens),
                "Want a delicious boost to your nutrition? We offer a wide variety of microgreens, which pack a powerful antioxidant punch! Microgreens are rich in potassium, iron, zinc, magnesium, copper and polyphenols. We grow our microgreens in an organic soil mix fortified with organic compost and liquid kelp for maximum nutrition. Our microgreens are fresh, colorful and tasty.  Enjoy them in sandwiches, salads, pestos and wraps, or use them as a garnish on any savory dish!",
                Snackbar.LENGTH_LONG).apply{view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines=15 }
                .show()
            }
        }
        plantstarts.setOnClickListener{
            this?.let {
                    it3 -> Snackbar.make(it3.findViewById(R.id.microgreens),
               "We sell a wide variety of vegetable starts that we have tested and grown successfully in our own garden beds. Our seeds come from reputable sellers and we make our own organic potting soil. Customers compliment us on the beauty, vigor, and success of our plant starts. ",
                Snackbar.LENGTH_LONG).apply{view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines=15 }
                .show()
           }
      }
        practices.setOnClickListener{}
        back.setOnClickListener{

        }
    }
}