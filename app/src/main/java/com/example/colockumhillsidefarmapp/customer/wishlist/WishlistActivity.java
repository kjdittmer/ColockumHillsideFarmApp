package com.example.colockumhillsidefarmapp.customer.wishlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.customer.store.Product;

import java.util.ArrayList;
import java.util.Collections;

public class WishlistActivity extends AppCompatActivity {
    private WishlistProductRecViewAdapter adapter;
    private SwipeRefreshLayout layout;
    private RecyclerView recyclerView;
    private ArrayList<Product> productList;
    private Spinner spnrSortWishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initData();
        setData();

        layout = findViewById(R.id.swipeRefreshLayoutWishlist);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                productList = DBInterface.getInstance().getWishlist(adapter);
                adapter.setProductsInWishlist(productList);
                spnrSortWishlist.setSelection(0);
                layout.setRefreshing(false);
            }
        });

        EditText txtSearch = findViewById(R.id.txtSearchWishlist);
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
        ArrayAdapter<String> sortByAdapter = new ArrayAdapter<String>(this, R.layout.spinner_search_criteria,
                sortBy);
        spnrSortWishlist.setAdapter(sortByAdapter);

        setSpinnerOnItemSelectedLister(productList);
    }

    private void setSpinnerOnItemSelectedLister(ArrayList<Product> list) {
        spnrSortWishlist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedSort = spnrSortWishlist.getSelectedItem().toString();
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

    private void initData () {
        recyclerView = findViewById(R.id.wishlistRecView);
        spnrSortWishlist = findViewById(R.id.spnrSortWishlist);
    }

    private void setData () {
        adapter = new WishlistProductRecViewAdapter(this, this);
        productList = DBInterface.getInstance().getWishlist(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = DBInterface.getInstance().getWishlist(adapter);
        adapter.setProductsInWishlist(productList);
    }

    public void reload() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}