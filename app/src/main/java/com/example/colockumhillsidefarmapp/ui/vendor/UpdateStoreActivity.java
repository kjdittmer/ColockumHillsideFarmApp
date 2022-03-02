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

    private Button btnAddItem, btnRemoveItem, btnEditItem, btnBackFromUpdateStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.colockumhillsidefarmapp.R.layout.activity_update_store);

        initVariables();

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UpdateStoreActivity.this, "Adding item...", Toast.LENGTH_SHORT).show();
            }
        });

        btnEditItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UpdateStoreActivity.this, "Editing item...", Toast.LENGTH_SHORT).show();
            }
        });

        btnRemoveItem.setOnClickListener(new View.OnClickListener() {
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
        btnAddItem = findViewById(R.id.btnAddItem);
        btnRemoveItem = findViewById(R.id.btnRemoveItem);
        btnEditItem = findViewById(R.id.btnEditItem);
        btnBackFromUpdateStore = findViewById(R.id.btnBackFromUpdateStore);
    }
}