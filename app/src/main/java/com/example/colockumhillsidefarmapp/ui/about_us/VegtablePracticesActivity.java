package com.example.colockumhillsidefarmapp.ui.about_us;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colockumhillsidefarmapp.R;
import com.google.android.material.snackbar.Snackbar;

public class VegtablePracticesActivity extends AppCompatActivity {
    private Button backbutton;
    private Button water;
    private Button pest;
    private Button fertilizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poultry_activity);

        backbutton = findViewById(R.id.backbutton);
        water = findViewById(R.id.button_water);
        pest = findViewById(R.id.button_pest);
        fertilizer = findViewById(R.id.button_fert);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VegtablePracticesActivity.this, AboutUsFragment.class);
                startActivity(intent);
            }
        });
        water.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar snack = Snackbar.make(findViewById(R.id.button_water),
                        "Water is scarce up in the shrub steppe, and we use several water-wise techniques in our growing beds. In oddly shaped beds, we use a buried clay pot system.  The clay pots act as water reservoirs, and the plants’ roots draw water through the porous sides of the pots as they need it.  In rectangular beds, we have buried perforated pipes.  Hoses on timers deliver water to these pipes that allow water to seep into the plants’ root zones at regular intervals. ",
                        Snackbar.LENGTH_LONG);
                View snackView = snack.getView();
                TextView tv = (TextView) snackView.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setMaxLines(15);
                snack.show();
            }

        });
        pest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar snack = Snackbar.make(findViewById(R.id.button_pest),
                        "Instead of using pesticides, we interplant flowers with our veggies to attract beneficial insects. These garden friendly insects help control the pests. We also practice crop rotation, so that pesky insects don’t know where to find their favorite plants from season to season.  Finally, when plants do become overrun with pests, we leave a few sacrificial plants out for the pests—this attracts the beneficial insects that prey on them and the cycle continues.",
                        Snackbar.LENGTH_LONG);
                View snackView = snack.getView();
                TextView tv = (TextView) snackView.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setMaxLines(15);
                snack.show();
            }

        });
        fertilizer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar snack = Snackbar.make(findViewById(R.id.button_fert),
                        "Instead of using chemical fertilizers, we build our own compost from used animal bedding and veggie debris. We water and turn our compost frequently until it becomes rich, brown and crumbly. We add this to our beds in the fall and spring and are rewarded by beautiful, friable soil. We avoid tilling the soil in our garden beds—preferring instead to either allow a thick layer of compost to break down naturally on top of the soil during dormant seasons, or to carefully fork compost into the beds by hand with a broadfork at the beginning of growing seasons. We believe that this is gentler on the microbes in the soil than tilling.",
                        Snackbar.LENGTH_LONG);
                View snackView = snack.getView();
                TextView tv = (TextView) snackView.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setMaxLines(15);
                snack.show();
            }

        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VegtablePracticesActivity.this, VegetableActivity.class);
                startActivity(intent);
            }
        });

    }

}