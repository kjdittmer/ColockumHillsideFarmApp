package com.example.colockumhillsidefarmapp.vendor.update_recipes;

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
import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.recipes.Recipe;
import com.example.colockumhillsidefarmapp.vendor.VendorDashboardActivity;

import java.util.ArrayList;


public class UpdateRecipeRecViewAdapter extends RecyclerView.Adapter<UpdateRecipeRecViewAdapter.ViewHolder> {

    private static final String RECIPE = "recipe";

    private ArrayList<Recipe> recipes = new ArrayList<>();
    private Context mContext;
    private RecyclerView.Adapter thisAdapter;
    private VendorDashboardActivity currentActivity;


    public UpdateRecipeRecViewAdapter(Context mContext, VendorDashboardActivity currentActivity) {
        this.mContext = mContext;
        thisAdapter = this;
        this.currentActivity = currentActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_edit_recipe, parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = recipes.get(holder.getAdapterPosition());
        holder.txtRecipeNameEditRecipe.setText(recipe.getName());
        String[] ingredients = (recipe.getIngredients().split(","));
        holder.txtIngredientsEditRecipe.setText("");
        for (int i = 0; i < ingredients.length; i++) {
            holder.txtIngredientsEditRecipe.append("\u2022 ");
            holder.txtIngredientsEditRecipe.append(ingredients[i].trim());
            holder.txtIngredientsEditRecipe.append("\n");
        }
        Glide.with(mContext)
                .asBitmap()
                .load(recipe.getImageUrl())
                .into(holder.imgRecipeEditRecipe);

        holder.parentEditRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditRecipeActivity.class);
                intent.putExtra(RECIPE, recipe);
                mContext.startActivity(intent);
            }
        });

        holder.btnDeleteEditRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to remove " + recipe.getName() + " from the recipes?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBInterface.getInstance().removeRecipe(recipe);
                        currentActivity.reloadRecipes();
                        Toast.makeText(mContext, recipe.getName() + " removed.", Toast.LENGTH_SHORT).show();
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView parentEditRecipe;
        private ImageView imgRecipeEditRecipe;
        private TextView txtRecipeNameEditRecipe, txtIngredientsEditRecipe;
        private ImageButton btnDeleteEditRecipe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentEditRecipe = itemView.findViewById(R.id.parentEditRecipe);
            imgRecipeEditRecipe = itemView.findViewById(R.id.imgRecipeEditRecipe);
            txtRecipeNameEditRecipe = itemView.findViewById(R.id.txtRecipeNameEditRecipe);
            btnDeleteEditRecipe = itemView.findViewById(R.id.btnDeleteEditRecipe);
            txtIngredientsEditRecipe = itemView.findViewById(R.id.txtIngredientsEditRecipe);
        }
    }
}
