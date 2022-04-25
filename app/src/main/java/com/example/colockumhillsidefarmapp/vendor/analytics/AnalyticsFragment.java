package com.example.colockumhillsidefarmapp.vendor.analytics;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.Transaction;

import java.util.ArrayList;

public class AnalyticsFragment extends Fragment {

    private RecyclerView transactionRecView;
    private SwipeRefreshLayout layout;
    private ArrayList<Transaction> transactionList;
    private TransactionRecViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_analytics, container, false);

        setHasOptionsMenu(true);

        adapter = new TransactionRecViewAdapter(root.getContext());
        transactionList = DBInterface.getInstance().getAllTransactions(adapter);
        transactionRecView = root.findViewById(R.id.analyticsRecView);

        transactionRecView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(root.getContext());
        transactionRecView.setLayoutManager(manager);

        adapter.setTransactions(transactionList);

        layout = root.findViewById(R.id.swipeRefreshLayoutAnalytics);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                transactionList = DBInterface.getInstance().getAllTransactions(adapter);
                adapter.setTransactions(transactionList);
                layout.setRefreshing(false);
            }
        });

        Spinner spnrSearchCriteria = root.findViewById(R.id.spnrSearchCriteriaAnalytics);
        ArrayList<String> searchCriteria = new ArrayList<>();
        searchCriteria.add("Product Name");
        searchCriteria.add("Date");
        searchCriteria.add("User");
        ArrayAdapter<String> searchCriteriaAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_search_criteria,
                searchCriteria);
        spnrSearchCriteria.setAdapter(searchCriteriaAdapter);

        EditText txtSearch = root.findViewById(R.id.txtSearchUpdateAnalytics);
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

    private void filter(String text) {
        ArrayList<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction transaction : transactionList) {
            if (transaction.getProduct().getName().toLowerCase().contains(text.toLowerCase())) {
                filteredTransactions.add(transaction);
            }
        }

        adapter.filterList(filteredTransactions);
    }

    private void closeKeyboard () {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
