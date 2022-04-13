package com.example.colockumhillsidefarmapp.ui.recipes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RecipeActivity.class);
                intent.putExtra(RECIPE, recipe);
                mContext.startActivity(intent);
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private ImageView imgRecipe;
        private TextView txtName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgRecipe = itemView.findViewById(R.id.imgRecipe);
            txtName = itemView.findViewById(R.id.txtRecipeName);

        }
    }
}
