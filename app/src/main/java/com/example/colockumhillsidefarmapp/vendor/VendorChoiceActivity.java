package com.example.colockumhillsidefarmapp.vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.vendor.analytics.AnalyticsActivity;
import com.example.colockumhillsidefarmapp.vendor.update_about_us.UpdateAboutUsActivity;
import com.example.colockumhillsidefarmapp.vendor.update_recipes.UpdateRecipesActivity;
import com.example.colockumhillsidefarmapp.vendor.update_store.UpdateStoreActivity;

public class VendorChoiceActivity extends AppCompatActivity {

    private Button btnGoToAnalytics, btnGoToUpdateStore, btnGoToUpdateAboutUs, btnUpdateRecipes;


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

        btnUpdateRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UpdateRecipesActivity.class);
                startActivity(intent);
            }
        });


    }

    private void initVariables() {
        btnGoToAnalytics = findViewById(R.id.btnGoToAnalytics);
        btnGoToUpdateStore = findViewById(R.id.btnGoToUpdateStore);
        btnGoToUpdateAboutUs = findViewById(R.id.btnGoToUpdateAboutUs);
        btnUpdateRecipes = findViewById(R.id.btnUpdateRecipes);
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