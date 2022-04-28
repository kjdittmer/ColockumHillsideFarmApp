package com.example.colockumhillsidefarmapp.vendor.update_about_us;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.about_us.Info;
import com.example.colockumhillsidefarmapp.vendor.VendorDashboardActivity;

public class AddAboutUsActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST = 0;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final String UPDATE_ABOUT_US = "updateAboutUs";

    EditText txtNameAddInfoHeader, txtAddInformation, txtImageUrlAddAboutUs;
    ImageView imageView;
    Button btnAddInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.colockumhillsidefarmapp.R.layout.activity_add_about_us);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
        }
        btnAddInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateData()) {
                    String name = txtNameAddInfoHeader.getText().toString();
                    String imageUrl = txtImageUrlAddAboutUs.getText().toString();
                    String text = txtAddInformation.getText().toString();

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Is the following information correct?\nName: " + name  + "" +
                            "\nImage URL: " + imageUrl +
                            "\nText: " + text
                    );
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DBInterface.getInstance().addInfo(new Info(0, name, imageUrl, text));
                            Toast.makeText(view.getContext(), name + " was added to the about us page.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(view.getContext(), VendorDashboardActivity.class);
                            intent.putExtra("fragmentToLoad", UPDATE_ABOUT_US);
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
        if (TextUtils.isEmpty(txtNameAddInfoHeader.getText())) {
            txtNameAddInfoHeader.setError("Please provide a title.");
            txtNameAddInfoHeader.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtImageUrlAddAboutUs.getText())) {
            txtImageUrlAddAboutUs.setError("Please provide an image URL.");
            txtImageUrlAddAboutUs.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtAddInformation.getText())) {
            txtAddInformation.setError("Please provide information.");
            txtAddInformation.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }


    private void initVariables() {
        btnAddInfo = findViewById(R.id.btnAddInfo);
        txtNameAddInfoHeader = findViewById(R.id.txtNameAddInfoHeader);
        txtImageUrlAddAboutUs = findViewById(R.id.txtImageUrlAddAboutUs);
        txtAddInformation = findViewById(R.id.txtAddInformation);
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
                Intent intent = new Intent(getApplicationContext(), VendorDashboardActivity.class);
                intent.putExtra("fragmentToLoad", UPDATE_ABOUT_US);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}