package com.example.colockumhillsidefarmapp.customer.about_us;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.colockumhillsidefarmapp.R;

public class CustomerAboutUsFragment extends Fragment {

    private Button btnPoultryAndEggs, btnPork, btnVegetables;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_customer_about_us, container, false);

        initVariables(root);

        btnPoultryAndEggs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(root.getContext(), PoultryActivity.class);
                startActivity(intent);
            }
        });

        btnPork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(root.getContext(), PorkActivity.class);
                startActivity(intent);
            }
        });

        btnVegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(root.getContext(), VegetableActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    private void initVariables(View root) {
        btnPoultryAndEggs = root.findViewById(R.id.poultryandeggs);
        btnPork = root.findViewById(R.id.pork);
        btnVegetables = root.findViewById(R.id.vegetables);
    }
}
