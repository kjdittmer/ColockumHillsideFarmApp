package com.example.colockumhillsidefarmapp.vendor.update_store;

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

import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.Storage;
import com.example.colockumhillsidefarmapp.vendor.VendorDashboardActivity;

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
                if(validateData()) {
                    String name = txtNameAddProdAct.getText().toString();
                    String imageUrl = txtImageUrlProdAct.getText().toString();
                    String shortDesc = txtShortDescAddProdAct.getText().toString();
                    String longDesc = txtLongDescAddProdAct.getText().toString();
                    String packageQuantity = txtPackageQuantityAddProdAct.getText().toString();
                    int quantity = Integer.parseInt(txtQuantityAddProdAct.getText().toString());
                    double price = Double.parseDouble(txtPriceAddProdAct.getText().toString());

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Is the following information correct?\nName: " + name + "\nQuantity: " + quantity + "\nImage URL: " + imageUrl + "\nShort Description: " + shortDesc + "\nLong Description: " + longDesc + "\nPrice: " + price + "\nPackage Quantity: " + packageQuantity);
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Storage.getInstance().addProduct(new Product(-1, name, quantity, imageUrl, shortDesc, longDesc, price, packageQuantity));
                            Toast.makeText(view.getContext(), name + " was added to the store.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(view.getContext(), VendorDashboardActivity.class);
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
        if (TextUtils.isEmpty(txtNameAddProdAct.getText())) {
            txtNameAddProdAct.setError("Please provide a name.");
            txtNameAddProdAct.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtQuantityAddProdAct.getText())) {
            txtQuantityAddProdAct.setError("Please provide a quantity.");
            txtQuantityAddProdAct.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtImageUrlProdAct.getText())) {
            txtImageUrlProdAct.setError("Please provide an image url.");
            txtImageUrlProdAct.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtShortDescAddProdAct.getText())) {
            txtShortDescAddProdAct.setError("Please provide a short description.");
            txtShortDescAddProdAct.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtLongDescAddProdAct.getText())) {
            txtLongDescAddProdAct.setError("Please provide a long description.");
            txtLongDescAddProdAct.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtPriceAddProdAct.getText())) {
            txtPriceAddProdAct.setError("Please provide a price.");
            txtPriceAddProdAct.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtPackageQuantityAddProdAct.getText())) {
            txtPackageQuantityAddProdAct.setError("Please provide a package quantity.");
            txtPackageQuantityAddProdAct.requestFocus();
            return false;
        }
        else {
            return true;
        }
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