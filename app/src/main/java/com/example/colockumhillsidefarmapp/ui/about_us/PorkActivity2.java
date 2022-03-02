package com.example.colockumhillsidefarmapp.ui.about_us;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colockumhillsidefarmapp.MainActivity;
import com.example.colockumhillsidefarmapp.R;

public class PorkActivity2 extends AppCompatActivity {
    private Button backbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pork_activity);

        backbutton = findViewById(R.id.backbutton2);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PorkActivity2.this, AboutUsFragment.class);
                startActivity(intent);
            }
        });
    }
    private void openMenuAct () {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

