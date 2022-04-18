package com.example.colockumhillsidefarmapp.customer.shopping_cart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.example.colockumhillsidefarmapp.customer.store.ProductActivity;
import com.example.colockumhillsidefarmapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class CartProductRecViewAdapter extends RecyclerView.Adapter<CartProductRecViewAdapter.ViewHolder> {
    private static final String PRODUCT = "product";


    private ArrayList<Product> productsInCart;
    private HashMap<Product, Integer> cart;
    private Context mContext;
    private ShoppingCartActivity currentActivity;

    public CartProductRecViewAdapter(Context mContext, ShoppingCartActivity currentActivity) {
        this.mContext = mContext;
        cart = ShoppingCart.getInstance().getCart();
        this.currentActivity = currentActivity;
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

        holder.txtQuantityCartItem.setText("Quantity: ");

        DecimalFormat df = new DecimalFormat("0.00");
        String price = df.format(product.getPrice());
        holder.txtPriceCartItem.setText("$" + price + "/" + product.getPackageQuantity());

        String subtotal = df.format(quantityInCart * product.getPrice());
        holder.txtSubtotalCartItem.setText("Subtotal: $" + subtotal);

        ArrayList<Integer> quantity = new ArrayList<>();
        quantity.add(quantityInCart);
        for(int i = 1; i <= product.getQuantity(); i++){
            if (i != quantityInCart) quantity.add(i);
        }
        ArrayAdapter<Integer> quantityAdapter = new ArrayAdapter<Integer>(holder.txtProductNameCartItem.getContext(), R.layout.spinner_text_cart_item,
                quantity);

        holder.spnrQuantityCartItem.setAdapter(quantityAdapter);
        holder.spnrQuantityCartItem.setSelection(0, false);

        holder.spnrQuantityCartItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            boolean firstTrigger = true;

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (firstTrigger) {
//                    firstTrigger = false;
//                } else {
                    ShoppingCart.getInstance().setProductQuantity(product, (int) quantity.get(i));
                    currentActivity.reload();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.parentCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductActivity.class);
                intent.putExtra(PRODUCT, product);
                mContext.startActivity(intent);
            }
        });

        holder.imgDeleteCartItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to remove " + product.getName() + " from your cart?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ShoppingCart.getInstance().removeProductFromCart(product);
                        currentActivity.reload();
                        Toast.makeText(mContext, product.getName() + " Removed", Toast.LENGTH_SHORT).show();
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
        private ImageButton imgDeleteCartItem;
        private TextView txtProductNameCartItem, txtQuantityCartItem, txtPriceCartItem, txtSubtotalCartItem;
        private Spinner spnrQuantityCartItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            spnrQuantityCartItem = itemView.findViewById(R.id.spnrQuantityCartItem);
            parentCartItem = itemView.findViewById(R.id.parentCartItem);
            imgProductCartItem = itemView.findViewById(R.id.imgProductCartItem);
            txtProductNameCartItem = itemView.findViewById(R.id.txtProductNameCartItem);
            txtQuantityCartItem = itemView.findViewById(R.id.txtQuantityCartItem);
            txtPriceCartItem = itemView.findViewById(R.id.txtPriceCartItem);
            txtSubtotalCartItem = itemView.findViewById(R.id.txtSubtotalCartItem);
            imgDeleteCartItem = itemView.findViewById(R.id.imgDeleteCartItem);
        }
    }
}
