package com.example.colockumhillsidefarmapp.vendor.update_store;

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
import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.example.colockumhillsidefarmapp.vendor.VendorDashboardActivity;

import java.util.ArrayList;
import java.util.Collections;

public class UpdateStoreFragment extends Fragment {

    private RecyclerView editStoreRecView;
    private EditStoreProductRecViewAdapter adapter;
    private SwipeRefreshLayout layout;
    private ArrayList<Product> productList;
    private Spinner spnrSortUpdateStore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_update_store, container, false);

        setHasOptionsMenu(true);

        adapter = new EditStoreProductRecViewAdapter(root.getContext(), (VendorDashboardActivity) getActivity());
        productList = DBInterface.getInstance().getAllProducts(adapter);
        editStoreRecView = root.findViewById(R.id.editStoreRecView);
        spnrSortUpdateStore = root.findViewById(R.id.spnrSortUpdateStore);


        editStoreRecView.setAdapter(adapter);
        editStoreRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setProducts(productList);

        layout = root.findViewById(R.id.swipeRefreshLayoutEditStore);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList = DBInterface.getInstance().getAllProducts(adapter);
                adapter.setProducts(productList);
                spnrSortUpdateStore.setSelection(0);
                layout.setRefreshing(false);
            }
        });

        EditText txtSearch = root.findViewById(R.id.txtSearchUpdateStore);
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

        ArrayList<String> sortBy = new ArrayList<>();
        sortBy.add("A->Z");
        sortBy.add("Z->A");
        sortBy.add("$->$$");
        sortBy.add("$$->$");
        ArrayAdapter<String> sortByAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_search_criteria,
                sortBy);
        spnrSortUpdateStore.setAdapter(sortByAdapter);

        setSpinnerOnItemSelectedLister(productList);

        return root;
    }

    private void setSpinnerOnItemSelectedLister(ArrayList<Product> list) {
        spnrSortUpdateStore.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedSort = spnrSortUpdateStore.getSelectedItem().toString();
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.update_store, menu);
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
