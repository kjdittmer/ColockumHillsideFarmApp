package com.example.colockumhillsidefarmapp.customer.about_us;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.colockumhillsidefarmapp.R;

public class AboutUsActivity extends AppCompatActivity {

    private Info info;

    private static final String INFO = "info";
    private ImageView aboutUsImage;
    private TextView infoName, informationbody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();
        informationbody.setMovementMethod(new ScrollingMovementMethod());
        Intent intent = getIntent();
        if (getIntent() != null) {
            Info info = intent.getParcelableExtra(INFO);
            if (info != null) {
                setData(info);
            }
        }

    }

    private void setData(Info info) {
        infoName.setText(info.getName());
        informationbody.setText(info.getText());
        Glide.with(this).load(info.getImageUrl()).into(aboutUsImage);
    }

    private void initVariables() {
        aboutUsImage = findViewById(R.id.aboutUsImage);
        infoName = findViewById(R.id.infoName);
        informationbody = findViewById(R.id.informationbody);
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
