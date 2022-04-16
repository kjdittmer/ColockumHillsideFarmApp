package com.example.colockumhillsidefarmapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.colockumhillsidefarmapp.ui.recipes.Recipe;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesRecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesRecipesFragment extends Fragment {

    private FavoritesRecipesRecViewAdapter adapter;
    private RecyclerView recyclerView;
    private FavoritesActivity currentActivity;

    public FavoritesRecipesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritesRecipesFragment.
     */
    public static FavoritesRecipesFragment newInstance(String param1, String param2) {
        FavoritesRecipesFragment fragment = new FavoritesRecipesFragment();
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
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_favorites_recipes, container, false);

        recyclerView = root.findViewById(R.id.favoritesRecipesRecView);

        adapter = new FavoritesRecipesRecViewAdapter(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Recipe> favoritesRecipes = Favorites.getInstance().getFavoritesRecipes();
        adapter.setRecipesFavoritesRecipes(favoritesRecipes);

        return root;
    }
}