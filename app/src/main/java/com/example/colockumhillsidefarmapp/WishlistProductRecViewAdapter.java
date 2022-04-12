package com.example.colockumhillsidefarmapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class WishlistProductRecViewAdapter extends RecyclerView.Adapter<WishlistProductRecViewAdapter.ViewHolder>{

    private static final String PRODUCT_ID = "productId";


    private ArrayList<Product> wishlist;
    private Context mContext;
    private WishlistActivity currentActivity;

    public WishlistProductRecViewAdapter(Context mContext, WishlistActivity currentActivity) {
        this.mContext = mContext;
        wishlist = Wishlist.getInstance().getWishlist();
        this.currentActivity = currentActivity;
    }

    public void setProductsInWishlist(ArrayList<Product> wishlist) {
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public WishlistProductRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_wishlist_product, parent, false);
        return new WishlistProductRecViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistProductRecViewAdapter.ViewHolder holder, int position) {
        Product product = wishlist.get(holder.getAdapterPosition());

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
                intent.putExtra(PRODUCT_ID, wishlist.get(holder.getAdapterPosition()).getId());
                mContext.startActivity(intent);
            }
        });

        holder.imgDeleteWishlistItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to remove " + product.getName() + " from your wishlist?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Wishlist.getInstance().removeProductFromWishList(product);
                        currentActivity.reload();
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
    }

    @Override
    public int getItemCount() {
        return wishlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parentWishlistItem;
        private ImageView imgProductWishlistItem;
        private ImageButton imgDeleteWishlistItem;
        private TextView txtProductNameWishlistItem, txtQuantityWishlistItem, txtPriceWishlistItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentWishlistItem = itemView.findViewById(R.id.parentWishlistItem);
            imgProductWishlistItem = itemView.findViewById(R.id.imgProductWishlistItem);
            imgDeleteWishlistItem = itemView.findViewById(R.id.imgDeleteWishlistItem);
            txtProductNameWishlistItem = itemView.findViewById(R.id.txtProductNameWishlistItem);
            txtQuantityWishlistItem = itemView.findViewById(R.id.txtQuantityWishlistItem);
            txtPriceWishlistItem = itemView.findViewById(R.id.txtPriceWishlistItem);
        }
    }
}
