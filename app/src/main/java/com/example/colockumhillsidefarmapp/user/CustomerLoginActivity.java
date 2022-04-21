package com.example.colockumhillsidefarmapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.GlobalStorage;
import com.example.colockumhillsidefarmapp.MainActivity;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.CustomerDashboardActivity;
import com.example.colockumhillsidefarmapp.vendor.update_store.AddProductActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CustomerLoginActivity extends AppCompatActivity {

    private EditText txtEmail, txtPassword;
    private Button btnLogin;
    private TextView txtCreateAccount, txtVendorLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        initVariables();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();
                GlobalStorage.getInstance().signInCustomer(email, password, view.getContext());

                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);

//                FirebaseAuth mAuth = FirebaseAuth.getInstance();
//
//                mAuth.signInWithEmailAndPassword(username, password)
//                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    User.getInstance().setCustomer(user.getEmail());
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Toast.makeText(CustomerLoginActivity.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
//                                }
//                            }
//                        });
            }
        });

        txtCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewCustomerActivity.class);
                startActivity(intent);
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
    }

    private boolean validateLogin() {
        return true;
    }

    @Override
    public void onBackPressed() {
    }
}