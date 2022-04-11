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
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.Product;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.GlobalStorage;

//having problems with these
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;


public class AddProductActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST = 0;
    private static final int RESULT_LOAD_IMAGE = 1;
    EditText txtNameAddProdAct, txtQuantityAddProdAct, txtImageUrlProdAct, txtShortDescAddProdAct, txtLongDescAddProdAct, txtPriceAddProdAct, txtPackageQuantityAddProdAct;
    ImageView imageView;
    Button btnAddProductAddProdAct, btnUploadPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.colockumhillsidefarmapp.R.layout.activity_add_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
        }

        btnUploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        btnAddProductAddProdAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtNameAddProdAct.getText().toString();
                String imageUrl = txtImageUrlProdAct.getText().toString();
                String shortDesc = txtShortDescAddProdAct.getText().toString();
                String longDesc = txtLongDescAddProdAct.getText().toString();
                String packageQuantity = txtPackageQuantityAddProdAct.getText().toString();
                int quantity = Integer.parseInt(txtQuantityAddProdAct.getText().toString());
                double price = Double.parseDouble(txtPriceAddProdAct.getText().toString());
                if(validateData()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Is the following information correct?\nName: " + name + "\nQuantity: " + quantity + "\nImage URL: " + imageUrl + "\nShort Description: " + shortDesc + "\nLong Description: " + longDesc + "\nPrice: " + price + "\nPackage Quantity: " + packageQuantity);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            GlobalStorage.getInstance().addProductToAllProducts(new Product(0, name, quantity, imageUrl, shortDesc, longDesc, price, packageQuantity));
                            Toast.makeText(view.getContext(), name + " was added to the store.", Toast.LENGTH_SHORT).show();
                            txtNameAddProdAct.setText("");
                            txtImageUrlProdAct.setText("");
                            txtShortDescAddProdAct.setText("");
                            txtLongDescAddProdAct.setText("");
                            txtPackageQuantityAddProdAct.setText("");
                            txtQuantityAddProdAct.setText("");
                            txtPriceAddProdAct.setText("");
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
        btnAddProductAddProdAct = findViewById(R.id.btnAddProductAddProdAct);
        btnUploadPicture = findViewById(R.id.btnUploadPicture);
        txtNameAddProdAct = findViewById(R.id.txtNameAddProdAct);
        txtQuantityAddProdAct = findViewById(R.id.txtQuantityAddProdAct);
        txtImageUrlProdAct = findViewById(R.id.txtImageUrlProdAct);
        txtShortDescAddProdAct = findViewById(R.id.txtShortDescAddProdAct);
        txtLongDescAddProdAct = findViewById(R.id.txtLongDescAddProdAct);
        txtPriceAddProdAct = findViewById(R.id.txtPriceAddProdAct);
        txtPackageQuantityAddProdAct = findViewById(R.id.txtPackageQuantityAddProdAct);
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