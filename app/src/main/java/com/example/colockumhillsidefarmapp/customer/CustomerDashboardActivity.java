package com.example.colockumhillsidefarmapp.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.colockumhillsidefarmapp.BuildConfig;
import com.example.colockumhillsidefarmapp.customer.about_us.CustomerAboutUsFragment;
import com.example.colockumhillsidefarmapp.customer.contact_us.CustomerContactUsFragment;
import com.example.colockumhillsidefarmapp.customer.favorites.FavoritesActivity;
import com.example.colockumhillsidefarmapp.customer.recipes.CustomerRecipesFragment;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.ShoppingCart;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.ShoppingCartActivity;
import com.example.colockumhillsidefarmapp.customer.store.CustomerStoreFragment;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.transaction_history.CustomerTransactionHistoryFragment;
import com.example.colockumhillsidefarmapp.customer.wishlist.WishlistActivity;
import com.example.colockumhillsidefarmapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mailchimp.sdk.core.MailchimpSdkConfiguration;
import com.mailchimp.sdk.main.Mailchimp;

public class CustomerDashboardActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        toolbar = findViewById(R.id.toolbarCustomerDashboard);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Store");
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.ic_logout_24);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_customer_dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()) {
                    case R.id.nav_customer_store:
                        selectedFragment = new CustomerStoreFragment();
                        toolbar.setTitle("Store");
                        break;
                    case R.id.nav_customer_recipes:
                        selectedFragment = new CustomerRecipesFragment();
                        toolbar.setTitle("Recipes");
                        break;
                    case R.id.nav_customer_about_us:
                        selectedFragment = new CustomerAboutUsFragment();
                        toolbar.setTitle("About Us");
                        break;
                    case R.id.nav_customer_contact_us:
                        selectedFragment = new CustomerContactUsFragment() ;
                        toolbar.setTitle("Contact Us");
                        break;
                    case R.id.nav_customer_transaction_history:
                        selectedFragment = new CustomerTransactionHistoryFragment() ;
                        toolbar.setTitle("History");
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

        initMailchimp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
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
                        //this?
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
            case R.id.action_shopping_cart:
                intent = new Intent(getApplicationContext(), ShoppingCartActivity.class);
                startActivity(intent);
                break;
            case R.id.action_favorites:
                intent = new Intent(getApplicationContext(), FavoritesActivity.class);
                startActivity(intent);
                break;
            case R.id.action_wishlist:
                intent = new Intent(getApplicationContext(), WishlistActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initMailchimp () {
        String sdkKey = "389c0f6b13dc44edf3a09fb5373df74b-us19"; //SDK Key from Mailchimp
        boolean isDebugBuild = BuildConfig.DEBUG;
        MailchimpSdkConfiguration configuration = new MailchimpSdkConfiguration.Builder(getApplicationContext(), sdkKey)
                .isDebugModeEnabled(isDebugBuild)
                .isAutoTaggingEnabled(true)
                .build();

        Mailchimp mailchimpSdk = Mailchimp.initialize(configuration);
    }
}