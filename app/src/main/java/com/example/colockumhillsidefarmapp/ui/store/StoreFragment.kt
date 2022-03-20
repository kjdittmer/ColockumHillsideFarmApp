package com.example.colockumhillsidefarmapp.ui.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.colockumhillsidefarmapp.Product
import com.example.colockumhillsidefarmapp.ProductRecViewAdapter
import com.example.colockumhillsidefarmapp.R
import com.example.colockumhillsidefarmapp.databinding.FragmentStoreBinding

class StoreFragment : Fragment() {

    private var _binding: FragmentStoreBinding? = null
    private lateinit var productRecView: RecyclerView
    private lateinit var adapter: ProductRecViewAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(StoreViewModel::class.java)

        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textStore
        //slideshowViewModel.text.observe(viewLifecycleOwner) {
        //    textView.text = it
        //}

        adapter = ProductRecViewAdapter(activity)
        productRecView = root.findViewById(R.id.productRecView)

        productRecView.adapter = adapter
        productRecView.layoutManager = GridLayoutManager(activity, 2)

        val productsList = ArrayList<Product>()
        productsList.add(
            Product(1, "Pork Chop", 10, "https://atlas-content-cdn.pixelsquid.com/stock-images/pork-chop-raw-chops-ZemwxrE-600.jpg",
            "A tasty pork chop", "Long description", 4.99)
        )
        productsList.add(
            Product(2, "Chicken Breast", 10, "https://media01.stockfood.com/largepreviews/MzQ3NjQ3MjA2/11214426-A-Single-Raw-Chicken-Breast-on-a-White-Background.jpg",
                "A yummy chicken breast", "Long description", 5.99)
        )
        productsList.add(
            Product(3, "Beef Tenderloin", 10, "https://st2.depositphotos.com/1625039/11858/i/600/depositphotos_118582494-stock-photo-raw-beef-tenderloin.jpg",
                "A scrumptious beef tenderloin", "Long description", 4.99)
        )

        productsList.add(
            Product(4, "Eggs", 10, "https://www.humphreysfarm.com/productcart/pc/catalog/5514-lg.jpg",
                "A nourishing breakfast", "Long description", 2.99)
        )
        productsList.add(
            Product(5, "Tomato", 10, "https://st.depositphotos.com/1642482/2529/i/600/depositphotos_25296371-stock-photo-red-tomato.jpg",
                "An acidic treat", "Long description", 3.99)
        )
        productsList.add(
            Product(6, "Apple", 10, "https://cdn.britannica.com/14/77414-004-30B131EC/Apple.jpg",
                "A juicy delight", "Long description", 3.50)
        )
        productsList.add(
            Product(7, "Banana", 10, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/55/Banana_%281%29.jpg/1200px-Banana_%281%29.jpg",
                "An energizing snack", "Long description", 2.99)
        )

        adapter.setProducts(productsList)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}