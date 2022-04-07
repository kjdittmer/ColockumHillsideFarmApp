package com.example.colockumhillsidefarmapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCartActivity extends AppCompatActivity {
    private CartProductRecViewAdapter adapter;
    private RecyclerView recyclerView;
    private TextView txtTitleShoppingCartAct, txtTotalShoppingCartAct;
    private Button btnContinueShoppingShoppingCartAct, btnCheckoutShoppingCartAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        initData();
        setData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void initData() {
        recyclerView = findViewById(R.id.cartRecView);
        txtTitleShoppingCartAct = findViewById(R.id.txtTitleShoppingCartAct);
        txtTotalShoppingCartAct = findViewById(R.id.txtTotalShoppingCartAct);
        btnContinueShoppingShoppingCartAct = findViewById(R.id.btnContinueShoppingShoppingCartAct);
        btnCheckoutShoppingCartAct = findViewById(R.id.btnCheckoutShoppingCartAct);
    }

    private void setData() {
        adapter = new CartProductRecViewAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Product> productsInCart = new ArrayList<>();
        HashMap<Product, Integer> cart = ShoppingCart.getInstance().getCart();
        for (Product product : cart.keySet()){
            productsInCart.add(product);
        }
        adapter.setProductsInCart(productsInCart);

        double totalCost = adapter.getTotalCost();
        DecimalFormat df = new DecimalFormat("0.00");
        txtTotalShoppingCartAct.setText("Total: $" + df.format(totalCost));

        btnCheckoutShoppingCartAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "You are being navigated to a secure checkout :)", Toast.LENGTH_SHORT
                ).show();
            }
        });

        btnContinueShoppingShoppingCartAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
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