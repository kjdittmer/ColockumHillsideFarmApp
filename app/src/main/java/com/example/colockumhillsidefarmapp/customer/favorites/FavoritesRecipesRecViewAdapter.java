package com.example.colockumhillsidefarmapp.customer.favorites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.recipes.Recipe;
import com.example.colockumhillsidefarmapp.customer.recipes.RecipeActivity;

import java.util.ArrayList;

public class FavoritesRecipesRecViewAdapter extends RecyclerView.Adapter<FavoritesRecipesRecViewAdapter.ViewHolder>{
    private static final String RECIPE = "recipe";

    private FavoritesActivity currentActivity;
    private ArrayList<Recipe> favoritesRecipes;
    private Context mContext;

    public FavoritesRecipesRecViewAdapter(Context mContext, FavoritesActivity currentActivity) {
        this.mContext = mContext;
        this.currentActivity = currentActivity;
        //favoritesRecipes = Favorites.getInstance().getFavoritesRecipes();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_favorites_recipe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Recipe recipe = favoritesRecipes.get(holder.getAdapterPosition());
        holder.txtName.setText(recipe.getName());
        Glide.with(mContext)
                .asBitmap()
                .load(recipe.getImageUrl())
                .into(holder.imgRecipe);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RecipeActivity.class);
                intent.putExtra(RECIPE, recipe);
                mContext.startActivity(intent);
            }
        });

        holder.btnDeleteFavoritesRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to remove " + recipe.getName() + " from your favorite recipes?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Favorites.getInstance(mContext).removeRecipeFromFavoritesRecipes(recipe);
                        currentActivity.reload();
                        Toast.makeText(mContext, recipe.getName() + " removed", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoritesRecipes.size();
    }

    public void setRecipesFavoritesRecipes(ArrayList<Recipe> recipes) {
        this.favoritesRecipes = recipes;
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<Recipe> filteredRecipes) {
        favoritesRecipes = filteredRecipes;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private ImageView imgRecipe;
        private TextView txtName;
        private ImageButton btnDeleteFavoritesRecipe;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parentFavoritesRecipe);
            imgRecipe = itemView.findViewById(R.id.imgRecipeFavoritesRecipe);
            txtName = itemView.findViewById(R.id.txtRecipeNameFavoritesRecipe);
            btnDeleteFavoritesRecipe = itemView.findViewById(R.id.btnDeleteFavoritesRecipe);
        }
    }
}
