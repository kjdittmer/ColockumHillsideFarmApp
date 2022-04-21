package com.example.colockumhillsidefarmapp.vendor.update_recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.example.colockumhillsidefarmapp.customer.recipes.Recipe;
import com.example.colockumhillsidefarmapp.vendor.VendorDashboardActivity;

import java.util.ArrayList;

public class UpdateRecipesFragment extends Fragment {

    private RecyclerView updateRecipesRecView;
    private UpdateRecipeRecViewAdapter adapter;
    private SwipeRefreshLayout layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_update_recipes, container, false);

        setHasOptionsMenu(true);

        adapter = new UpdateRecipeRecViewAdapter(getContext(), (VendorDashboardActivity) getActivity());
        ArrayList<Recipe> allRecipes = GlobalStorage.getInstance().getAllRecipes(adapter);

        updateRecipesRecView = root.findViewById(R.id.updateRecipesRecView);

        updateRecipesRecView.setAdapter(adapter);
        updateRecipesRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setRecipes(allRecipes);

        layout = root.findViewById(R.id.swipeRefreshLayoutUpdateRecipes);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.update_recipes, menu);
    }
}
