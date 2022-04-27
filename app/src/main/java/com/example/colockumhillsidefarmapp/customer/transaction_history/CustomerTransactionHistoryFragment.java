package com.example.colockumhillsidefarmapp.customer.transaction_history;

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

import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.Transaction;
import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.example.colockumhillsidefarmapp.vendor.analytics.TransactionRecViewAdapter;

import java.util.ArrayList;
import java.util.Date;

public class CustomerTransactionHistoryFragment extends Fragment {

    private RecyclerView transactionRecView;
    private SwipeRefreshLayout layout;
    private ArrayList<Transaction> transactionList;
    private TransactionRecViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_customer_transaction_history, container, false);

        adapter = new TransactionRecViewAdapter(root.getContext());

        transactionList = DBInterface.getInstance().getCustomerTransactions(adapter);

        transactionRecView = root.findViewById(R.id.customerTransactionHistoryRecView);

        transactionRecView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(root.getContext());
        transactionRecView.setLayoutManager(manager);

        adapter.setTransactions(transactionList);

        layout = root.findViewById(R.id.swipeRefreshLayoutCustomerTransactionHistory);
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                transactionList = DBInterface.getInstance().getCustomerTransactions(adapter);
                adapter.setTransactions(transactionList);
                layout.setRefreshing(false);
            }
        });

        EditText txtSearch = root.findViewById(R.id.txtSearchCustomerTransactionHisotry);
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
            Product product = transaction.getProduct();
            if (product != null) {
                String input = product.getName();
                if (input != null) {
                    if (input.toLowerCase().contains(text.toLowerCase())) {
                        filteredTransactions.add(transaction);
                    }
                }
            }
            Date time = transaction.getTime();
            if (time != null) {
                String input = time.toString();
                if (input != null) {
                    if (input.toLowerCase().contains(text.toLowerCase())) {
                        filteredTransactions.add(transaction);
                    }
                }
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
