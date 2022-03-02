package com.example.colockumhillsidefarmapp.ui.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.R;

public class UpdateAboutUsActivity extends AppCompatActivity {

    private Button btnUpdateInfoAboutUs, btnUpdatePicturesAboutUs, btnBackFromUpdateUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.colockumhillsidefarmapp.R.layout.activity_update_about_us);

        initVariables();

        btnUpdateInfoAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UpdateAboutUsActivity.this, "Updating info...", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdatePicturesAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UpdateAboutUsActivity.this, "Updating pictures...", Toast.LENGTH_SHORT).show();
            }
        });

        btnBackFromUpdateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateAboutUsActivity.this, VendorChoiceActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initVariables() {
        btnUpdateInfoAboutUs = findViewById(R.id.btnUpdateInfoAboutUs);
        btnUpdatePicturesAboutUs = findViewById(R.id.btnUpdatePicturesAboutUs);
        btnBackFromUpdateUs = findViewById(R.id.btnBackFromUpdateAboutUs);
    }
}