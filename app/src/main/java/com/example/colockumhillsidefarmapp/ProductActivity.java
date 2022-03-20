package com.example.colockumhillsidefarmapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

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

        //TODO get product data from rec view
        Product product = new Product(1, "Pork Chop", 10, "https://atlas-content-cdn.pixelsquid.com/stock-images/pork-chop-raw-chops-ZemwxrE-600.jpg",
                "A tasty pork chop", "Long description", 4.99);

        setData(product);
    }

    private void setData(Product product){
        txtProductNameProdAct.setText(product.getName());
        txtShortDescProdAct.setText(product.getShortDesc());
        txtProductPriceProdAct.setText(String.valueOf(product.getPrice()));
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