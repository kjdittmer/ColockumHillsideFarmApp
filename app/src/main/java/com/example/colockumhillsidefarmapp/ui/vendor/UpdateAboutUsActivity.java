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

    private TextView txtPromptUpdateAboutUs;
    private RadioGroup rgUpdateAboutUsChoice;
    private Button btnGoUpdateAboutUs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.colockumhillsidefarmapp.R.layout.activity_update_about_us);

        txtPromptUpdateAboutUs = findViewById(R.id.txtPromptUpdateAboutUs);
        rgUpdateAboutUsChoice = findViewById(R.id.rgUpdateAboutUsChoice);
        btnGoUpdateAboutUs = findViewById(R.id.btnGoUpdateAboutUs);

        btnGoUpdateAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rgUpdateAboutUsChoice.getCheckedRadioButtonId()) {
                    case R.id.rbUpdateInfoAboutUs:
                        Toast.makeText(UpdateAboutUsActivity.this, "Updating info...", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rbUpdatePicturesAboutUs:
                        Toast.makeText(UpdateAboutUsActivity.this, "Updating pictures...", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rbBackFromUpdateAboutUs:
                        Intent intent = new Intent(UpdateAboutUsActivity.this, VendorChoiceActivity.class);
                        startActivity(intent);
                    default:
                        break;
                }
            }
        });


    }
}