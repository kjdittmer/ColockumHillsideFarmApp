package com.example.colockumhillsidefarmapp.customer.about_us;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.R;

import java.util.ArrayList;

public class CustomerAboutUsFragment extends Fragment {

    private RecyclerView aboutUsRecView;
    private SwipeRefreshLayout layout;
    private ArrayList<Info> infoList;
    private AboutUsRecViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_customer_about_us, container, false);

        adapter = new AboutUsRecViewAdapter(root.getContext());
        infoList = DBInterface.getInstance().getAllInfo(adapter);
        aboutUsRecView = root.findViewById(R.id.aboutUsRecView);

        aboutUsRecView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(root.getContext());
        aboutUsRecView.setLayoutManager(manager);

        adapter.setInfoList(infoList);

        layout = root.findViewById(R.id.swipeRefreshLayoutAboutUs);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                infoList= DBInterface.getInstance().getAllInfo(adapter);
                adapter.setInfoList(infoList);
                layout.setRefreshing(false);
            }
        });

        EditText txtSearch = root.findViewById(R.id.txtSearchAboutUs);
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

        txtSearch.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    closeKeyboard();
                    return true;
                }
                return false;
            }
        });

        return root;
    }

    private void closeKeyboard () {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void filter(String text) {
        ArrayList<Info> filteredInfoList = new ArrayList<>();

        for (Info info : infoList) {
            if (info.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredInfoList.add(info);
            }
        }

        adapter.filterList(filteredInfoList);
    }

}