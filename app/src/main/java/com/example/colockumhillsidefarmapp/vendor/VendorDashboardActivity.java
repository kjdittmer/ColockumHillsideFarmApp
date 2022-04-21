package com.example.colockumhillsidefarmapp.vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.colockumhillsidefarmapp.AnalyticsFragment;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.UpdateAboutUsFragment;
import com.example.colockumhillsidefarmapp.UpdateRecipesFragment;
import com.example.colockumhillsidefarmapp.UpdateStoreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VendorDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_dashboard);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_vendor_dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_update_store:
                        selectedFragment = new UpdateStoreFragment();
                        break;
                    case R.id.nav_update_recipes:
                        selectedFragment = new UpdateRecipesFragment();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.create().show();
                break;
            default:
                break;
        }
        return true;
    }
}