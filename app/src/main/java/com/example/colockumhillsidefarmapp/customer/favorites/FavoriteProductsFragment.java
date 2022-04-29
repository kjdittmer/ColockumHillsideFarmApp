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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.example.colockumhillsidefarmapp.R;
import com.google.android.gms.auth.api.signin.internal.Storage;

import java.util.ArrayList;
import java.util.Collections;

public class FavoriteProductsFragment extends Fragment {

    private FavoriteProductsRecViewAdapter adapter;
    private RecyclerView recyclerView;
    private FavoritesActivity currentActivity;
    private ArrayList<Product> productList;
    private SwipeRefreshLayout layout;
    private Spinner spnrSortFavoriteProducts;

    public FavoriteProductsFragment(FavoritesActivity currentActivity) {
        // Required empty public constructor
        this.currentActivity = currentActivity;
    }

    public static FavoriteProductsFragment newInstance(FavoritesActivity currentActivity) {
        FavoriteProductsFragment fragment = new FavoriteProductsFragment(currentActivity);
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
        spnrSortFavoriteProducts = root.findViewById(R.id.spnrSortFavoriteProducts);

        adapter = new FavoriteProductsRecViewAdapter(getContext(), currentActivity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        productList = DBInterface.getInstance().getFavoriteProducts(adapter);
        adapter.setProductsFavoritesProducts(productList);

        layout = root.findViewById(R.id.swipeRefreshLayoutFavoriteProducts);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList = DBInterface.getInstance().getFavoriteProducts(adapter);
                adapter.setProductsFavoritesProducts(productList);
                spnrSortFavoriteProducts.setSelection(0);
                layout.setRefreshing(false);
            }
        });

        EditText txtSearch = root.findViewById(R.id.txtSearchFavoritesProducts);
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
        sortBy.add("$->$$");
        sortBy.add("$$->$");
        ArrayAdapter<String> sortByAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_search_criteria,
                sortBy);
        spnrSortFavoriteProducts.setAdapter(sortByAdapter);

        setSpinnerOnItemSelectedLister(productList);

        return root;
    }

    private void setSpinnerOnItemSelectedLister(ArrayList<Product> list) {
        spnrSortFavoriteProducts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedSort = spnrSortFavoriteProducts.getSelectedItem().toString();
                switch (selectedSort) {
                    case "A->Z":
                        Collections.sort(list, Product.ProductNameAZComparator);
                        adapter.notifyDataSetChanged();
                        break;
                    case "Z->A":
                        Collections.sort(list, Product.ProductNameZAComparator);
                        adapter.notifyDataSetChanged();
                        break;
                    case "$->$$":
                        Collections.sort(list, Product.ProductPriceAscendingComparator);
                        adapter.notifyDataSetChanged();
                        break;
                    case "$$->$":
                        Collections.sort(list, Product.ProductPriceDescendingComparator);
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

    private void filter(String text) {
        ArrayList<Product> filteredProducts = new ArrayList<>();

        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredProducts.add(product);
            }
        }
        setSpinnerOnItemSelectedLister(filteredProducts);
        adapter.filterList(filteredProducts);
    }

    private void closeKeyboard () {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}