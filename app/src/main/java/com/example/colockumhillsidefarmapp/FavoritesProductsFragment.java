package com.example.colockumhillsidefarmapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesProductsFragment extends Fragment {

    private FavoritesProductsRecViewAdapter adapter;
    private RecyclerView recyclerView;
    private FavoritesActivity currentActivity;

    public FavoritesProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FavoritesProductsFragment.
     */
    public static FavoritesProductsFragment newInstance() {
        FavoritesProductsFragment fragment = new FavoritesProductsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_favorites_products, container, false);

        recyclerView = root.findViewById(R.id.favoritesProductsRecView);

        adapter = new FavoritesProductsRecViewAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Product> favoritesProducts = Favorites.getInstance().getFavoritesProducts();
        adapter.setProductsFavoritesProducts(favoritesProducts);


        return root;
    }
}