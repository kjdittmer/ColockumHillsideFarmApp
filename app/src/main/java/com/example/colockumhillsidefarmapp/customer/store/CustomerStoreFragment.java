package com.example.colockumhillsidefarmapp.customer.store;

import android.content.Context;
import android.os.Bundle;
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

public class CustomerStoreFragment extends Fragment {

    private RecyclerView productRecView;
    private SwipeRefreshLayout layout;
    private ArrayList<Product> productList;
    private StoreProductRecViewAdapter adapter;
    private Spinner spnrSortStore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_customer_store, container, false);

        adapter = new StoreProductRecViewAdapter(root.getContext());
        productList = DBInterface.getInstance().getAllProducts(adapter);
        productRecView = root.findViewById(R.id.productRecView);
        spnrSortStore = root.findViewById(R.id.spnrSortStore);


        productRecView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(root.getContext());
        productRecView.setLayoutManager(manager);

        adapter.setProducts(productList);

        layout = root.findViewById(R.id.swipeRefreshLayoutStore);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList = DBInterface.getInstance().getAllProducts(adapter);
                adapter.setProducts(productList);
                spnrSortStore.setSelection(0);
                layout.setRefreshing(false);
            }
        });

        EditText txtSearch = root.findViewById(R.id.txtSearchCustomerStore);
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
                    spnrSortStore.requestFocus();
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
        spnrSortStore.setAdapter(sortByAdapter);

        setSpinnerOnItemSelectedLister(productList);

        return root;
    }

    private void setSpinnerOnItemSelectedLister(ArrayList<Product> list) {
        spnrSortStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedSort = spnrSortStore.getSelectedItem().toString();
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
        adapter.filterList(filteredProducts);
        setSpinnerOnItemSelectedLister(filteredProducts);
    }

    private void closeKeyboard () {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
