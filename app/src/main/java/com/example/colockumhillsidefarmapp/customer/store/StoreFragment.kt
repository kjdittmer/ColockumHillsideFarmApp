package com.example.colockumhillsidefarmapp.customer.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.colockumhillsidefarmapp.DBInterface
import com.example.colockumhillsidefarmapp.R
import com.example.colockumhillsidefarmapp.databinding.FragmentStoreBinding


class StoreFragment : Fragment() {

    private var _binding: FragmentStoreBinding? = null
    private lateinit var productRecView: RecyclerView
    private lateinit var adapter: StoreProductRecViewAdapter
    private lateinit var layout: SwipeRefreshLayout

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

        adapter =
            StoreProductRecViewAdapter(
                activity
            )
        val allProducts = DBInterface.getInstance().getAllProducts(adapter)

        productRecView = root.findViewById(R.id.productRecView)

        productRecView.adapter = adapter
        val manager : LinearLayoutManager = LinearLayoutManager(root.context)
        productRecView.layoutManager = manager

        adapter.setProducts(allProducts)

        layout = root.findViewById(R.id.swipeRefreshLayoutStore)
        layout.setOnRefreshListener {
            val allProducts = DBInterface.getInstance().getAllProducts(adapter)
            adapter.setProducts(allProducts)
            layout.isRefreshing = false;
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}