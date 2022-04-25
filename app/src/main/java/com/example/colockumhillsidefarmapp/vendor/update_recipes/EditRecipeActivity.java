package com.example.colockumhillsidefarmapp.vendor.update_recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.recipes.Recipe;
import com.example.colockumhillsidefarmapp.vendor.VendorDashboardActivity;

public class EditRecipeActivity extends AppCompatActivity {

    private static final String UPDATE_RECIPES = "updateRecipes";
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
                if (validateData()) {
                    Recipe editedRecipe = new Recipe(recipeToEdit.getId(), txtNameEditRecipe.getText().toString(), txtImageUrlEditRecipe.getText().toString(),
                            txtShortDescEditRecipe.getText().toString(), txtIngredientsEditRecipe.getText().toString(),
                            txtInstructionsEditRecipe.getText().toString());

                    DBInterface.getInstance().editRecipe(recipeToEdit, editedRecipe);
                    Toast.makeText(EditRecipeActivity.this, recipeToEdit.getName() + " edited.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), VendorDashboardActivity.class);
                    intent.putExtra("fragmentToLoad", UPDATE_RECIPES);
                    startActivity(intent);
                }
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
                Intent intent = new Intent(getApplicationContext(), VendorDashboardActivity.class);
                intent.putExtra("fragmentToLoad", UPDATE_RECIPES);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateData() {
        if (TextUtils.isEmpty(txtNameEditRecipe.getText())) {
            txtNameEditRecipe.setError("Please provide a name.");
            txtNameEditRecipe.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtImageUrlEditRecipe.getText())) {
            txtImageUrlEditRecipe.setError("Please provide an image url.");
            txtImageUrlEditRecipe.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtShortDescEditRecipe.getText())) {
            txtShortDescEditRecipe.setError("Please provide a short description.");
            txtShortDescEditRecipe.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtIngredientsEditRecipe.getText())) {
            txtIngredientsEditRecipe.setError("Please provide a list of ingredients.");
            txtIngredientsEditRecipe.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtInstructionsEditRecipe.getText())) {
            txtInstructionsEditRecipe.setError("Please provide a list of instructions.");
            txtInstructionsEditRecipe.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }

}