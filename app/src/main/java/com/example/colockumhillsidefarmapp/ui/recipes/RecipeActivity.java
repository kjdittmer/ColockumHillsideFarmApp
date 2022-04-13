package com.example.colockumhillsidefarmapp.ui.recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.colockumhillsidefarmapp.GlobalStorage;
import com.example.colockumhillsidefarmapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    private Recipe recipe;

    private static final String RECIPE = "recipe";
    private ImageView recipeImage;
    private TextView recipeName, shortDescRec, ingredientsRec, instructionsRec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();

        Intent intent = getIntent();
        if (getIntent() != null) {
            Recipe recipe = intent.getParcelableExtra(RECIPE);
            if (recipe != null) {
                setData(recipe);
            }
        }

    }

    private void setData(Recipe recipe) {
        recipeName.setText(recipe.getName());
        shortDescRec.setText(recipe.getShortDesc());
        ingredientsRec.setText(recipe.getIngredients());
        instructionsRec.setText(recipe.getInstructions());
        Glide.with(this).load(recipe.getImageUrl()).into(recipeImage);    }

    private void initVariables() {
        recipeImage = findViewById(R.id.recipeImage);
        recipeName = findViewById(R.id.recipeName);
        shortDescRec = findViewById(R.id.shortDescRec);
        ingredientsRec = findViewById(R.id.ingredientsRec);
        instructionsRec = findViewById(R.id.instructionsRec);
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