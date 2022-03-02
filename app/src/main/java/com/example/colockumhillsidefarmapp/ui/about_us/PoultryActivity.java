package com.example.colockumhillsidefarmapp.ui.about_us;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.colockumhillsidefarmapp.R;
import com.google.android.material.snackbar.Snackbar;

public class PoultryActivity extends AppCompatActivity {
    private Button backbutton;
    private Button turkey;
    private Button chicken;
    private Button practices;
    private Button eggs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poultry_activity);

        backbutton = findViewById(R.id.backbutton);
        turkey = findViewById(R.id.turkey);
        chicken = findViewById(R.id.chicken);
        eggs = findViewById(R.id.eggs);
        practices= findViewById(R.id.practices);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PoultryActivity.this, AboutUsFragment.class);
                startActivity(intent);
            }
        });
        turkey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar snack = Snackbar.make(findViewById(R.id.turkey),
                        "We raise specialty “Midget White” turkeys each year for Thanksgiving. Midget Whites love to spend their day foraging, so on our farm they enjoy a diet of free range shrub steppe findings supplemented with locally sourced, non-GMO, corn- and soy-free feed. This sought after breed takes about 7 months to grow to a compact 8-18 pounds, but rewards the chef with rich and juicy flavor. Watch our store for the opportunity to reserve your Thanksgiving bird!",
                        Snackbar.LENGTH_LONG);
                View snackView = snack.getView();
                TextView tv = (TextView) snackView.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setMaxLines(15);
                snack.show();
            }

        });
        chicken.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar snack = Snackbar.make(findViewById(R.id.chicken),
                        "What makes our Shrub Steppe eggs special? Our hens enjoy the freedom to forage on our 80 acres of pristine shrub steppe land. They spend their days scratching for shoots, leaves and insects. When you crack the eggs open, the benefits of their footloose and fancy-free lifestyle is obvious: the eggs’ yolks are a deep golden orange, and the whites are firm, not runny.",
                        Snackbar.LENGTH_LONG);
                View snackView = snack.getView();
                TextView tv = (TextView) snackView.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setMaxLines(15);
                snack.show();
            }

        });
        eggs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar snack = Snackbar.make(findViewById(R.id.eggs),
                        "Our Freedom Ranger chickens are raised on pasture and fed locally-sourced corn-, soy-, and GMO-free feed. This breed is said to have been preferred by Julia Child, and we agree with Julia! Freedom Rangers have a natural balance between white and dark meat and a deep, rich chicken flavor. The meat is juicy and firm–but not the least bit tough",
                        Snackbar.LENGTH_LONG);
                View snackView = snack.getView();
                TextView tv = (TextView) snackView.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setMaxLines(15);
                snack.show();
            }

        });
        practices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PoultryActivity.this, ChickenPracticesActivity.class);
                startActivity(intent);
            }
        });

    }

}