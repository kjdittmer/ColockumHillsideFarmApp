package com.example.colockumhillsidefarmapp.ui.vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.R;

public class UpdateStoreActivity extends AppCompatActivity {

    private Button btnAddProduct, btnRemoveProduct, btnEditProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.colockumhillsidefarmapp.R.layout.activity_update_store);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddProductActivity.class);
                startActivity(intent);
            }
        });

        btnEditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditStoreActivity.class);
                startActivity(intent);
            }
        });

        btnRemoveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UpdateStoreActivity.this, "Removing item...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initVariables() {
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnRemoveProduct = findViewById(R.id.btnRemoveProduct);
        btnEditProduct = findViewById(R.id.btnEditProduct);
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