package com.example.colockumhillsidefarmapp.ui.vendor;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.GlobalStorage;
import com.example.colockumhillsidefarmapp.ui.recipes.Recipe;

public class AddRecipeActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST = 0;
    private static final int RESULT_LOAD_IMAGE = 1;
    EditText txtNameAddRecipeName, txtIngredients, txtImageUrlRecAct, txtShortDescAddRecipeAct, txtInstructions;
    ImageView imageView;
    Button btnAddRecipe, btnUploadRecipePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.colockumhillsidefarmapp.R.layout.activity_add_recipe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
        }

        btnUploadRecipePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        btnAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtNameAddRecipeName.getText().toString();
                //String imageUrl = txtImageUrlRecAct.getText().toString();
                //String shortDesc = txtShortDescAddRecipeAct.getText().toString();
                String ingredients = txtIngredients.getText().toString();
                String instructions = txtInstructions.getText().toString();
                if(validateData()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Is the following information correct?\nName: " + name  + "" +
                            //"\nImage URL: " + imageUrl +
                            "\nShort Description: " +
                            //shortDesc + "\nIngredients:" +
                            ingredients + "\nInstructions:" +
                            instructions
                    );
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            GlobalStorage.getInstance().addRecipeToAllRecipes(new Recipe(0, name,
                                    //imageUrl,
                                    "testing",
                                    //shortDesc,
                                    "testing",
                                     ingredients, instructions));
                            Toast.makeText(view.getContext(), name + " was added to the recipe page.", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    builder.create().show();

                } else {
                    Toast.makeText(view.getContext(), "Please enter valid data.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private boolean validateData() {
        return true;
    }

    private void initVariables() {
        btnAddRecipe = findViewById(R.id.btnAddRecipe);
        btnUploadRecipePicture = findViewById(R.id.btnUploadRecipePicture);
        txtNameAddRecipeName = findViewById(R.id.txtNameAddRecipeName);
        txtImageUrlRecAct = findViewById(R.id.txtImageUrlProdAct);
        txtShortDescAddRecipeAct = findViewById(R.id.txtShortDescAddProdAct);
        txtIngredients = findViewById(R.id.txtIngredients);
        txtInstructions = findViewById(R.id.txtInstructions);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                }
        }
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