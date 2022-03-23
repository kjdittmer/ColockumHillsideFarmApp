package com.example.colockumhillsidefarmapp.ui.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.colockumhillsidefarmapp.R;


public class AddProductActivity extends AppCompatActivity {

    EditText txtNameAddProdAct, txtQuantityAddProdAct, txtImageUrlProdAct, txtShortDescAddProdAct, txtLongDescAddProdAct, txtPriceAddProdAct, txtPackageQuantityAddProdAct;
    Button btnAddProductAddProdAct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.colockumhillsidefarmapp.R.layout.activity_add_product);

        initVariables();


    }

    public void initVariables() {
        btnAddProductAddProdAct = findViewById(R.id.btnAddProductAddProdAct);
        txtNameAddProdAct = findViewById(R.id.txtNameAddProdAct);
        txtQuantityAddProdAct = findViewById(R.id.txtQuantityAddProdAct);
        txtImageUrlProdAct = findViewById(R.id.txtImageUrlProdAct);
        txtShortDescAddProdAct = findViewById(R.id.txtShortDescAddProdAct);
        txtLongDescAddProdAct = findViewById(R.id.txtLongDescAddProdAct);
        txtPriceAddProdAct = findViewById(R.id.txtPriceAddProdAct);
        txtPackageQuantityAddProdAct = findViewById(R.id.txtPackageQuantityAddProdAct);

    }
}