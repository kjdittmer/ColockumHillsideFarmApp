package com.example.colockumhillsidefarmapp.vendor.update_about_us;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.about_us.Info;

import java.util.ArrayList;

public class UpdateAboutUsActivity extends AppCompatActivity {

    private RecyclerView updateAboutUsRecView;
    private UpdateAboutUsRecViewAdapter adapter;
    private SwipeRefreshLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_about_us);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ArrayList<Info> allInfo = DBInterface.getInstance().getAllInfo(adapter);

        updateAboutUsRecView = findViewById(R.id.updateInfoRecView);

        updateAboutUsRecView.setAdapter(adapter);
        updateAboutUsRecView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setInfoList(allInfo);

        layout = findViewById(R.id.swipeRefreshLayoutUpdateInfo);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ArrayList<Info> allInfo = DBInterface.getInstance().getAllInfo(adapter);
                adapter.setInfoList(allInfo);
                layout.setRefreshing(false);
            }
        });

    }

    public void reload() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.update_about_us, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_add_info:
                Intent intent = new Intent(this, AddAboutUsActivity.class);
                startActivity(intent);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}