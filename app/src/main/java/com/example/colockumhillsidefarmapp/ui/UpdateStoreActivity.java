package com.example.colockumhillsidefarmapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.MainActivity;
import com.example.colockumhillsidefarmapp.R;

public class UpdateStoreActivity extends AppCompatActivity {

    private RadioGroup rgUpdateStoreChioce;
    private Button btnGoUpdateStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.colockumhillsidefarmapp.R.layout.activity_update_store);

        rgUpdateStoreChioce = findViewById(R.id.rgUpddateStoreChioce);
        btnGoUpdateStore = findViewById(R.id.btnGoUpdateStore);

        btnGoUpdateStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (rgUpdateStoreChioce.getCheckedRadioButtonId()) {
                    case R.id.rbAddItem:
                        Toast.makeText(UpdateStoreActivity.this, "Adding item...", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rbEditItem:
                        Toast.makeText(UpdateStoreActivity.this, "Editing item...", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rbRemoveItem:
                        Toast.makeText(UpdateStoreActivity.this, "Removing item...", Toast.LENGTH_SHORT).show();
                    case R.id.rbBackFromUpdateStore:
                        Intent intent = new Intent(UpdateStoreActivity.this, VendorChoiceActivity.class);
                        startActivity(intent);
                    default:
                        break;
                }
            }
        });

    }

}