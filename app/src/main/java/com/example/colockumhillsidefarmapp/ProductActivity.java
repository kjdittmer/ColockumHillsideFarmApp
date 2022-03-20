package com.example.colockumhillsidefarmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductActivity extends AppCompatActivity {

    private Product product;

    private static final String PRODUCT_ID = "productId";
    private ImageView imgProductProdAct;
    private TextView txtProductNameProdAct, txtShortDescProdAct, txtProductPriceProdAct,
            txtProductQuantityLeftProdAct, txtLongDescProdAct;
    private Button btnAddToWishlist, btnAddToCart;
    private Spinner spnrQuantitySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        initVariables();

        Intent intent = getIntent();
        if(getIntent() != null){
            int productId = intent.getIntExtra(PRODUCT_ID, -1);
            if(productId != -1){
                product = ShoppingCart.getInstance().getProduct(productId);
                if(product != null){
                    setData(product);
                }
            }
        }

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantitySelected = (int)spnrQuantitySelected.getSelectedItem();

                ShoppingCart.getInstance().addProductToCart(product, quantitySelected);

                String plurality = "";
                if (quantitySelected > 1) {
                    plurality = "s";
                }
                Toast.makeText(view.getContext(), quantitySelected + " " + product.getName() + plurality +
                        " added to cart!", Toast.LENGTH_SHORT).show();

                System.out.println(ShoppingCart.getInstance().getCart());

                Intent intent = new Intent(view.getContext(), ShoppingCartActivity.class);
                startActivity(intent);

            }
        });

        btnAddToWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantitySelected = (int) spnrQuantitySelected.getSelectedItem();

                ShoppingCart.getInstance().addProductToWishlist(product, quantitySelected);

                String plurality = "";
                if (quantitySelected > 1) {
                    plurality = "s";
                }
                Toast.makeText(view.getContext(), quantitySelected + " " + product.getName() + plurality +
                        " added to wishlist!", Toast.LENGTH_SHORT).show();

                System.out.println(ShoppingCart.getInstance().getWishlist());
            }
        });
    }

    private void setData(Product product){
        txtProductNameProdAct.setText(product.getName());
        txtShortDescProdAct.setText(product.getShortDesc());
        String amount = "/lb";
        if(product.getName().equals("Eggs")){
            amount = "/dozen";
        }
        txtProductPriceProdAct.setText("$" + product.getPrice() + amount);
        txtProductQuantityLeftProdAct.setText("Quantity Left: " + product.getQuantity());
        txtLongDescProdAct.setText(product.getLongDesc());

        Glide.with(this).load(product.getImageUrl()).into(imgProductProdAct);

        ArrayList<Integer> quantity = new ArrayList<>();
        for(int i = 1; i <= product.getQuantity(); i++){
            quantity.add(i);
        }

        ArrayAdapter<Integer> quantityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                quantity);
        spnrQuantitySelected.setAdapter(quantityAdapter);

    }

    private void initVariables(){
        imgProductProdAct = findViewById(R.id.imgProductProdAct);
        txtProductNameProdAct = findViewById(R.id.txtProductNameProdAct);
        txtShortDescProdAct = findViewById(R.id.txtShortDescProdAct);
        txtProductPriceProdAct = findViewById(R.id.txtProductPriceProdAct);
        txtProductQuantityLeftProdAct = findViewById(R.id.txtProductQuantityLeftProdAct);
        btnAddToWishlist = findViewById(R.id.btnAddToWishlist);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        spnrQuantitySelected = findViewById(R.id.spnrQuantitySelected);
        txtLongDescProdAct = findViewById(R.id.txtLongDescProdAct);

    }
}