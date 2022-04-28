package com.example.colockumhillsidefarmapp.customer.recipes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.R;

import java.util.ArrayList;

public class RecipeRecViewAdapter extends RecyclerView.Adapter<RecipeRecViewAdapter.ViewHolder>{
    private static final String TAG = "RecipeRecViewAdapter";
    private static final String RECIPE = "recipe";

    private ArrayList<Recipe> recipes = new ArrayList<>();
    private Context mContext;

    public RecipeRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Recipe recipe = recipes.get(holder.getAdapterPosition());
        holder.txtName.setText(recipe.getName());
        Glide.with(mContext)
                .asBitmap()
                .load(recipe.getImageUrl())
                .into(holder.imgRecipe);

//        ArrayList<String> ingredientsString = new ArrayList<>(Arrays.asList(recipe.getIngredients().split(",")));
//        ArrayAdapter ingredientsAdapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, ingredientsString);
//        holder.ingredientsListView.setAdapter(ingredientsAdapter);

        //make this better
        //holder.txtIngredientsList.setText(new ArrayList<>(Arrays.asList(recipe.getIngredients().split(","))).toString());
        holder.txtShortDesc.setText(recipe.getShortDesc());
        /**holder.txtIngredientsList.setText("");
        String[] ingredients = (recipe.getIngredients().split(","));
        for (int i = 0; i < ingredients.length; i++) {
            holder.txtIngredientsList.append("\u2022 ");
            holder.txtIngredientsList.append(ingredients[i].trim());
            holder.txtIngredientsList.append("\n");
        }
**/

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RecipeActivity.class);
                intent.putExtra(RECIPE, recipe);
                mContext.startActivity(intent);
            }
        });

        holder.btnAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBInterface.getInstance().addRecipeToFavoriteRecipes(recipe);

                Toast.makeText(view.getContext(), recipe.getName() +
                        " added to favorites!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<Recipe> filteredRecipes) {
        recipes = filteredRecipes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private ImageView imgRecipe;
        private TextView txtName;
        private ImageButton btnAddToFavorites;
        private TextView txtShortDesc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgRecipe = itemView.findViewById(R.id.imgRecipe);
            txtName = itemView.findViewById(R.id.txtRecipeName);
            btnAddToFavorites = itemView.findViewById(R.id.btnAddToFavoritesRecipeFrag);
            txtShortDesc = itemView.findViewById(R.id.txtIngredientsList);
        }
    }
}
