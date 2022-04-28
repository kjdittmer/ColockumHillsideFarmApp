package com.example.colockumhillsidefarmapp.customer.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.colockumhillsidefarmapp.R;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeActivity extends AppCompatActivity {

    private Recipe recipe;

    private static final String RECIPE = "recipe";
    private ImageView recipeImage;
    private TextView recipeName, shortDescRec;
    private ListView ingredientsRec2, instructionsRec2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();
        shortDescRec.setMovementMethod(new ScrollingMovementMethod());

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
        Glide.with(this).load(recipe.getImageUrl()).into(recipeImage);

        ArrayList<String> ingredientsString = new ArrayList<>(Arrays.asList(recipe.getIngredients().split(",")));
        ArrayAdapter ingredientsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, ingredientsString);
        ingredientsRec2.setAdapter(ingredientsAdapter);

        ArrayList<String> instructionString = new ArrayList<>(Arrays.asList(recipe.getInstructions().split("/")));
        ArrayAdapter instructionsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, instructionString);
        instructionsRec2.setAdapter(instructionsAdapter);

    }

    private void initVariables() {
        recipeImage = findViewById(R.id.recipeImage);
        recipeName = findViewById(R.id.recipeName);
        shortDescRec = findViewById(R.id.shortDescRec);
        ingredientsRec2 = findViewById(R.id.ingredientsRec2);
        instructionsRec2 = findViewById(R.id.instructionsRec2);
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
