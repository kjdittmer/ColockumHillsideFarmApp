package com.example.colockumhillsidefarmapp.customer.store;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.ShoppingCart;
import com.example.colockumhillsidefarmapp.customer.shopping_cart.ShoppingCartActivity;
import com.example.colockumhillsidefarmapp.customer.wishlist.Wishlist;
import com.example.colockumhillsidefarmapp.customer.favorites.Favorites;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    private Product product;

    private static final String PRODUCT = "product";
    private ImageView imgProductProdAct;
    private TextView txtProductNameProdAct, txtShortDescProdAct, txtProductPriceProdAct,
            txtProductQuantityLeftProdAct, txtLongDescProdAct;
    private Button btnAddToWishlist, btnAddToCart, btnAddToFavorites;
    private Spinner spnrQuantitySelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initVariables();

        Intent intent = getIntent();
        if(getIntent() != null){
            product = intent.getParcelableExtra(PRODUCT);
            if(product != null){
                setData(product);
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

                Intent intent = new Intent(view.getContext(), ShoppingCartActivity.class);
                startActivity(intent);

            }
        });

        btnAddToWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBInterface.getInstance().addProductToWishlist(product);

                Toast.makeText(view.getContext(), product.getName() +
                        " added to wishlist!", Toast.LENGTH_SHORT).show();
            }
        });

        btnAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBInterface.getInstance().addProductToFavoriteProducts(product);

                Toast.makeText(view.getContext(), product.getName() +
                        " added to favorites!", Toast.LENGTH_SHORT).show();

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

        String price;
        DecimalFormat df = new DecimalFormat("0.00");
        price = df.format(product.getPrice());
        txtProductPriceProdAct.setText("$" + price + amount);
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
        btnAddToFavorites = findViewById(R.id.btnAddToFavorites);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}