package com.example.colockumhillsidefarmapp.ui.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.MainActivity;
import com.example.colockumhillsidefarmapp.R;

public class VendorChoiceActivity extends AppCompatActivity {

    private TextView txtPrompt;
    private RadioGroup rgVendorChoice;
    Button btnGo;
    //private RadioButton rbAnalytics, rbUpdateStore, rbUpdateAboutUs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_choice);

        txtPrompt = findViewById(R.id.txtPrompt);
        rgVendorChoice = findViewById(R.id.rgVendorChoice);
        btnGo = findViewById(R.id.btnGo);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioButtonId = rgVendorChoice.getCheckedRadioButtonId();
                switch (radioButtonId) {
                    case R.id.rbAnalytics:
                        Toast.makeText(VendorChoiceActivity.this, "Go to analytics", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(VendorChoiceActivity.this, AnalyticsActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.rbUpdateStore:
                        Toast.makeText(VendorChoiceActivity.this, "Go to update store", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(VendorChoiceActivity.this, UpdateStoreActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.rbUpdateAboutUs:
                        Toast.makeText(VendorChoiceActivity.this, "Go to update about us", Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(VendorChoiceActivity.this, UpdateAboutUsActivity.class);
                        startActivity(intent3);
                        break;
                    case R.id.rbMenuButton:
                        Toast.makeText(VendorChoiceActivity.this, "Exit vendor mode", Toast.LENGTH_SHORT).show();
                        openMenuAct();
                        break;
                    default:
                        break;
                }
            }
        });

    }

    private void openMenuAct() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}