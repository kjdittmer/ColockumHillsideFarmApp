package com.example.colockumhillsidefarmapp.customer.shopping_cart;

import android.content.Context;
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
import com.example.colockumhillsidefarmapp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class OrderConfirmationTransactionRecViewAdapter extends RecyclerView.Adapter<OrderConfirmationTransactionRecViewAdapter.ViewHolder> {

    private ArrayList<Transaction> transactions = new ArrayList<>();
    private Context mContext;

    public OrderConfirmationTransactionRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = transactions.get(holder.getAdapterPosition());
        holder.txtProductNameTransaction.setText(transaction.getProduct().getName());
        String price;
        DecimalFormat df = new DecimalFormat("0.00");
        price = df.format(transaction.getProduct().getPrice());
        holder.txtPriceTransaction.setText("$" + price + "/" + transaction.getProduct().getPackageQuantity());
        holder.txtQuantityTransaction.setText("Quantity: " + String.valueOf(transaction.getQuantity()));
        String totalCost;
        totalCost = df.format(transaction.getCost());
        holder.txtTotalCostTransaction.setText("Total: $" + totalCost);
        holder.txtDateTransaction.setText(Calendar.getInstance().getTime().toString());
        Glide.with(mContext)
                .asBitmap()
                .load(transaction.getProduct().getImageUrl())
                .into(holder.imgProductTransaction);
        holder.txtUserTransaction.setText(transaction.getUser());

//        holder.parent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, ProductActivity.class);
//                intent.putExtra(PRODUCT, product);
//                mContext.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<Transaction> filteredTransactions) {
        transactions = filteredTransactions;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CardView parent;
        private ImageView imgProductTransaction;
        private TextView txtProductNameTransaction, txtPriceTransaction,
                txtQuantityTransaction, txtTotalCostTransaction, txtDateTransaction, txtUserTransaction;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgProductTransaction = itemView.findViewById(R.id.imgProductTransaction);
            txtProductNameTransaction = itemView.findViewById(R.id.txtProductNameTransaction);
            txtPriceTransaction = itemView.findViewById(R.id.txtPriceTransaction);
            txtQuantityTransaction = itemView.findViewById(R.id.txtQuantityTransaction);
            txtTotalCostTransaction = itemView.findViewById(R.id.txtTotalCostTransaction);
            txtDateTransaction = itemView.findViewById(R.id.txtDateTransaction);
            txtUserTransaction = itemView.findViewById(R.id.txtUserTransaction);
        }
    }
}
