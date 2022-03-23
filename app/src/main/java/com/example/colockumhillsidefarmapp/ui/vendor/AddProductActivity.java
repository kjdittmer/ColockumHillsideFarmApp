package com.example.colockumhillsidefarmapp.ui.vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.Product;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.ShoppingCart;


public class AddProductActivity extends AppCompatActivity {

    EditText txtNameAddProdAct, txtQuantityAddProdAct, txtImageUrlProdAct, txtShortDescAddProdAct, txtLongDescAddProdAct, txtPriceAddProdAct, txtPackageQuantityAddProdAct;
    Button btnAddProductAddProdAct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.colockumhillsidefarmapp.R.layout.activity_add_product);

        initVariables();

        btnAddProductAddProdAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtNameAddProdAct.getText().toString();
                String imageUrl = txtImageUrlProdAct.getText().toString();
                String shortDesc = txtShortDescAddProdAct.getText().toString();
                String longDesc = txtLongDescAddProdAct.getText().toString();
                String packageQuantity = txtPackageQuantityAddProdAct.getText().toString();
                int quantity = Integer.parseInt(txtQuantityAddProdAct.getText().toString());
                double price = Double.parseDouble(txtPriceAddProdAct.getText().toString());
                if(validateData()) {
                    int newProductId = ShoppingCart.getInstance().getNewId();
                    Product productToAdd = new Product(newProductId, name, quantity, imageUrl, shortDesc, longDesc, price, packageQuantity);
                    ShoppingCart.getInstance().addProductToAllProducts(productToAdd);
                    Toast.makeText(view.getContext(), name + " was added to the store.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(view.getContext(), "Please enter valid data.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private boolean validateData() {


        return true;
    }

    private void initVariables() {
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