package com.example.colockumhillsidefarmapp.ui.about_us;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        setContentView(R.layout.vegetable_activity);

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
                Snackbar snack = Snackbar.make(findViewById(R.id.veggies),
                        "You can taste the difference! We use organic practices to grow beautiful, nutrient-rich produce. Compost built on our farm nourishes the soil, and we employ crop rotation, interplanting and water-wise practices to conserve water and nurture our plants and growing beds.    ",
                        Snackbar.LENGTH_INDEFINITE);
                View snackView = snack.getView();
                TextView tv = (TextView) snackView.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setMaxLines(15);
                snack.show();
            }

        });
        microgreens.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar snack = Snackbar.make(findViewById(R.id.microgreens),
                        "Want a delicious boost to your nutrition? We offer a wide variety of microgreens, which pack a powerful antioxidant punch! Microgreens are rich in potassium, iron, zinc, magnesium, copper and polyphenols. We grow our microgreens in an organic soil mix fortified with organic compost and liquid kelp for maximum nutrition. Our microgreens are fresh, colorful and tasty.  Enjoy them in sandwiches, salads, pestos and wraps, or use them as a garnish on any savory dish!",
                        Snackbar.LENGTH_INDEFINITE);
                View snackView = snack.getView();
                TextView tv = (TextView) snackView.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setMaxLines(15);
                snack.show();
            }

        });
        plantstarts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar snack = Snackbar.make(findViewById(R.id.plantstarts),
                        "We sell a wide variety of vegetable starts that we have tested and grown successfully in our own garden beds. Our seeds come from reputable sellers and we make our own organic potting soil. Customers compliment us on the beauty, vigor, and success of our plant starts. ",
                        Snackbar.LENGTH_INDEFINITE);
                View snackView = snack.getView();
                TextView tv = (TextView) snackView.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setMaxLines(15);
                snack.show();
            }

        });

    }

}
