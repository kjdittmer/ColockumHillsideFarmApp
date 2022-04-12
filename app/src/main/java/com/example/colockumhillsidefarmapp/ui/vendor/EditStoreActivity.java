package com.example.colockumhillsidefarmapp.ui.vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_store);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new EditStoreProductRecViewAdapter(this, this);
        ArrayList<Product> allProducts = GlobalStorage.getInstance().getAllProductsForEditStore(adapter);

        editStoreRecView = findViewById(R.id.editStoreRecView);

        editStoreRecView.setAdapter(adapter);
        editStoreRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setProducts(allProducts);
        adapter.notifyDataSetChanged();

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