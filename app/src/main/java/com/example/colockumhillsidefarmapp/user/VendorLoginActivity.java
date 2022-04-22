package com.example.colockumhillsidefarmapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.vendor.VendorDashboardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class VendorLoginActivity extends AppCompatActivity {

    private EditText txtEmail, txtPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void initVariables () {
        txtEmail = findViewById(R.id.txtEmailVendorLogin);
        txtPassword = findViewById(R.id.txtPasswordVendorLogin);
        btnLogin = findViewById(R.id.btnLoginVendorLogin);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBarVendorLogin);
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

    private void login () {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();

        if (!validateLogin(email, password)) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), VendorDashboardActivity.class));
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(VendorLoginActivity.this,
                            "Failed to login!",
                            Toast.LENGTH_SHORT)
                            .show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private boolean validateLogin (String email, String password) {
        if (email.isEmpty()) {
            txtEmail.setError("Email is required.");
            txtEmail.requestFocus();
            return false;
        }

        if (!email.equals("suzanne@colockumhillsidefarm.com")) {
            txtEmail.setError("Only login if you are a vendor.");
            txtEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            txtPassword.setError("Password is required.");
            txtPassword.requestFocus();
            return false;
        }

        return true;
    }
}