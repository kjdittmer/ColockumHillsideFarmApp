package com.example.colockumhillsidefarmapp.vendor.update_store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.colockumhillsidefarmapp.Storage;
import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.example.colockumhillsidefarmapp.R;

import java.util.ArrayList;

public class UpdateStoreActivity extends AppCompatActivity {

    private RecyclerView editStoreRecView;
    private EditStoreProductRecViewAdapter adapter;
    private SwipeRefreshLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_store);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //adapter = new EditStoreProductRecViewAdapter(this, VendorDashboardActivity.class);
        ArrayList<Product> allProducts = Storage.getInstance().getAllProducts(adapter);

        editStoreRecView = findViewById(R.id.editStoreRecView);

        editStoreRecView.setAdapter(adapter);
        editStoreRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setProducts(allProducts);

        layout = findViewById(R.id.swipeRefreshLayoutEditStore);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList<Product> allProducts = Storage.getInstance().getAllProducts(adapter);
                adapter.setProducts(allProducts);
                layout.setRefreshing(false);
            }
        });

    }

    public void reload() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_store, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_add_product:
                Intent intent = new Intent(this, AddProductActivity.class);
                startActivity(intent);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}