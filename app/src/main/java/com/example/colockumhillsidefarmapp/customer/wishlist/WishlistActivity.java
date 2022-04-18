package com.example.colockumhillsidefarmapp.customer.wishlist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.store.Product;

import java.util.ArrayList;

public class WishlistActivity extends AppCompatActivity {
    private WishlistProductRecViewAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initData();
        setData();
    }

    private void initData () {
        recyclerView = findViewById(R.id.wishlistRecView);
    }

    private void setData () {
        adapter = new WishlistProductRecViewAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Product> wishlistProducts = Wishlist.getInstance(this).getWishlist();
        adapter.setProductsInWishlist(wishlistProducts);
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