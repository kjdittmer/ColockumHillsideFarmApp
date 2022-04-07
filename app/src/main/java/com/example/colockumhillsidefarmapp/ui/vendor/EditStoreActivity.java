package com.example.colockumhillsidefarmapp.ui.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

        adapter = new EditStoreProductRecViewAdapter(this, this);
        editStoreRecView = findViewById(R.id.editStoreRecView);

        editStoreRecView.setAdapter(adapter);
        editStoreRecView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter.setProducts(GlobalStorage.getInstance().getEditingProducts());
        adapter.notifyDataSetChanged();

    }

    public void reload() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

}