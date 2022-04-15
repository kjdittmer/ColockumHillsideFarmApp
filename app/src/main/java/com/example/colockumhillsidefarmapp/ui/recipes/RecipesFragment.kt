package com.example.colockumhillsidefarmapp.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.colockumhillsidefarmapp.GlobalStorage
import com.example.colockumhillsidefarmapp.R
import com.example.colockumhillsidefarmapp.StoreProductRecViewAdapter
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
        val allRecipes = GlobalStorage.getInstance().getAllRecipes(adapter)

        recipeRecView = root.findViewById(R.id.recipeRecView)

        recipeRecView.adapter = adapter
        val manager : LinearLayoutManager = LinearLayoutManager(root.context)
        recipeRecView.layoutManager = manager

        adapter.setRecipes(allRecipes)

        layout = root.findViewById(R.id.swipeRefreshLayoutRecipes)
        layout.setOnRefreshListener {
            val allRecipes = GlobalStorage.getInstance().getAllRecipes(adapter)
            adapter.setRecipes(allRecipes)
            layout.isRefreshing = false;
        }

      //  val dummyRecipes : ArrayList<Recipe> = ArrayList()
       // dummyRecipes.add(Recipe(1, "Chicken Soup", "short", "long", "https://www.inspiredtaste.net/wp-content/uploads/2018/09/Easy-Chicken-Noodle-Soup-Recipe-1200.jpg"))
       // dummyRecipes.add(Recipe(2, "Chicken and Rice", "short", "long", "https://www.eatwell101.com/wp-content/uploads/2018/04/chicken-and-rice-recipe.jpg"))
       // dummyRecipes.add(Recipe(3, "Scrambled Eggs", "short", "long", "https://images.media-allrecipes.com/userphotos/9175615.jpg"))
       // dummyRecipes.add(Recipe(4, "Meatloaf", "short", "long", "https://imagesvc.meredithcorp.io/v3/mm/image?q=60&c=sc&poi=face&w=2000&h=1000&url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F43%2F2022%2F02%2F18%2F16354-easy-meatloaf-mfs-76.jpg"))
       // dummyRecipes.add(Recipe(5, "Roast Beef", "short", "long", "https://hips.hearstapps.com/hmg-prod/images/delish-roast-beef-horizontal-1540505165.jpg"))
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}