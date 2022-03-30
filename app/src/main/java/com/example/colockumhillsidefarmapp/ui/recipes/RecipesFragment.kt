package com.example.colockumhillsidefarmapp.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.colockumhillsidefarmapp.R
import com.example.colockumhillsidefarmapp.databinding.FragmentRecipesBinding

class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private lateinit var recipeRecView: RecyclerView
    private lateinit var adapter: RecipeRecViewAdapter

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

//        val textView: TextView = binding.textRecipes
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

//        recipe = root.findViewById(R.id.recipeRecView);
//        recipe.setOnClickListener {
//            val intent = Intent(activity, RecipeActivity::class.java)
//            startActivity(intent)
//
//        }

        adapter = RecipeRecViewAdapter(activity)
        recipeRecView = root.findViewById(R.id.recipeRecView)

        recipeRecView.adapter = adapter
        val manager : GridLayoutManager = GridLayoutManager(activity, 2)
        recipeRecView.layoutManager = manager

        //GridLayoutManager(activity, 2)

        val dummyRecipes : ArrayList<Recipe> = ArrayList()
        dummyRecipes.add(Recipe(1, "Chicken Soup", "short", "long", "https://www.inspiredtaste.net/wp-content/uploads/2018/09/Easy-Chicken-Noodle-Soup-Recipe-1200.jpg"))
        adapter.setRecipes(dummyRecipes)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}