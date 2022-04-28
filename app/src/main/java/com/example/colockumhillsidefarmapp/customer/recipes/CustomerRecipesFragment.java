package com.example.colockumhillsidefarmapp.customer.recipes;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import java.util.ArrayList;
import java.util.Collections;

public class CustomerRecipesFragment extends Fragment {

    private RecyclerView recipesRecView;
    private SwipeRefreshLayout layout;
    private ArrayList<Recipe> recipeList;
    private RecipeRecViewAdapter adapter;
    private Spinner spnrSortRecipes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_customer_recipes, container, false);

        adapter = new RecipeRecViewAdapter(root.getContext());
        recipeList = DBInterface.getInstance().getAllRecipes(adapter);
        recipesRecView = root.findViewById(R.id.recipeRecView);
        spnrSortRecipes = root.findViewById(R.id.spnrSortRecipes);

        recipesRecView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(root.getContext());
        recipesRecView.setLayoutManager(manager);

        adapter.setRecipes(recipeList);

        layout = root.findViewById(R.id.swipeRefreshLayoutRecipes);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recipeList = DBInterface.getInstance().getAllRecipes(adapter);
                adapter.setRecipes(recipeList);
                spnrSortRecipes.setSelection(0);
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
        spnrSortRecipes.setAdapter(sortByAdapter);

        setSpinnerOnItemSelectedLister(recipeList);

        return root;
    }

    private void setSpinnerOnItemSelectedLister(ArrayList<Recipe> list) {
        spnrSortRecipes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedSort = spnrSortRecipes.getSelectedItem().toString();
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
