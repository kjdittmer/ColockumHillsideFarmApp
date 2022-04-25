package com.example.colockumhillsidefarmapp.vendor.update_recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.colockumhillsidefarmapp.Storage;

import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.recipes.Recipe;

import java.util.ArrayList;

public class UpdateRecipesActivity extends AppCompatActivity {

    private RecyclerView updateRecipesRecView;
    private UpdateRecipeRecViewAdapter adapter;
    private SwipeRefreshLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_recipes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //adapter = new UpdateRecipeRecViewAdapter(this, (UpdateRecipesActivity) this);
        ArrayList<Recipe> allRecipes = Storage.getInstance().getAllRecipes(adapter);

        updateRecipesRecView = findViewById(R.id.updateRecipesRecView);

        updateRecipesRecView.setAdapter(adapter);
        updateRecipesRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setRecipes(allRecipes);

        layout = findViewById(R.id.swipeRefreshLayoutUpdateRecipes);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList<Recipe> allRecipes = Storage.getInstance().getAllRecipes(adapter);
                adapter.setRecipes(allRecipes);
                layout.setRefreshing(false);
            }
        });

    }

    public void reload() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_store, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_add_product:
                Intent intent = new Intent(this, AddRecipeActivity.class);
                startActivity(intent);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}