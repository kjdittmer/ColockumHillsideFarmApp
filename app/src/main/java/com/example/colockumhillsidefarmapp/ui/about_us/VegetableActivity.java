package com.example.colockumhillsidefarmapp.ui.about_us;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.colockumhillsidefarmapp.MainActivity;
import com.example.colockumhillsidefarmapp.R;
import com.google.android.material.snackbar.Snackbar;

public class VegetableActivity extends AppCompatActivity {
    private Button veggies;
    private Button microgreens;
    private Button plantstarts;
    private Button practices;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetable);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        back = findViewById(R.id.back3);
        veggies = findViewById(R.id.veggies);
        microgreens = findViewById(R.id.microgreens);
        plantstarts = findViewById(R.id.plantstarts);
        practices= findViewById(R.id.veggiepractices);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VegetableActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        veggies.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("You can taste the difference! We use organic practices to grow beautiful, nutrient-rich produce. Compost built on our farm nourishes the soil, and we employ crop rotation, interplanting and water-wise practices to conserve water and nurture our plants and growing beds.");
                builder.create().show();
            }

        });
        practices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VegetableActivity.this, VegtablePracticesActivity.class);
                startActivity(intent);
            }
        });
        microgreens.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Want a delicious boost to your nutrition? We offer a wide variety of microgreens, which pack a powerful antioxidant punch! Microgreens are rich in potassium, iron, zinc, magnesium, copper and polyphenols. We grow our microgreens in an organic soil mix fortified with organic compost and liquid kelp for maximum nutrition. Our microgreens are fresh, colorful and tasty.  Enjoy them in sandwiches, salads, pestos and wraps, or use them as a garnish on any savory dish!");
                builder.create().show();
            }

        });
        plantstarts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("We sell a wide variety of vegetable starts that we have tested and grown successfully in our own garden beds. Our seeds come from reputable sellers and we make our own organic potting soil. Customers compliment us on the beauty, vigor, and success of our plant starts. ");
               builder.create().show();
            }

        });

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
