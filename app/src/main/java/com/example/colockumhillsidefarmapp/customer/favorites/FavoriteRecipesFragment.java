package com.example.colockumhillsidefarmapp.customer.favorites;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.recipes.Recipe;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoriteRecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteRecipesFragment extends Fragment {

    private FavoriteRecipesRecViewAdapter adapter;
    private RecyclerView recyclerView;
    private FavoritesActivity currentActivity;
    private ArrayList<Recipe> recipeList;
    private SwipeRefreshLayout layout;

    public FavoriteRecipesFragment(FavoritesActivity currentActivity) {
        this.currentActivity = currentActivity;
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FavoritesRecipesFragment.
     */
    public static FavoriteRecipesFragment newInstance(FavoritesActivity currentActivity) {
        FavoriteRecipesFragment fragment = new FavoriteRecipesFragment(currentActivity);
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

        adapter = new FavoriteRecipesRecViewAdapter(getContext(), currentActivity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recipeList = DBInterface.getInstance().getFavoriteRecipes(adapter);
        adapter.setRecipesFavoritesRecipes(recipeList);

        layout = root.findViewById(R.id.swipeRefreshLayoutFavoriteRecipes);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recipeList = DBInterface.getInstance().getFavoriteRecipes(adapter);
                adapter.setRecipesFavoritesRecipes(recipeList);
                layout.setRefreshing(false);
            }
        });

        EditText txtSearch = root.findViewById(R.id.txtSearchFavoritesRecipes);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString().trim());
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
            String name = recipe.getName();
            if (name != null) {
                if (name.toLowerCase().contains(text.toLowerCase())) {
                    filteredRecipes.add(recipe);
                }
            }
            String ingredients = recipe.getIngredients();
            if (ingredients != null) {
                if (ingredients.toLowerCase().contains(text.toLowerCase())) {
                    filteredRecipes.add(recipe);
                }
            }
        }

        adapter.filterList(filteredRecipes);
    }
}