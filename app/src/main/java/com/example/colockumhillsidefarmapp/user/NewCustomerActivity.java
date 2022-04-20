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
import com.example.colockumhillsidefarmapp.customer.CustomerDashboardActivity;

public class NewCustomerActivity extends AppCompatActivity {

    private EditText txtEmail, txtPassword, txtReenterPassword;
    private Button btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CustomerDashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initVariables () {
        txtEmail = findViewById(R.id.txtEmailNewCustomer);
        txtPassword = findViewById(R.id.txtPasswordNewCustomer);
        txtReenterPassword = findViewById(R.id.txtReenterPasswordNewCustomer);
        btnCreateAccount = findViewById(R.id.btnCreateAccountNewCustomer);
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