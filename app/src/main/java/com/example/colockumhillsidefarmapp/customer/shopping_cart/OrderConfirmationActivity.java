package com.example.colockumhillsidefarmapp.customer.shopping_cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.CustomerDashboardActivity;
import com.example.colockumhillsidefarmapp.customer.store.StoreProductRecViewAdapter;
import com.example.colockumhillsidefarmapp.vendor.analytics.TransactionRecViewAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class OrderConfirmationActivity extends AppCompatActivity {

    private ArrayList<Transaction> transactions;
    private RecyclerView recyclerView;
    private OrderConfirmationTransactionRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(getIntent() != null){
            transactions = getIntent().getParcelableArrayListExtra("transactions");
        }

        adapter = new OrderConfirmationTransactionRecViewAdapter(this);
        adapter.setTransactions(transactions);
        recyclerView = findViewById(R.id.orderPlacedRecView);

        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, CustomerDashboardActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}