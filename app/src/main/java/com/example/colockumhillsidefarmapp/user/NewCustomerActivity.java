package com.example.colockumhillsidefarmapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.MainActivity;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.CustomerDashboardActivity;
import com.example.colockumhillsidefarmapp.customer.recipes.Recipe;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.ShoppingCartItem;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.Transaction;
import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NewCustomerActivity extends AppCompatActivity {

    private EditText txtEmail, txtPassword, txtReenterPassword, txtFullName, txtAge;
    String email, password, reenterPassword, fullName, age;
    private Button btnCreateAccount;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {
                    //registerUser();
                    DBInterface.getInstance().registerUser(fullName,
                            age, email, password, reenterPassword,
                            progressBar, NewCustomerActivity.this);
                }
            }
        });
    }

    private void initVariables () {
        txtEmail = findViewById(R.id.txtEmailNewCustomer);
        txtPassword = findViewById(R.id.txtPasswordNewCustomer);
        txtReenterPassword = findViewById(R.id.txtReenterPasswordNewCustomer);
        btnCreateAccount = findViewById(R.id.btnCreateAccountNewCustomer);
        txtFullName = findViewById(R.id.txtNameNewCustomer);
        txtAge = findViewById(R.id.txtAgeNewCustomer);
        progressBar = findViewById(R.id.progressBarNewCustomer);
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

    private boolean validateData() {
        email = txtEmail.getText().toString().trim();
        fullName = txtFullName.getText().toString().trim();
        age = txtAge.getText().toString().trim();
        password = txtPassword.getText().toString().trim();
        reenterPassword = txtReenterPassword.getText().toString().trim();
        if (fullName.isEmpty()) {
            txtFullName.setError("Full name is required.");
            txtFullName.requestFocus();
            return false;
        }

        if (age.isEmpty()) {
            txtAge.setError("Age is required.");
            txtAge.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            txtEmail.setError("Email is required.");
            txtEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Please provide a valid email address");
            txtEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            txtPassword.setError("Please provide a password.");
            txtPassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            txtPassword.setError("Password must be at least 6 characters.");
            txtPassword.requestFocus();
            return false;
        }

        if (!password.equals(reenterPassword)) {
            txtReenterPassword.setError("Passwords must match.");
            txtReenterPassword.requestFocus();
            return false;
        }

        return true;
    }
}