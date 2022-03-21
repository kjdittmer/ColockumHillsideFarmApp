package com.example.colockumhillsidefarmapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class CartProductRecViewAdapter extends RecyclerView.Adapter<CartProductRecViewAdapter.ViewHolder> {
    private static final String PRODUCT_ID = "productId";


    private ArrayList<Product> productsInCart;
    private HashMap<Product, Integer> cart;
    private Context mContext;

    public CartProductRecViewAdapter(Context mContext) {
        this.mContext = mContext;
        cart = ShoppingCart.getInstance().getCart();
    }

    public void setProductsInCart(ArrayList<Product> productsInCart) {
        this.productsInCart = productsInCart;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cart_product, parent, false);
        return new CartProductRecViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int quantityInCart = cart.get(productsInCart.get(holder.getAdapterPosition()));
        Product product = productsInCart.get(holder.getAdapterPosition());

        holder.txtProductNameCartItem.setText(product.getName());

        Glide.with(mContext)
                .asBitmap()
                .load(product.getImageUrl())
                .into(holder.imgProductCartItem);

        holder.txtQuantityCartItem.setText("Quantity: " + quantityInCart);

        String amount = "/lb";
        if(product.getName().equals("Eggs")){
            amount = "/dozen";
        }
        holder.txtPriceCartItem.setText("$" + product.getPrice() + amount);

        double subtotal = quantityInCart * product.getPrice();
        holder.txtSubtotalCartItem.setText("Subtotal: &" + subtotal);

        holder.parentCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductActivity.class);
                intent.putExtra(PRODUCT_ID, productsInCart.get(holder.getAdapterPosition()).getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cart.keySet().size();
    }

    public double getTotalCost(){
        double totalCost = 0;
        for(Product productInCart : cart.keySet()) {
            totalCost += productInCart.getPrice() * cart.get(productInCart);
        }
        return totalCost;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parentCartItem;
        private ImageView imgProductCartItem;
        private TextView txtProductNameCartItem, txtQuantityCartItem, txtPriceCartItem, txtSubtotalCartItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentCartItem = itemView.findViewById(R.id.parentCartItem);
            imgProductCartItem = itemView.findViewById(R.id.imgProductCartItem);
            txtProductNameCartItem = itemView.findViewById(R.id.txtProductNameCartItem);
            txtQuantityCartItem = itemView.findViewById(R.id.txtQuantityCartItem);
            txtPriceCartItem = itemView.findViewById(R.id.txtPriceCartItem);
            txtSubtotalCartItem = itemView.findViewById(R.id.txtSubtotalCartItem);

        }
    }
}
