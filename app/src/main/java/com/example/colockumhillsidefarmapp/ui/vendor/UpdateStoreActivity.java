package com.example.colockumhillsidefarmapp.ui.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.R;

public class UpdateStoreActivity extends AppCompatActivity {

    private Button btnAddProduct, btnRemoveProduct, btnEditProduct, btnBackFromUpdateStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.colockumhillsidefarmapp.R.layout.activity_update_store);

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
                Toast.makeText(UpdateStoreActivity.this, "Editing item...", Toast.LENGTH_SHORT).show();
            }
        });

        btnRemoveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UpdateStoreActivity.this, "Removing item...", Toast.LENGTH_SHORT).show();
            }
        });

        btnBackFromUpdateStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateStoreActivity.this, VendorChoiceActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initVariables() {
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnRemoveProduct = findViewById(R.id.btnRemoveProduct);
        btnEditProduct = findViewById(R.id.btnEditProduct);
        btnBackFromUpdateStore = findViewById(R.id.btnBackFromUpdateStore);
    }
}