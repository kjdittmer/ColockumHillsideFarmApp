package com.example.colockumhillsidefarmapp.vendor.update_recipes;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.recipes.Recipe;
import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.example.colockumhillsidefarmapp.vendor.VendorDashboardActivity;

import java.util.ArrayList;
import java.util.Collections;

public class UpdateRecipesFragment extends Fragment {

    private RecyclerView updateRecipesRecView;
    private UpdateRecipeRecViewAdapter adapter;
    private SwipeRefreshLayout layout;
    private ArrayList<Recipe> recipeList;
    private Spinner spnrSortUpdateRecipes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_update_recipes, container, false);

        setHasOptionsMenu(true);

        adapter = new UpdateRecipeRecViewAdapter(getContext(), (VendorDashboardActivity) getActivity());
        recipeList = DBInterface.getInstance().getAllRecipes(adapter);

        updateRecipesRecView = root.findViewById(R.id.updateRecipesRecView);
        spnrSortUpdateRecipes = root.findViewById(R.id.spnrSortUpdateRecipes);


        updateRecipesRecView.setAdapter(adapter);
        updateRecipesRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setRecipes(recipeList);

        layout = root.findViewById(R.id.swipeRefreshLayoutUpdateRecipes);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recipeList = DBInterface.getInstance().getAllRecipes(adapter);
                adapter.setRecipes(recipeList);
                spnrSortUpdateRecipes.setSelection(0);
                layout.setRefreshing(false);
            }
        });

        EditText txtSearch = root.findViewById(R.id.txtSearchUpdateRecipes);
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

        ArrayList<String> sortBy = new ArrayList<>();
        sortBy.add("A->Z");
        sortBy.add("Z->A");
        ArrayAdapter<String> sortByAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_search_criteria,
                sortBy);
        spnrSortUpdateRecipes.setAdapter(sortByAdapter);

        setSpinnerOnItemSelectedLister(recipeList);

        return root;
    }

    private void setSpinnerOnItemSelectedLister(ArrayList<Recipe> list) {
        spnrSortUpdateRecipes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedSort = spnrSortUpdateRecipes.getSelectedItem().toString();
                switch (selectedSort) {
                    case "A->Z":
                        Collections.sort(list, Recipe.RecipeNameAZComparator);
                        adapter.notifyDataSetChanged();
                        break;
                    case "Z->A":
                        Collections.sort(list, Recipe.RecipeNameZAComparator);
                        adapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.update_recipes, menu);
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
        setSpinnerOnItemSelectedLister(filteredRecipes);
        adapter.filterList(filteredRecipes);
    }
}
