package com.example.colockumhillsidefarmapp.customer.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.colockumhillsidefarmapp.DBInterface
import com.example.colockumhillsidefarmapp.R
import com.example.colockumhillsidefarmapp.databinding.FragmentRecipesBinding

class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private lateinit var recipeRecView: RecyclerView
    private lateinit var adapter: RecipeRecViewAdapter
    private lateinit var layout: SwipeRefreshLayout

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

        adapter = RecipeRecViewAdapter(activity)
        val allRecipes = DBInterface.getInstance().getAllRecipes(adapter)

        recipeRecView = root.findViewById(R.id.recipeRecView)

        recipeRecView.adapter = adapter
        val manager : LinearLayoutManager = LinearLayoutManager(root.context)
        recipeRecView.layoutManager = manager

        adapter.setRecipes(allRecipes)

        layout = root.findViewById(R.id.swipeRefreshLayoutRecipes)
        layout.setOnRefreshListener {
            val allRecipes = DBInterface.getInstance().getAllRecipes(adapter)
            adapter.setRecipes(allRecipes)
            layout.isRefreshing = false;
        }

       return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}