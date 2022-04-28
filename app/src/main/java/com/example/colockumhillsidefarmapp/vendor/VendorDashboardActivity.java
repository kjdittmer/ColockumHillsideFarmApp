package com.example.colockumhillsidefarmapp.vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.colockumhillsidefarmapp.user.VendorLoginActivity;
import com.example.colockumhillsidefarmapp.vendor.analytics.AnalyticsFragment;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.vendor.update_about_us.AddAboutUsActivity;
import com.example.colockumhillsidefarmapp.vendor.update_about_us.UpdateAboutUsFragment;
import com.example.colockumhillsidefarmapp.vendor.update_recipes.AddRecipeActivity;
import com.example.colockumhillsidefarmapp.vendor.update_recipes.UpdateRecipesFragment;
import com.example.colockumhillsidefarmapp.vendor.update_store.AddProductActivity;
import com.example.colockumhillsidefarmapp.vendor.update_store.UpdateStoreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class VendorDashboardActivity extends AppCompatActivity {

    private static final String UPDATE_RECIPES = "updateRecipes";
    private static final String UPDATE_ABOUT_US = "updateAboutUs";
    private static final String ANALYTICS = "analytics";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_dashboard);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_logout_24);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_vendor_dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_update_store:
                        getSupportActionBar().setTitle("Update Store");
                        selectedFragment = new UpdateStoreFragment();
                        break;
                    case R.id.nav_update_recipes:
                        getSupportActionBar().setTitle("Update Recipes");
                        selectedFragment = new UpdateRecipesFragment();
                        break;
                    case R.id.nav_update_about_us:
                        getSupportActionBar().setTitle("Update About Us");
                        selectedFragment = new UpdateAboutUsFragment();
                        break;
                    case R.id.nav_analytics:
                        getSupportActionBar().setTitle("Analytics");
                        selectedFragment = new AnalyticsFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentVendorDashboard,
                        selectedFragment).commit();
                 return true;
            }
        });

        if (getIntent().hasExtra("fragmentToLoad")) {
            String fragmentToLoad = "fragmentToLoad";
            switch (getIntent().getExtras().getString("fragmentToLoad")) {
                case UPDATE_RECIPES:
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragmentVendorDashboard, new UpdateRecipesFragment()).
                            commit();
                    getSupportActionBar().setTitle("Update Recipes");
                    bottomNavigationView.setSelectedItemId(R.id.nav_update_recipes);
                    break;
                case UPDATE_ABOUT_US:
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragmentVendorDashboard, new UpdateAboutUsFragment()).
                            commit();
                    getSupportActionBar().setTitle("Update About Us");
                    bottomNavigationView.setSelectedItemId(R.id.nav_update_about_us);
                    break;
                case ANALYTICS:
                    getSupportFragmentManager().beginTransaction().
                            replace(R.id.fragmentVendorDashboard, new AnalyticsFragment()).
                            commit();
                    getSupportActionBar().setTitle("Analytics");
                    bottomNavigationView.setSelectedItemId(R.id.nav_analytics);
                    break;
            }
        } else {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.fragmentVendorDashboard, new UpdateStoreFragment()).
                    commit();
            getSupportActionBar().setTitle("Update Store");
        }
    }


        @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case android.R.id.home:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getApplicationContext(), VendorLoginActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.create().show();
                break;
            case R.id.action_add_product:
                intent = new Intent(this, AddProductActivity.class);
                startActivity(intent);
                break;
            case R.id.action_add_recipe:
                intent = new Intent(this, AddRecipeActivity.class);
                startActivity(intent);
                break;
            case R.id.action_add_info:
                intent = new Intent(this, AddAboutUsActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    public void reloadStore() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void reloadRecipes() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent().putExtra("fragmentToLoad", UPDATE_RECIPES));
        overridePendingTransition(0, 0);
    }
}