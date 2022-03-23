package com.example.colockumhillsidefarmapp.ui.about_us;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.colockumhillsidefarmapp.R;
import com.google.android.material.snackbar.Snackbar;

public class ChickenPracticesActivity extends AppCompatActivity {
    private Button meatChickens;
    private Button layerHens;
    private Button turkey2;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chicken_practices);

        back = findViewById(R.id.back4);
        meatChickens = findViewById(R.id.meatChicken);
        layerHens = findViewById(R.id.layerHens);
        turkey2 = findViewById(R.id.turkey2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChickenPracticesActivity.this, PoultryActivity.class);
                startActivity(intent);
            }
        });
        meatChickens.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar snack = Snackbar.make(findViewById(R.id.meatChicken),
                       "We raise Freedom Ranger chickens because they are excellent foragers. We time their arrival with the seasons so that they can spend the maximum amount of time outside foraging. At the age of three weeks, we put the chickens out on pasture in chicken tractors. We move the tractors daily so that the chicks can enjoy fresh forage without overgrazing. The tractors are surrounded by an Electronet fence, which keeps the chicks safe from predators.  When the chicks are big enough not to slip through the mesh of the Electronet fence, we open the door to the tractor during the day so the chickens can roam freely inside the fence. We still move the tractors daily, however, to avoid damage to any one area of our pasture while the chickens are closed in at night.\n" +
                               "\n" +
                               "Freedom Rangers take about three weeks longer than commercial breeds to mature—10 to 11 weeks in all, but the flavor of the resulting slow-grown meat is well worth the time and effort. Freedom Rangers have an even balance of light and dark meat, and while firm, it is tender, juicy and delicious.",
                        Snackbar.LENGTH_LONG);
                View snackView = snack.getView();
                TextView tv = (TextView) snackView.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setMaxLines(25);
                snack.show();
            }

        });
        layerHens.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar snack = Snackbar.make(findViewById(R.id.layerHens),
                       "Our layer chickens are cheerful chickens because we treat them with kindness and respect. We believe that when our hens are happy and unstressed, they produce plentiful and tastier eggs.\n" +
                               "\n" +
                               "We raise our layer chickens out in the untouched shrub steppe. From March through November, our chickens are housed in a portable coop (we call it our Eggmobile) that we move frequently so the chickens always have access to fresh forage, and our land does not become overgrazed. The hens spend from dusk to dawn scratching and pecking for shoots, leaves, and insects in the pristine shrub steppe.  Our Livestock Guardian Dogs, Owen and Rosy, keep a careful eye out for hawks and coyotes so that the chickens can have total freedom to live happy chicken lives. \n" +
                               "\n" +
                               "In winter, when the frozen ground makes grazing difficult, we do the next best thing we can for our hens: we house them in our dormant vegetable garden. They enjoy spending their day on deep litter in a clear plastic hoop house that maximizes light and absorbs heat during the day. The deep litter composts over time, and the composting action warms the air in the hoop house from the ground up. We add fresh straw weekly to keep things smelling fresh and pleasant. And, scratching through the composting straw provides entertainment for the chickens.",
                        Snackbar.LENGTH_LONG);
                View snackView = snack.getView();
                TextView tv = (TextView) snackView.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setMaxLines(25);
                snack.show();
            }

        });
        turkey2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar snack = Snackbar.make(findViewById(R.id.turkey2),
                       "We love our Midget White Turkeys! This is the only poultry that we have successfully bred on our farm. We keep a breeding flock of Midget Whites year round, and they are housed with the laying flock of chickens in our dormant garden over winter.\n" +
                               "\n" +
                               "Midget Whites are known for their excellent foraging skills and they cover an astonishing amount of ground daily. We are grateful for our watchful guardian dogs who keep a careful eye on the turkeys.  Midget Whites are definitely flock animals, and they make all decisions by committee with much (noisy) discussion! They are curious, friendly, and like to follow the farm humans around like dogs. They think that they are in heaven in the shrub steppe!\n" +
                               "\n" +
                               "Midget Whites are smaller than commercial turkeys.  The hens grow out to 8-12 lbs, and the toms weigh between 14 and 18 lbs.  Their meat is more equally balanced between dark and light meat than the heavy, huge breasted turkeys that are grown commercially. The resulting meat is incredibly flavorful. I have yet to NOT have a Thanksgiving dinner guest declare, “That is the best turkey I have ever eaten!” after enjoying one of our homegrown turkeys.",
                        Snackbar.LENGTH_LONG);
                View snackView = snack.getView();
                TextView tv = (TextView) snackView.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setMaxLines(25);
                snack.show();
            }

        });

    }

}
