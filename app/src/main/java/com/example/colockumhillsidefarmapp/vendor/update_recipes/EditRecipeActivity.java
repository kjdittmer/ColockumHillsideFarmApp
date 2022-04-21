package com.example.colockumhillsidefarmapp.vendor.update_recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.GlobalStorage;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.recipes.Recipe;
import com.example.colockumhillsidefarmapp.vendor.VendorDashboardActivity;

public class EditRecipeActivity extends AppCompatActivity {

    private static final String RECIPE = "recipe";

    private EditText txtNameEditRecipe, txtIngredientsEditRecipe, txtImageUrlEditRecipe,
            txtShortDescEditRecipe, txtInstructionsEditRecipe;
    private Button btnUpdateRecipeEditRecipe;

    private Recipe recipeToEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();

        Intent intent = getIntent();
        if(getIntent() != null){
            recipeToEdit = intent.getParcelableExtra(RECIPE);
            if(recipeToEdit != null){
                setData(recipeToEdit);
            }
        }

        btnUpdateRecipeEditRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe editedRecipe = new Recipe(recipeToEdit.getId(), txtNameEditRecipe.getText().toString(), txtImageUrlEditRecipe.getText().toString(),
                        txtShortDescEditRecipe.getText().toString(), txtIngredientsEditRecipe.getText().toString(),
                        txtInstructionsEditRecipe.getText().toString());

                GlobalStorage.getInstance().editRecipe(recipeToEdit, editedRecipe);
                Toast.makeText(EditRecipeActivity.this, recipeToEdit.getName() + " edited.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(view.getContext(), VendorDashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initVariables() {
        txtNameEditRecipe = findViewById(R.id.txtNameEditRecipe);
        txtIngredientsEditRecipe = findViewById(R.id.txtIngredientsEditRecipe);
        txtImageUrlEditRecipe = findViewById(R.id.txtImageUrlEditRecipe);
        txtShortDescEditRecipe = findViewById(R.id.txtShortDescEditRecipe);
        txtInstructionsEditRecipe = findViewById(R.id.txtInstructionsEditRecipe);
        btnUpdateRecipeEditRecipe = findViewById(R.id.btnUpdateRecipeEditRecipe);
    }

    private void setData(Recipe recipe){
        txtNameEditRecipe.setText(recipe.getName());
        txtIngredientsEditRecipe.setText(recipe.getIngredients());
        txtImageUrlEditRecipe.setText(recipe.getImageUrl());
        txtShortDescEditRecipe.setText(recipe.getShortDesc());
        txtInstructionsEditRecipe.setText(recipe.getInstructions());
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