package com.example.colockumhillsidefarmapp.vendor.update_about_us;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.example.colockumhillsidefarmapp.customer.about_us.Info;
import com.example.colockumhillsidefarmapp.vendor.VendorDashboardActivity;

import java.util.ArrayList;

public class UpdateAboutUsFragment extends Fragment {

    private RecyclerView updateAboutUsRecView;
    private UpdateAboutUsRecViewAdapter adapter;
    private SwipeRefreshLayout layout;
    private ArrayList<Info> infoList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_update_about_us, container, false);

        setHasOptionsMenu(true);

        adapter = new UpdateAboutUsRecViewAdapter(getContext(), (VendorDashboardActivity) getActivity());
        infoList = DBInterface.getInstance().getAllInfo(adapter);

        updateAboutUsRecView = root.findViewById(R.id.updateAboutUsRecView);

        updateAboutUsRecView.setAdapter(adapter);
        updateAboutUsRecView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setInfoList(infoList);

        layout = root.findViewById(R.id.swipeRefreshLayoutUpdateAboutUs);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                infoList = DBInterface.getInstance().getAllInfo(adapter);
                adapter.setInfoList(infoList);
                layout.setRefreshing(false);
            }
        });

        EditText txtSearch = root.findViewById(R.id.txtSearchUpdateAboutUs);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.update_about_us, menu);
    }

    private void closeKeyboard () {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void filter(String text) {
        ArrayList<Info> filteredInfo = new ArrayList<>();

        for (Info info : infoList) {
            if (info.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredInfo.add(info);
            }
        }

        adapter.filterList(filteredInfo);
    }
}
