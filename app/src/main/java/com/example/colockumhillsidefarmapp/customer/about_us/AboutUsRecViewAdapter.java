package com.example.colockumhillsidefarmapp.customer.about_us;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class AboutUsRecViewAdapter extends RecyclerView.Adapter<AboutUsRecViewAdapter.ViewHolder>{
    private static final String TAG = "AboutUsRecViewAdapter";
    private static final String INFO = "info";

    private ArrayList<Info> infoList = new ArrayList<>();
    private Context mContext;

    public AboutUsRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Info info = infoList.get(holder.getAdapterPosition());
        holder.txtName.setText(info.getName());
        Glide.with(mContext)
                .asBitmap()
                .load(info.getImageUrl())
                .into(holder.imgItem);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AboutUsActivity.class);
                intent.putExtra(INFO, info);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    public void setInfoList(ArrayList<Info> infoList) {
        this.infoList= infoList;
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<Info> filteredinfoList) {
        infoList = filteredinfoList;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgItem;
        private CardView parent;
        private TextView txtName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgItem = itemView.findViewById(R.id.imgInfo);
            txtName = itemView.findViewById(R.id.txtInfoHeading);
        }
    }
}
