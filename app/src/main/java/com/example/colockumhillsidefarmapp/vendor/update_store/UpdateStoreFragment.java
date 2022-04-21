package com.example.colockumhillsidefarmapp.vendor.update_store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.colockumhillsidefarmapp.GlobalStorage;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.example.colockumhillsidefarmapp.vendor.VendorDashboardActivity;

import java.util.ArrayList;

public class UpdateStoreFragment extends Fragment {

    private RecyclerView editStoreRecView;
    private EditStoreProductRecViewAdapter adapter;
    private SwipeRefreshLayout layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_update_store, container, false);

        setHasOptionsMenu(true);

        adapter = new EditStoreProductRecViewAdapter(root.getContext(), (VendorDashboardActivity) getActivity());
        ArrayList<Product> allProducts = GlobalStorage.getInstance().getAllProducts(adapter);

        editStoreRecView = root.findViewById(R.id.editStoreRecView);

        editStoreRecView.setAdapter(adapter);
        editStoreRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setProducts(allProducts);

        layout = root.findViewById(R.id.swipeRefreshLayoutEditStore);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.update_store, menu);
    }
}
