package com.example.colockumhillsidefarmapp.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.AnalyticsFragment;
import com.example.colockumhillsidefarmapp.CustomerAboutUsFragment;
import com.example.colockumhillsidefarmapp.CustomerContactUsFragment;
import com.example.colockumhillsidefarmapp.CustomerRecipesFragment;
import com.example.colockumhillsidefarmapp.CustomerStoreFragment;
import com.example.colockumhillsidefarmapp.GlobalStorage;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.UpdateAboutUsFragment;
import com.example.colockumhillsidefarmapp.UpdateRecipesFragment;
import com.example.colockumhillsidefarmapp.UpdateStoreFragment;
import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.example.colockumhillsidefarmapp.vendor.update_store.UpdateStoreActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_customer_dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_customer_store:
                        selectedFragment = new CustomerStoreFragment();
                        break;
                    case R.id.nav_customer_recipes:
                        selectedFragment = new CustomerRecipesFragment();
                        break;
                    case R.id.nav_customer_about_us:
                        selectedFragment = new CustomerAboutUsFragment();
                        break;
                    case R.id.nav_customer_contact_us:
                        selectedFragment = new CustomerContactUsFragment() ;
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentCustomerDashboard,
                        selectedFragment).commit();
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().
                replace(R.id.fragmentCustomerDashboard, new CustomerStoreFragment()).
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