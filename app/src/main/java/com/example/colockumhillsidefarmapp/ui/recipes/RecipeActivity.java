package com.example.colockumhillsidefarmapp.ui.recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.colockumhillsidefarmapp.R;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {
    private ImageView recipeImage;
    private TextView recipeName, shortDesc, longDesc;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        initViews();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        TODO: Get the data from recycler view in here
        Recipe chicken_soup = new Recipe(1, "Chicken Soup", "It's a good tasting soup!", "Step 1: Pour it in " +
                "a bowl.\nStep 2: Heat it.\nStep 3: EAT IT.", "https://www.inspiredtaste.net/wp-content/uploads/2018/09/Easy-Chicken-Noodle-Soup-Recipe-1200.jpg");
        Recipe riceChicken = new Recipe(2, "Chicken and Rice", "short", "long", "https://www.eatwell101.com/wp-content/uploads/2018/04/chicken-and-rice-recipe.jpg");
        Recipe eggs = new Recipe(3, "Scrambled Eggs", "short", "long", "https://images.media-allrecipes.com/userphotos/9175615.jpg");
        Recipe meatloaf = new Recipe(4, "Meatloaf", "short", "long", "https://imagesvc.meredithcorp.io/v3/mm/image?q=60&c=sc&poi=face&w=2000&h=1000&url=https%3A%2F%2Fstatic.onecms.io%2Fwp-content%2Fuploads%2Fsites%2F43%2F2022%2F02%2F18%2F16354-easy-meatloaf-mfs-76.jpg");
        Recipe roastBeef = new Recipe(5, "Roast Beef", "short", "long", "https://hips.hearstapps.com/hmg-prod/images/delish-roast-beef-horizontal-1540505165.jpg");

        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        recipes.add(chicken_soup);
        recipes.add(riceChicken);
        recipes.add(eggs);
        recipes.add(meatloaf);
        recipes.add(roastBeef);
        setData(chicken_soup);
    }
    private void setData(Recipe recipe){
        recipeName.setText(recipe.getName());
        shortDesc.setText(recipe.getShortDesc());
        longDesc.setText(recipe.getLongDesc());
        Glide.with(this).asBitmap().load(recipe.getImageUrl()).into(recipeImage);
    }
    private void initViews() {
        recipeImage = findViewById(R.id.recipeImage);

        recipeName = findViewById(R.id.recipeName);
        shortDesc = findViewById(R.id.shortDesc);
        longDesc = findViewById(R.id.longDesc);
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
