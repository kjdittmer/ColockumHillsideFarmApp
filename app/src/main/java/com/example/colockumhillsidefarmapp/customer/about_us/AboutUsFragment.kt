package com.example.colockumhillsidefarmapp.customer.about_us

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
import com.example.colockumhillsidefarmapp.databinding.FragmentAboutUsBinding
import com.example.colockumhillsidefarmapp.databinding.FragmentRecipesBinding

class AboutUsFragment : Fragment() {

    private var _binding: FragmentAboutUsBinding? = null
    private lateinit var aboutUsRecView: RecyclerView
    private lateinit var adapter: AboutUsRecViewAdapter
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
            ViewModelProvider(this).get(AboutUsViewModel::class.java)

        _binding = FragmentAboutUsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapter = AboutUsRecViewAdapter(activity)
        val allInfo = DBInterface.getInstance().getAllInfo(adapter)

        aboutUsRecView = root.findViewById(R.id.aboutUsRecView)

        aboutUsRecView.adapter = adapter
        val manager : LinearLayoutManager = LinearLayoutManager(root.context)
        aboutUsRecView.layoutManager = manager

        adapter.setInfoList(allInfo)

        layout = root.findViewById(R.id.swipeRefreshLayoutAboutUs)
        layout.setOnRefreshListener {
            val allInfo = DBInterface.getInstance().getAllInfo(adapter)
            adapter.setInfoList(allInfo)
            layout.isRefreshing = false;
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}