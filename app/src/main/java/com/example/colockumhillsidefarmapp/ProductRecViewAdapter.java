package com.example.colockumhillsidefarmapp;

import android.content.Context;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ProductRecViewAdapter extends RecyclerView.Adapter<ProductRecViewAdapter.ViewHolder> {
    private static final String TAG = "ProductRecViewAdapter";

    private ArrayList<Product> products = new ArrayList<>();
    private Context mContext;

    public ProductRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.txtProductName.setText(products.get(holder.getAdapterPosition()).getName());
        String amount = "/lb";
        if(products.get(holder.getAdapterPosition()).getName().equals("Eggs")){
            amount = "/dozen";
        }
        holder.txtPrice.setText(products.get(holder.getAdapterPosition()).getPrice() + amount);
        Glide.with(mContext)
                .asBitmap()
                .load(products.get(holder.getAdapterPosition()).getImageUrl())
                .into(holder.imgProduct);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, products.get(holder.getAdapterPosition()).getName() + " Selected", Toast.LENGTH_SHORT).show();
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtPrice = itemView.findViewById(R.id.txtPrice);

        }
    }
}
