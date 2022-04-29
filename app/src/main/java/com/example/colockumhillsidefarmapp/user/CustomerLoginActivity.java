package com.example.colockumhillsidefarmapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.CustomerDashboardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CustomerLoginActivity extends AppCompatActivity {

    private EditText txtEmail, txtPassword;
    private Button btnLogin;
    private TextView txtCreateAccount, txtVendorLogin;
    private ProgressBar progressBar;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        initVariables();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateLogin()) {
                    DBInterface.getInstance().loginCustomer(email, password, progressBar, CustomerLoginActivity.this);
                }
            }
        });

        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(), NewCustomerActivity.class));
            }
        });

        txtVendorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), VendorLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initVariables() {
        txtEmail = findViewById(R.id.txtEmailCustomerLogin);
        txtPassword = findViewById(R.id.txtPasswordCustomerLogin);
        btnLogin = findViewById(R.id.btnLoginCustomerLogin);
        txtCreateAccount = findViewById(R.id.txtCreateAccountCustomerLogin);
        txtVendorLogin = findViewById(R.id.txtVendorLoginCustomerLogin);
        progressBar = findViewById(R.id.progressBarCustomerLogin);
    }

    private boolean validateLogin() {
        email = txtEmail.getText().toString().trim();
        password = txtPassword.getText().toString();

        if (email.isEmpty()) {
            txtEmail.setError("Email is required.");
            txtEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Please provide a valid email.");
            txtEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            txtPassword.setError("Password is required.");
            txtPassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            txtPassword.setError("Password must be at least 6 characters");
            txtPassword.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        return;
    }
}