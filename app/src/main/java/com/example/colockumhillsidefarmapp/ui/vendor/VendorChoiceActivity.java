package com.example.colockumhillsidefarmapp.ui.vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.MainActivity;
import com.example.colockumhillsidefarmapp.R;

public class VendorChoiceActivity extends AppCompatActivity {

    private Button btnGoToAnalytics, btnGoToUpdateStore, btnGoToUpdateAboutUs, btnAddRecipes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_choice);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();

        System.out.println(btnGoToUpdateStore);

        btnGoToAnalytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VendorChoiceActivity.this, AnalyticsActivity.class);
                startActivity(intent);
            }
        });

        btnGoToUpdateStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VendorChoiceActivity.this, UpdateStoreActivity.class);
                startActivity(intent);
            }
        });

        btnGoToUpdateAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VendorChoiceActivity.this, UpdateAboutUsActivity.class);
                startActivity(intent);
            }
        });

        btnAddRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VendorChoiceActivity.this, AddRecipeActivity.class);
                startActivity(intent);
            }
        });


    }

    private void initVariables() {
        btnGoToAnalytics = findViewById(R.id.btnGoToAnalytics);
        btnGoToUpdateStore = findViewById(R.id.btnGoToUpdateStore);
        btnGoToUpdateAboutUs = findViewById(R.id.btnGoToUpdateAboutUs);
        btnAddRecipes = findViewById(R.id.btnAddRecipes);
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