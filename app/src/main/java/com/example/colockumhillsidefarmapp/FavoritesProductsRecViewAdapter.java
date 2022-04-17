package com.example.colockumhillsidefarmapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FavoritesProductsRecViewAdapter extends RecyclerView.Adapter<FavoritesProductsRecViewAdapter.ViewHolder>{
    private static final String PRODUCT_ID = "productId";


    private ArrayList<Product> favoritesProducts;
    private Context mContext;

    public FavoritesProductsRecViewAdapter(Context mContext) {
        this.mContext = mContext;
        //favoritesProducts = Favorites.getInstance().getFavoritesProducts();
    }

    public void setProductsFavoritesProducts(ArrayList<Product> favoritesProducts) {
        this.favoritesProducts = favoritesProducts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoritesProductsRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_wishlist_product, parent, false);
        return new FavoritesProductsRecViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesProductsRecViewAdapter.ViewHolder holder, int position) {
        Product product = favoritesProducts.get(holder.getAdapterPosition());

        holder.txtProductNameWishlistItem.setText(product.getName());

        Glide.with(mContext)
                .asBitmap()
                .load(product.getImageUrl())
                .into(holder.imgProductWishlistItem);

        holder.txtQuantityWishlistItem.setText("Quantity: " + product.getQuantity());

        DecimalFormat df = new DecimalFormat("0.00");
        String price = df.format(product.getPrice());
        holder.txtPriceWishlistItem.setText("$" + price + "/" + product.getPackageQuantity());

        holder.parentWishlistItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductActivity.class);
                intent.putExtra(PRODUCT_ID, favoritesProducts.get(holder.getAdapterPosition()).getId());
                mContext.startActivity(intent);
            }
        });

        holder.imgDeleteWishlistItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to remove " + product.getName() + " from your favorite products?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Favorites.getInstance(mContext).removeProductFromFavoritesProducts(product);
                        //currentActivity.reload();
                        notifyDataSetChanged();
                        Toast.makeText(mContext, product.getName() + " removed", Toast.LENGTH_SHORT).show();
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

        ArrayList<Integer> quantity = new ArrayList<>();
        for(int i = 1; i <= product.getQuantity(); i++){
            quantity.add(i);
        }
        ArrayAdapter<Integer> quantityAdapter = new ArrayAdapter<Integer>(holder.txtProductNameWishlistItem.getContext(), android.R.layout.simple_spinner_dropdown_item,
                quantity);
        holder.spnrQuantityWishlistItem.setAdapter(quantityAdapter);

        holder.btnAddToCartWishlistItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantitySelected = (int)holder.spnrQuantityWishlistItem.getSelectedItem();

                ShoppingCart.getInstance().addProductToCart(product, quantitySelected);

                String plurality = "";
                if (quantitySelected > 1) {
                    plurality = "s";
                }
                Toast.makeText(view.getContext(), quantitySelected + " " + product.getName() + plurality +
                        " added to cart!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(mContext, ShoppingCartActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoritesProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parentWishlistItem;
        private ImageView imgProductWishlistItem;
        private ImageButton imgDeleteWishlistItem, btnAddToCartWishlistItem;
        private TextView txtProductNameWishlistItem, txtQuantityWishlistItem, txtPriceWishlistItem;
        private Spinner spnrQuantityWishlistItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            spnrQuantityWishlistItem = itemView.findViewById(R.id.spnrQuantityWishlistItem);
            btnAddToCartWishlistItem = itemView.findViewById(R.id.btnAddToCartWishlistItem);
            parentWishlistItem = itemView.findViewById(R.id.parentWishlistItem);
            imgProductWishlistItem = itemView.findViewById(R.id.imgProductWishlistItem);
            imgDeleteWishlistItem = itemView.findViewById(R.id.imgDeleteWishlistItem);
            txtProductNameWishlistItem = itemView.findViewById(R.id.txtProductNameWishlistItem);
            txtQuantityWishlistItem = itemView.findViewById(R.id.txtQuantityWishlistItem);
            txtPriceWishlistItem = itemView.findViewById(R.id.txtPriceWishlistItem);
        }
    }
}
