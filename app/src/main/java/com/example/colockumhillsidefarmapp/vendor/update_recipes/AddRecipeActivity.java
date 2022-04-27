package com.example.colockumhillsidefarmapp.vendor.update_recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.customer.recipes.Recipe;
import com.example.colockumhillsidefarmapp.vendor.VendorDashboardActivity;

public class AddRecipeActivity extends AppCompatActivity {

    private static final String UPDATE_RECIPES = "updateRecipes";

    EditText txtNameAddRecipeName, txtIngredients, txtImageUrlRecAct, txtShortDescAddRecipeAct, txtInstructions;
    ImageView imageView;
    Button btnAddRecipe, btnUploadRecipePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.colockumhillsidefarmapp.R.layout.activity_add_recipe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();

        btnAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateData()) {
                    String name = txtNameAddRecipeName.getText().toString();
                    String imageUrl = txtImageUrlRecAct.getText().toString();
                    String shortDesc = txtShortDescAddRecipeAct.getText().toString();
                    String ingredients = txtIngredients.getText().toString();
                    String instructions = txtInstructions.getText().toString();

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Is the following information correct?\nName: " + name  + "" +
                            "\nImage URL: " + imageUrl +
                            "\nShort Description: " +
                            shortDesc + "\nIngredients:" +
                            ingredients + "\nInstructions:" +
                            instructions
                    );
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DBInterface.getInstance().addRecipe(new Recipe(0, name, imageUrl, shortDesc, ingredients, instructions));
                            Toast.makeText(view.getContext(), name + " was added to the recipe page.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(view.getContext(), VendorDashboardActivity.class);
                            intent.putExtra("fragmentToLoad", UPDATE_RECIPES);
                            startActivity(intent);
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    builder.create().show();

                }
            }
        });

    }


    private boolean validateData() {
        if (TextUtils.isEmpty(txtNameAddRecipeName.getText())) {
            txtNameAddRecipeName.setError("Please provide a name.");
            txtNameAddRecipeName.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtImageUrlRecAct.getText())) {
            txtNameAddRecipeName.setError("Please provide an image URL.");
            txtNameAddRecipeName.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtShortDescAddRecipeAct.getText())) {
            txtNameAddRecipeName.setError("Please provide a short description.");
            txtNameAddRecipeName.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtIngredients.getText())) {
            txtNameAddRecipeName.setError("Please provide a list of ingredients.");
            txtNameAddRecipeName.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtInstructions.getText())) {
            txtNameAddRecipeName.setError("Please provide a list of instructions.");
            txtNameAddRecipeName.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }


    private void initVariables() {
        btnAddRecipe = findViewById(R.id.btnAddRecipe);
        txtNameAddRecipeName = findViewById(R.id.txtNameAddRecipeName);
        txtImageUrlRecAct = findViewById(R.id.txtImageUrlRecAct);
        txtShortDescAddRecipeAct = findViewById(R.id.txtShortDescAddRecipeAct);
        txtIngredients = findViewById(R.id.txtIngredients);
        txtInstructions = findViewById(R.id.txtInstructions);
        imageView = (ImageView) findViewById(R.id.imageView);
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

}