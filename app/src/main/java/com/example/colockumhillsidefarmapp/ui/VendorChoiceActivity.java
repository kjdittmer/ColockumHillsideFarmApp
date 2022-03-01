package com.example.colockumhillsidefarmapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.ui.about_us.AboutUsFragment;
import com.example.colockumhillsidefarmapp.ui.about_us.AboutUsViewModel;

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
                        break;
                    case R.id.rbUpdateStore:
                        Toast.makeText(VendorChoiceActivity.this, "Go to update store", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rbUpdateAboutUs:
                        Toast.makeText(VendorChoiceActivity.this, "Go to update about us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rbMenuButton:
                        Toast.makeText(VendorChoiceActivity.this, "Go back to menu", Toast.LENGTH_SHORT).show();
                        openMenuAct();
                        break;
                    default:
                        break;
                }
            }
        });

    }

    private void openMenuAct() {
        Intent intent = new Intent(this, AboutUsViewModel.class);
        startActivity(intent);
    }
}