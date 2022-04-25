package com.example.colockumhillsidefarmapp.customer.recipes;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.colockumhillsidefarmapp.Storage;
import com.example.colockumhillsidefarmapp.R;

import java.util.ArrayList;

public class CustomerRecipesFragment extends Fragment {

    private RecyclerView recipesRecView;
    private SwipeRefreshLayout layout;
    private ArrayList<Recipe> recipeList;
    private RecipeRecViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_customer_recipes, container, false);

        adapter = new RecipeRecViewAdapter(root.getContext());
        recipeList = Storage.getInstance().getAllRecipes(adapter);
        recipesRecView = root.findViewById(R.id.recipeRecView);

        recipesRecView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(root.getContext());
        recipesRecView.setLayoutManager(manager);

        adapter.setRecipes(recipeList);

        layout = root.findViewById(R.id.swipeRefreshLayoutRecipes);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recipeList = Storage.getInstance().getAllRecipes(adapter);
                adapter.setRecipes(recipeList);
                layout.setRefreshing(false);
            }
        });

        EditText txtSearch = root.findViewById(R.id.txtSearchCustomerRecipes);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        txtSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    closeKeyboard();
                    return true;
                }
                return false;
            }
        });

        return root;
    }

    private void closeKeyboard () {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void filter(String text) {
        ArrayList<Recipe> filteredRecipes = new ArrayList<>();

        for (Recipe recipe : recipeList) {
            if (recipe.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredRecipes.add(recipe);
            }
        }

        adapter.filterList(filteredRecipes);
    }

}
