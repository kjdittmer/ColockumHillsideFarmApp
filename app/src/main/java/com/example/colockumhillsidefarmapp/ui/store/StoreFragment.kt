package com.example.colockumhillsidefarmapp.ui.store

import android.os.Bundle
import android.provider.Settings
import android.service.media.MediaBrowserService
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.colockumhillsidefarmapp.GlobalStorage
import com.example.colockumhillsidefarmapp.Product
import com.example.colockumhillsidefarmapp.R
import com.example.colockumhillsidefarmapp.StoreProductRecViewAdapter
import com.example.colockumhillsidefarmapp.databinding.FragmentStoreBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


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
//        val allProducts = GlobalStorage.getInstance().allProducts


        val slideshowViewModel =
            ViewModelProvider(this).get(StoreViewModel::class.java)

        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textStore
        //slideshowViewModel.text.observe(viewLifecycleOwner) {
        //    textView.text = it
        //}

//        val mbase = FirebaseDatabase.getInstance().reference
//        val options: FirebaseRecyclerOptions<Product> = Builder<Product>()
//            .setQuery(mbase, Product::class.java)
//            .build()

//      val allProducts = GlobalStorage.getInstance().getAllProducts()

        adapter = StoreProductRecViewAdapter(activity)
        val allProducts = GlobalStorage.getInstance().getAllProductsForStore(adapter)

        productRecView = root.findViewById(R.id.productRecView)

        productRecView.adapter = adapter
        val manager : GridLayoutManager = GridLayoutManager(activity, 2)
        productRecView.layoutManager = manager

        adapter.setProducts(allProducts)
        adapter.notifyDataSetChanged()

        layout = root.findViewById(R.id.swipeRefreshLayoutStore)
        layout.setOnRefreshListener {
            val allProducts = GlobalStorage.getInstance().getAllProductsForStore(adapter)
            adapter.setProducts(allProducts)
            adapter.notifyDataSetChanged()
            layout.isRefreshing = false;
        }

//        val rootNode = FirebaseDatabase.getInstance()
//        val reference = rootNode.getReference("product")
//        reference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (currentSnapshot in snapshot.children) {
//                    val newProduct = currentSnapshot.getValue(Product::class.java)
//                    allProducts.add(newProduct!!)
//                    Log.d("tag", currentSnapshot.child("id").getValue().toString())
//                }
//                adapter.notifyDataSetChanged()
//            }
//
//            override fun onCancelled(error: DatabaseError) {}
//        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}