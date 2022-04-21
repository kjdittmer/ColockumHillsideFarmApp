package com.example.colockumhillsidefarmapp.customer.recipes;

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
import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.example.colockumhillsidefarmapp.customer.store.StoreProductRecViewAdapter;

import java.util.ArrayList;

public class CustomerRecipesFragment extends Fragment {

    private RecyclerView recipesRecView;
    private SwipeRefreshLayout layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_customer_recipes, container, false);

        RecipeRecViewAdapter adapter = new RecipeRecViewAdapter(root.getContext());
        ArrayList<Recipe> allRecipes = GlobalStorage.getInstance().getAllRecipes(adapter);
        recipesRecView = root.findViewById(R.id.recipeRecView);

        recipesRecView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(root.getContext());
        recipesRecView.setLayoutManager(manager);

        adapter.setRecipes(allRecipes);

        layout = root.findViewById(R.id.swipeRefreshLayoutRecipes);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList<Recipe> allRecipes = GlobalStorage.getInstance().getAllRecipes(adapter);
                adapter.setRecipes(allRecipes);
                layout.setRefreshing(false);
            }
        });

        return root;
    }
}
