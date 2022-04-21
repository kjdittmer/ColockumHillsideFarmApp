package com.example.colockumhillsidefarmapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.vendor.VendorDashboardActivity;


public class VendorLoginActivity extends AppCompatActivity {

    private EditText txtEmail, txtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), VendorDashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initVariables () {
        txtEmail = findViewById(R.id.txtEmailVendorLogin);
        txtPassword = findViewById(R.id.txtPasswordVendorLogin);
        btnLogin = findViewById(R.id.btnLoginVendorLogin);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CustomerLoginActivity.class);
        startActivity(intent);
    }
}