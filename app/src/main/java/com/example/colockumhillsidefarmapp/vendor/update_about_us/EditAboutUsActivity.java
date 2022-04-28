package com.example.colockumhillsidefarmapp.vendor.update_about_us;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.about_us.Info;
import com.example.colockumhillsidefarmapp.vendor.VendorDashboardActivity;

public class EditAboutUsActivity extends AppCompatActivity {
    private static final String UPDATE_ABOUT_US = "updateAboutUs";
    private static final String INFO = "info";

    private EditText txtNameEditInfoHeader, txtEditInformation, txtImageUrlEditAboutUs;
    private Button btnEditInfo;

    private Info infoToEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_about_us);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();

        Intent intent = getIntent();
        if(getIntent() != null){
            infoToEdit = intent.getParcelableExtra(INFO);
            if(infoToEdit != null){
                setData(infoToEdit);
            }
        }

        btnEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {
                    Info editedInfo = new Info(infoToEdit.getId(), txtNameEditInfoHeader.getText().toString(), txtImageUrlEditAboutUs.getText().toString(),
                            txtEditInformation.getText().toString());

                    DBInterface.getInstance().editInfo(infoToEdit, editedInfo);
                    Toast.makeText(EditAboutUsActivity.this, infoToEdit.getName() + " edited.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(view.getContext(), VendorDashboardActivity.class);
                    intent.putExtra("fragmentToLoad", UPDATE_ABOUT_US);
                    startActivity(intent);
                }
            }
        });
    }

    private void initVariables() {
        txtNameEditInfoHeader = findViewById(R.id.txtNameEditInfoHeader);
        txtImageUrlEditAboutUs = findViewById(R.id.txtImageUrlEditAboutUs);
        txtEditInformation = findViewById(R.id.txtEditInformation);
    }

    private void setData(Info info){
        txtNameEditInfoHeader.setText(info.getName());
        txtImageUrlEditAboutUs.setText(info.getImageUrl());
        txtEditInformation.setText(info.getText());
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

    private boolean validateData() {
        if (TextUtils.isEmpty(txtNameEditInfoHeader.getText())) {
            txtNameEditInfoHeader.setError("Please provide a title.");
            txtNameEditInfoHeader.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtImageUrlEditAboutUs.getText())) {
            txtImageUrlEditAboutUs.setError("Please provide an image url.");
            txtImageUrlEditAboutUs.requestFocus();
            return false;
        }
        else if (TextUtils.isEmpty(txtEditInformation.getText())) {
            txtEditInformation.setError("Please provide information.");
            txtEditInformation.requestFocus();
            return false;
        }
        else {
            return true;
        }
    }

}
