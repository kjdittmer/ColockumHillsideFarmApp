package com.example.colockumhillsidefarmapp.ui.recipes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.colockumhillsidefarmapp.R;

public class RecipeActivity extends AppCompatActivity {
    private ImageView recipeImage;
    private TextView recipeName, shortDesc, longDesc;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        initViews();

//        TODO: Get the data from recycler view in here
        Recipe recipe = new Recipe(1, "Chicken Soup", "short", "long", "https://www.inspiredtaste.net/wp-content/uploads/2018/09/Easy-Chicken-Noodle-Soup-Recipe-1200.jpg");

        setData(recipe);
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
}
