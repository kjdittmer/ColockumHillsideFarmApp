package com.example.colockumhillsidefarmapp.customer.store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.colockumhillsidefarmapp.GlobalStorage;
import com.example.colockumhillsidefarmapp.R;

import java.util.ArrayList;

public class CustomerStoreFragment extends Fragment {

    private RecyclerView productRecView;
    private SwipeRefreshLayout layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_customer_store, container, false);

        StoreProductRecViewAdapter adapter = new StoreProductRecViewAdapter(root.getContext());
        ArrayList<Product> allProducts = GlobalStorage.getInstance().getAllProducts(adapter);
        productRecView = root.findViewById(R.id.productRecView);

        productRecView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(root.getContext());
        productRecView.setLayoutManager(manager);

        adapter.setProducts(allProducts);

        layout = root.findViewById(R.id.swipeRefreshLayoutStore);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList<Product> allProducts = GlobalStorage.getInstance().getAllProducts(adapter);
                adapter.setProducts(allProducts);
                layout.setRefreshing(false);
            }
        });

        return root;
    }
}
