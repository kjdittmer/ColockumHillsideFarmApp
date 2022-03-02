package com.example.colockumhillsidefarmapp.ui.about_us;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.ui.vendor.VendorChoiceActivity;
import com.google.android.material.snackbar.Snackbar;

public class PoultryActivity2 extends AppCompatActivity {
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
                Intent intent = new Intent(PoultryActivity2.this, AboutUsFragment.class);
                startActivity(intent);
            }
        });
        turkey.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar snack = Snackbar.make(findViewById(R.id.turkey),
                        "We raise specialty “Midget White” turkeys each year for Thanksgiving. Midget Whites love to spend their day foraging, so on our farm they enjoy a diet of free range shrub steppe findings supplemented with locally sourced, non-GMO, corn- and soy-free feed. This sought after breed takes about 7 months to grow to a compact 8-18 pounds, but rewards the chef with rich and juicy flavor. Watch our store for the opportunity to reserve your Thanksgiving bird!",
                        Snackbar.LENGTH_INDEFINITE);
                View snackView = snack.getView();
                TextView tv = (TextView) snackView.findViewById(com.google.android.material.R.id.snackbar_text);
                tv.setMaxLines(15);
                snack.show();
            }

        });

       // btnGoUpdateStore.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //   public void onClick(View view) {
        //      switch (rgUpdateStoreChioce.getCheckedRadioButtonId()) {
        //          case R.id.rbAddItem:
        //                Toast.makeText(PoultryActivity2.this, "Adding item...", Toast.LENGTH_SHORT).show();
        //                break;
        //            case R.id.rbEditItem:
        //                Toast.makeText(PoultryActivity2.this, "Editing item...", Toast.LENGTH_SHORT).show();
        //                break;
        //            case R.id.rbRemoveItem:
        //                Toast.makeText(PoultryActivity2.this, "Removing item...", Toast.LENGTH_SHORT).show();
        //            case R.id.rbBackFromUpdateStore:
        //                Intent intent = new Intent(PoultryActivity2.this, VendorChoiceActivity.class);
        //                startActivity(intent);
        //            default:
        //                break;
        //       }
        //    }
        //});

    }

}