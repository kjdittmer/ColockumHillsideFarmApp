package com.example.colockumhillsidefarmapp.customer.shopping_cart;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.colockumhillsidefarmapp.R;
import com.example.colockumhillsidefarmapp.DBInterface;
import com.example.colockumhillsidefarmapp.customer.CustomerDashboardActivity;
import com.example.colockumhillsidefarmapp.customer.store.Product;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Optional;

import org.json.JSONException;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;

public class
ShoppingCartActivity extends AppCompatActivity {
    private CartProductRecViewAdapter adapter;
    private RecyclerView recyclerView;
    private TextView txtTotalShoppingCartAct;
    private Button btnContinueShoppingShoppingCartAct, btnCheckoutShoppingCartAct;
//    private ArrayList<Product> productsInCart;
//    private HashMap<Product, Integer> cart;
    private PaymentsClient paymentsClient;
    ArrayList<ShoppingCartItem> shoppingCart;



    public static final String clientKey = "AZdPoVE_jOqAVWmdep2onHn_rr1J5P7RJaEuMWtHwctBgSDRdiqX-OgSOiWraluAsVrhkjp02ORP2gfZ";
    public static final int PAYPAL_REQUEST_CODE = 123;

    // Paypal Configuration Object
    private static PayPalConfiguration config = new PayPalConfiguration()
            // Start with mock environment.  When ready,
            // switch to sandbox (ENVIRONMENT_SANDBOX)
            // or live (ENVIRONMENT_PRODUCTION)
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            // on below line we are passing a client id.
            .clientId(clientKey);


    public static final int PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST;
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;
    private static final long SHIPPING_COST_CENTS = 90 * PaymentsUtil.CENTS_IN_A_UNIT.longValue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        initData();
        setData();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        paymentsClient = createPaymentsClient(this);
    }

    public static PaymentsClient createPaymentsClient(Activity activity) {
        Wallet.WalletOptions walletOptions =
                new Wallet.WalletOptions.Builder().setEnvironment(Constants.PAYMENTS_ENVIRONMENT).build();
        return Wallet.getPaymentsClient(activity, walletOptions);
    }

    private void initData() {
        recyclerView = findViewById(R.id.cartRecView);
        txtTotalShoppingCartAct = findViewById(R.id.txtTotalShoppingCartAct);
        btnContinueShoppingShoppingCartAct = findViewById(R.id.btnContinueShoppingShoppingCartAct);
        btnCheckoutShoppingCartAct = findViewById(R.id.btnCheckoutShoppingCartAct);
    }

    private double getTotalCost() {
        double totalCost = 0;
        for (ShoppingCartItem shoppingCartItem : shoppingCart) {
            totalCost += shoppingCartItem.getProduct().getPrice() * shoppingCartItem.getQuantity();
        }
        return totalCost;
    }

    private void setData() {
        adapter = new CartProductRecViewAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shoppingCart = DBInterface.getInstance().getShoppingCart(adapter);

//        productsInCart = new ArrayList<>();
//        cart = ShoppingCart.getInstance().getCart();
//        for (Product product : cart.keySet()){
//            productsInCart.add(product);
//        }
        adapter.setShoppingCart(shoppingCart);

        DBInterface.getInstance().setTotalCost(txtTotalShoppingCartAct);

        btnCheckoutShoppingCartAct.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);
                builder.setMessage("Are you signed into your Google account on this device and do you have Google Pay set up?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //adapter.notifyDataSetChanged();
                        //reload();
                        requestPayment(view);
                        //getPayment();   /* this is for the paypal */
//                for (Product product : cart.keySet()) {
//                    DBInterface.getInstance().addTransaction(product, cart.get(product), product.getPrice(), Calendar.getInstance().getTime());
//                }
                        for (ShoppingCartItem shoppingCartItem : shoppingCart) {
                            DBInterface.getInstance().addTransaction(shoppingCartItem.getProduct(),
                                    shoppingCartItem.getQuantity(), shoppingCartItem.getProduct().getPrice(), Calendar.getInstance().getTime());
                        }
                        //DBInterface.getInstance().clearShoppingCart();
                        Toast.makeText(view.getContext(), "Please set up Google Pay", Toast.LENGTH_SHORT);

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.create().show();
            }
        });

        btnContinueShoppingShoppingCartAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CustomerDashboardActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setGooglePayAvailable(boolean available) {
        btnCheckoutShoppingCartAct.setVisibility(View.VISIBLE);
    }

    private void handlePaymentSuccess(PaymentData paymentData) {

        // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
        final String paymentInfo = paymentData.toJson();
        if (paymentInfo == null) {
            return;
        }
        try {
            JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");

            final JSONObject tokenizationData = paymentMethodData.getJSONObject("tokenizationData");
            final String token = tokenizationData.getString("token");
            final JSONObject info = paymentMethodData.getJSONObject("info");
            final String billingName = info.getJSONObject("billingAddress").getString("name");
            Toast.makeText(
                    this, getString(R.string.payments_show_name, billingName),
                    Toast.LENGTH_LONG).show();

            // Logging token string.
            Log.d("Google Pay token: ", token);

        } catch (JSONException e) {
            throw new RuntimeException("The selected garment cannot be parsed from the list of elements");
        }
    }

    private void handleError(int statusCode) {
        Log.e("loadPaymentData failed", String.format("Error code: %d", statusCode));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void requestPayment(View view) {

        // Disables the button to prevent multiple clicks.
        btnCheckoutShoppingCartAct.setClickable(false);

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
        double garmentPrice = getTotalCost();
        long garmentPriceCents = Math.round(garmentPrice * PaymentsUtil.CENTS_IN_A_UNIT.longValue());
        long priceCents = garmentPriceCents + SHIPPING_COST_CENTS;

        Optional<JSONObject> paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(priceCents);
        if (!paymentDataRequestJson.isPresent()) {
            return;
        }
        PaymentDataRequest request =
                PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());
        if (request != null) {
            AutoResolveHelper.resolveTask(
                    paymentsClient.loadPaymentData(request),
                    this, LOAD_PAYMENT_DATA_REQUEST_CODE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // value passed in AutoResolveHelper
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {

                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        handlePaymentSuccess(paymentData);
                        break;

                    case Activity.RESULT_CANCELED:
                        // The user cancelled the payment attempt
                        break;

                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        handleError(status.getStatusCode());
                        break;
                }

                // Re-enables the Google Pay payment button.
                btnCheckoutShoppingCartAct.setClickable(true);
        }
    }

    private static JSONArray getAllowedCardNetworks() {
        return new JSONArray()
                .put("AMEX")
                .put("DISCOVER")
                .put("INTERAC")
                .put("JCB")
                .put("MASTERCARD")
                .put("MIR")
                .put("VISA");
    }

    private static JSONArray getAllowedCardAuthMethods() {
        return new JSONArray()
                .put("PAN_ONLY")
                .put("CRYPTOGRAM_3DS");
    }

    private static JSONObject getGatewayTokenizationSpecification() throws JSONException {
        return new JSONObject() {{
            put("type", "PAYMENT_GATEWAY");
            put("parameters", new JSONObject() {{
                put("gateway", "example");
                put("gatewayMerchantId", "exampleGatewayMerchantId");
            }});
        }};
    }

    public void reload() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private static JSONObject getBaseCardPaymentMethod() throws JSONException {
        JSONObject cardPaymentMethod = new JSONObject();
        cardPaymentMethod.put("type", "CARD");

        JSONObject parameters = new JSONObject();
        parameters.put("allowedAuthMethods", getAllowedCardAuthMethods());
        parameters.put("allowedCardNetworks", getAllowedCardNetworks());
        // Optionally, you can add billing address/phone number associated with a CARD payment method.
        parameters.put("billingAddressRequired", true);

        JSONObject billingAddressParameters = new JSONObject();
        billingAddressParameters.put("format", "FULL");

        parameters.put("billingAddressParameters", billingAddressParameters);

        cardPaymentMethod.put("parameters", parameters);

        return cardPaymentMethod;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Optional<JSONObject> getIsReadyToPayRequest() {
        try {
            JSONObject isReadyToPayRequest = getBaseRequest();
            isReadyToPayRequest.put(
                    "allowedPaymentMethods", new JSONArray().put(getBaseCardPaymentMethod()));

            return Optional.of(isReadyToPayRequest);

        } catch (JSONException e) {
            return Optional.empty();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void possiblyShowGooglePayButton() {

        final Optional<JSONObject> isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest();
        if (!isReadyToPayJson.isPresent()) {
            return;
        }

        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
        IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());
        Task<Boolean> task = paymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(this,
                new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            setGooglePayAvailable(task.getResult());
                        } else {
                            Log.w("isReadyToPay failed", task.getException());
                        }
                    }
                });
    }

    private static JSONObject getTransactionInfo(String price) throws JSONException {
        JSONObject transactionInfo = new JSONObject();
        transactionInfo.put("totalPrice", price);
        transactionInfo.put("totalPriceStatus", "FINAL");
        transactionInfo.put("countryCode", Constants.COUNTRY_CODE);
        transactionInfo.put("currencyCode", Constants.CURRENCY_CODE);
        transactionInfo.put("checkoutOption", "COMPLETE_IMMEDIATE_PURCHASE");

        return transactionInfo;
    }

    private static JSONObject getMerchantInfo() throws JSONException {
        return new JSONObject().put("merchantName", "Example Merchant");
    }


    private static JSONObject getCardPaymentMethod() throws JSONException {
        JSONObject cardPaymentMethod = getBaseCardPaymentMethod();
        cardPaymentMethod.put("tokenizationSpecification", getGatewayTokenizationSpecification());

        return cardPaymentMethod;
    }

    private static JSONObject getBaseRequest() throws JSONException {
        return new JSONObject().put("apiVersion", 2).put("apiVersionMinor", 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Optional<JSONObject> getPaymentDataRequest(long priceCents) {

        final String price = PaymentsUtil.centsToString(priceCents);

        try {
            JSONObject paymentDataRequest = PaymentsUtil.getBaseRequest();
            paymentDataRequest.put(
                    "allowedPaymentMethods", new JSONArray().put(PaymentsUtil.getCardPaymentMethod()));
            paymentDataRequest.put("transactionInfo", PaymentsUtil.getTransactionInfo(price));
            paymentDataRequest.put("merchantInfo", PaymentsUtil.getMerchantInfo());

      /* An optional shipping address requirement is a top-level property of the PaymentDataRequest
      JSON object. */
            paymentDataRequest.put("shippingAddressRequired", true);

            JSONObject shippingAddressParameters = new JSONObject();
            shippingAddressParameters.put("phoneNumberRequired", false);

            JSONArray allowedCountryCodes = new JSONArray(Constants.SHIPPING_SUPPORTED_COUNTRIES);

            shippingAddressParameters.put("allowedCountryCodes", allowedCountryCodes);
            paymentDataRequest.put("shippingAddressParameters", shippingAddressParameters);
            return Optional.of(paymentDataRequest);

        } catch (JSONException e) {
            return Optional.empty();
        }
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




    private void getPayment() {

        // Getting the amount from editText
        String amount = String.valueOf(getTotalCost());

        // Creating a paypal payment on below line.
        PayPalPayment payment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD", "Colockum Hillside Farm",
                PayPalPayment.PAYMENT_INTENT_SALE);

        // Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        // Putting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        // Starting the intent activity for result
        // the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // If the result is from paypal
//        if (requestCode == PAYPAL_REQUEST_CODE) {
//
//            // If the result is OK i.e. user has not canceled the payment
//            if (resultCode == Activity.RESULT_OK) {
//
//                // Getting the payment confirmation
//                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
//
//                // if confirmation is not null
//                if (confirm != null) {
//                    try {
//                        // Getting the payment details
//                        String paymentDetails = confirm.toJSONObject().toString(4);
//                        // on below line we are extracting json response and displaying it in a text view.
//                        JSONObject payObj = new JSONObject(paymentDetails);
//                        String payID = payObj.getJSONObject("response").getString("id");
//                        String state = payObj.getJSONObject("response").getString("state");
//                        //paymentTV.setText("Payment " + state + "\n with payment id is " + payID);
//                    } catch (JSONException e) {
//                        // handling json exception on below line
//                        Log.e("Error", "an extremely unlikely failure occurred: ", e);
//                    }
//                }
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                // on below line we are checking the payment status.
//                Log.i("paymentExample", "The user canceled.");
//            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
//                // on below line when the invalid paypal config is submitted.
//                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
//            }
//        }
//    }


}
