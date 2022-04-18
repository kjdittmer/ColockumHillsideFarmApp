package com.example.colockumhillsidefarmapp.vendor.update_about_us;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.R;

public class UpdateAboutUsActivity extends AppCompatActivity {

    private Button btnUpdateInfoAboutUs, btnUpdatePicturesAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.colockumhillsidefarmapp.R.layout.activity_update_about_us);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    }

    private void initVariables() {
        btnUpdateInfoAboutUs = findViewById(R.id.btnUpdateInfoAboutUs);
        btnUpdatePicturesAboutUs = findViewById(R.id.btnUpdatePicturesAboutUs);
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