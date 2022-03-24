package com.example.colockumhillsidefarmapp.ui.recipes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.colockumhillsidefarmapp.R
import com.example.colockumhillsidefarmapp.databinding.FragmentRecipesBinding
import com.example.colockumhillsidefarmapp.ui.recipes.RecipeActivity

class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var recipe: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(RecipesViewModel::class.java)

        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        val root: View = binding.root



//        val textView: TextView = binding.textRecipes
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        val textView: TextView = binding.textRecipes
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

//        recipe = root.findViewById(R.id.recipeRecView);
//        recipe.setOnClickListener {
//            val intent = Intent(activity, RecipeActivity::class.java)
//            startActivity(intent)
//
//        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}