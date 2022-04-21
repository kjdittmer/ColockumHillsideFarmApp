package com.example.colockumhillsidefarmapp.vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.colockumhillsidefarmapp.user.VendorLoginActivity;
import com.example.colockumhillsidefarmapp.vendor.analytics.AnalyticsFragment;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.vendor.update_about_us.UpdateAboutUsFragment;
import com.example.colockumhillsidefarmapp.vendor.update_recipes.UpdateRecipesFragment;
import com.example.colockumhillsidefarmapp.vendor.update_store.UpdateStoreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VendorDashboardActivity extends AppCompatActivity {

    private Toolbar toolbarUpdateStore, toolbarUpdateRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_dashboard);

        toolbarUpdateStore = findViewById(R.id.toolbarUpdateStore);
        toolbarUpdateRecipes = findViewById(R.id.toolbarUpdateRecipes);

//        toolbar = getSupportActionBar();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_vendor_dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_update_store:
                        selectedFragment = new UpdateStoreFragment();
                        toolbarUpdateStore.setVisibility(View.VISIBLE);
                        toolbarUpdateRecipes.setVisibility(View.GONE);
                        break;
                    case R.id.nav_update_recipes:
                        selectedFragment = new UpdateRecipesFragment();
                        toolbarUpdateStore.setVisibility(View.GONE);
                        toolbarUpdateRecipes.setVisibility(View.VISIBLE);
                        break;
                    case R.id.nav_update_about_us:
                        selectedFragment = new UpdateAboutUsFragment();
                        break;
                    case R.id.nav_analytics:
                        selectedFragment = new AnalyticsFragment() ;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentVendorDashboard,
                        selectedFragment).commit();
                 return true;
            }
        });

        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragmentVendorDashboard, new UpdateStoreFragment()).
                commit();
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setMessage("Are you sure you want to logout?");
//                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Intent intent = new Intent(getApplicationContext(), VendorLoginActivity.class);
//                        startActivity(intent);
//                    }
//                });
//                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                    }
//                });
//                builder.create().show();
//                break;
//            default:
//                break;
//        }
//        return true;
//    }

    public void reload() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}