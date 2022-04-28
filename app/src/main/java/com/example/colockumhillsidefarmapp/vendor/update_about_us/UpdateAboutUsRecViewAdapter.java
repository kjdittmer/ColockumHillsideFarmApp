package com.example.colockumhillsidefarmapp.vendor.update_about_us;

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
import com.example.colockumhillsidefarmapp.customer.about_us.Info;
import com.example.colockumhillsidefarmapp.vendor.VendorDashboardActivity;

import java.util.ArrayList;

public class UpdateAboutUsRecViewAdapter extends RecyclerView.Adapter<UpdateAboutUsRecViewAdapter.ViewHolder> {
    private static final String INFO = "info";

    private ArrayList<Info> infoList = new ArrayList<>();
    private Context mContext;
    private RecyclerView.Adapter thisAdapter;
    private VendorDashboardActivity currentActivity;


    public UpdateAboutUsRecViewAdapter(Context mContext, VendorDashboardActivity currentActivity) {
        this.mContext = mContext;
        thisAdapter = this;
        this.currentActivity = currentActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_edit_about_us, parent, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpdateAboutUsRecViewAdapter.ViewHolder holder, int position) {
        Info info = infoList.get(holder.getAdapterPosition());
        holder.txtInfoNameEditInfo.setText(info.getName());
        Glide.with(mContext)
                .asBitmap()
                .load(info.getImageUrl())
                .into(holder.imgInfoEditInfo);

        holder.parentEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditAboutUsActivity.class);
                intent.putExtra(INFO, info);
                mContext.startActivity(intent);
            }
        });

        holder.btnDeleteEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure you want to remove " + info.getName() + " from About Us?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBInterface.getInstance().removeInfo(info);
                        currentActivity.reloadRecipes();
                        Toast.makeText(mContext, info.getName() + " removed.", Toast.LENGTH_SHORT).show();
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
        return infoList.size();
    }

    public void setInfoList(ArrayList<Info> infoList) {
        this.infoList = infoList;
        notifyDataSetChanged();
    }

    public void filterList(ArrayList<Info> filteredinfoList) {
        infoList = filteredinfoList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView parentEditInfo;
        private ImageView imgInfoEditInfo;
        private TextView txtInfoNameEditInfo;
        private ImageButton btnDeleteEditInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentEditInfo = itemView.findViewById(R.id.parentEditAboutUs);
            imgInfoEditInfo = itemView.findViewById(R.id.imgInfoEditInfo);
            txtInfoNameEditInfo = itemView.findViewById(R.id.txtInfoNameEditInfo);
            btnDeleteEditInfo = itemView.findViewById(R.id.btnDeleteEditInfo);
        }
    }
}
