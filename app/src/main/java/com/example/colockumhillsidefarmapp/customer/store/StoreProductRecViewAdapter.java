package com.example.colockumhillsidefarmapp.customer.store;

import android.content.Context;
import android.content.Intent;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.wishlist.Wishlist;
import com.example.colockumhillsidefarmapp.customer.favorites.Favorites;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.ShoppingCart;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.ShoppingCartActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class StoreProductRecViewAdapter extends RecyclerView.Adapter<StoreProductRecViewAdapter.ViewHolder> {
    private static final String PRODUCT = "product";
    private static final String TAG = "ProductRecViewAdapter";

    private ArrayList<Product> products = new ArrayList<>();
    private Context mContext;

    public StoreProductRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_store_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(holder.getAdapterPosition());
        holder.txtProductName.setText(product.getName());
        String price;
        DecimalFormat df = new DecimalFormat("0.00");
        price = df.format(product.getPrice());
        holder.txtPrice.setText("$" + price + "/" + product.getPackageQuantity());
        Glide.with(mContext)
                .asBitmap()
                .load(product.getImageUrl())
                .into(holder.imgProduct);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductActivity.class);
                intent.putExtra(PRODUCT, product);
                mContext.startActivity(intent);
            }
        });

        ArrayList<Integer> quantity = new ArrayList<>();
        for(int i = 1; i <= product.getQuantity(); i++){
            quantity.add(i);
        }
        ArrayAdapter<Integer> quantityAdapter = new ArrayAdapter<Integer>(holder.txtProductName.getContext(), android.R.layout.simple_spinner_dropdown_item,
                quantity);
        holder.spnrQuantityStoreFrag.setAdapter(quantityAdapter);

        holder.btnAddToCartStoreFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantitySelected = (int)holder.spnrQuantityStoreFrag.getSelectedItem();

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

        holder.btnAddToFavoritesStoreFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Favorites.getInstance(mContext).addProductToFavoritesProducts(product);

                Toast.makeText(view.getContext(), product.getName() +
                        " added to favorites!", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnAddToWishlistStoreFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Wishlist.getInstance(mContext).addProductToWishlist(product);

                Toast.makeText(view.getContext(), product.getName() +
                        " added to wishlist!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private ImageView imgProduct;
        private TextView txtProductName;
        private TextView txtPrice;
        private ImageButton btnAddToFavoritesStoreFrag, btnAddToWishlistStoreFrag, btnAddToCartStoreFrag;
        private Spinner spnrQuantityStoreFrag;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            btnAddToFavoritesStoreFrag = itemView.findViewById(R.id.btnAddToFavoritesStoreFrag);
            btnAddToWishlistStoreFrag = itemView.findViewById(R.id.btnAddToWishlistStoreFrag);
            btnAddToCartStoreFrag = itemView.findViewById(R.id.btnAddToCartStoreFrag);
            spnrQuantityStoreFrag = itemView.findViewById(R.id.spnrQuantityStoreFrag);
        }
    }
}
