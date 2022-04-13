package com.example.colockumhillsidefarmapp.ui.vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.colockumhillsidefarmapp.GlobalStorage;
import com.example.colockumhillsidefarmapp.Product;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.StoreProductRecViewAdapter;

import java.util.ArrayList;

public class EditStoreActivity extends AppCompatActivity {

    private RecyclerView editStoreRecView;
    private EditStoreProductRecViewAdapter adapter;
    private SwipeRefreshLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_store);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new EditStoreProductRecViewAdapter(this, this);
        ArrayList<Product> allProducts = GlobalStorage.getInstance().getAllProducts(adapter);

        editStoreRecView = findViewById(R.id.editStoreRecView);

        editStoreRecView.setAdapter(adapter);
        editStoreRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setProducts(allProducts);

        layout = findViewById(R.id.swipeRefreshLayoutEditStore);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList<Product> allProducts = GlobalStorage.getInstance().getAllProducts(adapter);
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

}