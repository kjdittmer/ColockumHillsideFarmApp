package com.example.colockumhillsidefarmapp.ui.vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

        adapter = new EditStoreProductRecViewAdapter(this);
        editStoreRecView = findViewById(R.id.editStoreRecView);

        editStoreRecView.setAdapter(adapter);
        editStoreRecView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(10, "Fish", 12,
                "https://i.pinimg.com/736x/59/25/60/59256023c47736ad703b7542aec8030f.jpg",
                "short", "long", 3.99, "lb"));
        products.add(new Product(11, "Fish", 12,
                "https://i.pinimg.com/736x/59/25/60/59256023c47736ad703b7542aec8030f.jpg",
                "short", "long", 3.99, "lb"));
        adapter.setProducts(products);

    }
}