package com.example.colockumhillsidefarmapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.MainActivity;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.CustomerDashboardActivity;
import com.example.colockumhillsidefarmapp.customer.recipes.Recipe;
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
    private Button btnCreateAccount;
    private FirebaseAuth mAuth;
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
                registerUser();
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
        mAuth = FirebaseAuth.getInstance();
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

    private void registerUser () {
        String email = txtEmail.getText().toString().trim();
        String fullName = txtFullName.getText().toString().trim();
        String age = txtAge.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String reenterPassword = txtReenterPassword.getText().toString().trim();

        if (!validateData(email, fullName, age, password, reenterPassword)) {
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            User user = new User(fullName, age, email,
                                    new ArrayList<Product>(),
                                    new ArrayList<Recipe>(),
                                    new ArrayList<Product>(),
                                    new ArrayList<Product>(),
                                    new ArrayList<Transaction>()
                            );
                            FirebaseDatabase.getInstance().getReference("user")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(NewCustomerActivity.this, "You have been registered!",
                                                Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);

                                        startActivity(new Intent(getApplicationContext(), CustomerLoginActivity.class));
                                    } else {
                                        Toast.makeText(NewCustomerActivity.this, "Failed to register! Try again.",
                                                Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(NewCustomerActivity.this, "Failed to register! Try again.",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private boolean validateData(String email,
                                 String fullName,
                                 String age,
                                 String password,
                                 String reenterPassword) {
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