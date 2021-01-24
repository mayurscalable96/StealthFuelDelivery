package com.stealthfueldelivery.app.delivery.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.stealthfueldelivery.app.delivery.R;
import com.stealthfueldelivery.app.delivery.others.HttpsTrustManager;
import com.stealthfueldelivery.app.delivery.others.SharedPrefrenceData;
import com.stealthfueldelivery.app.delivery.others.UrlConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class NewOrderParticular extends AppCompatActivity  {

    Intent intent;
    String invoice_idText,order_idText,order_addressText,order_dateText,order_timeText,order_statusText;
    String vehicle_yearText,vehicle_makeText,vehicle_modelText,vehicle_plateText,vehicle_stateText,vehicle_colorText;
    String gastypeText,gaspriceText;
    String gasQtyText;
    double gasTotalamountText=0.0;
    String destination_latitude,destination_longitude;
    String timeSlotText,extraAmountText;



    TextView order_id,order_address,order_date,order_status;
    TextView order_vehicle;
    TextView gastype,gasprice;
    EditText gas_qty;
    TextView gas_totalamount;
    TextView customer_name,customer_mob;
    TextView comment;
    TextView order_timeslot;
    TextView extra_ammount,delivery_charge;
    Button submit_order;


    private ProgressDialog pDialog;
    android.support.v7.widget.Toolbar toolbar;
    TextView toolbartextView;
    View view;
    SharedPrefrenceData sharedPrefrenceData;
    String access_tokene;
    String customer_nameText,customer_mobText;
    String order_commentText;
    String deliveryChargeText;

    LinearLayout comment_layout;
    LinearLayout customer_moblayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order_particular);

        toolbartextView = findViewById(R.id.toolbartextView);
        toolbartextView.setText("Order Detail");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        intent = getIntent();
        invoice_idText = intent.getStringExtra("invoice_id");

        order_idText = intent.getStringExtra("order_id");
        order_addressText = intent.getStringExtra("order_address");
        order_dateText = intent.getStringExtra("order_date");
        order_timeText = intent.getStringExtra("order_time");
        order_statusText = intent.getStringExtra("order_status");
        vehicle_yearText = intent.getStringExtra("vehicle_year");
        vehicle_makeText = intent.getStringExtra("vehicle_make");
        vehicle_modelText = intent.getStringExtra("vehicle_model");
        vehicle_plateText = intent.getStringExtra("vehicle_plate");
        vehicle_colorText = intent.getStringExtra("vehicle_color");
        vehicle_stateText = intent.getStringExtra("vehicle_state");
        gastypeText = intent.getStringExtra("gasetype");
        gaspriceText = intent.getStringExtra("gaseprice");
        customer_nameText = intent.getStringExtra("customer_name");
        customer_mobText = intent.getStringExtra("customer_mob");
        order_commentText = intent.getStringExtra("order_comment");
        destination_latitude = intent.getStringExtra("latitude");
        destination_longitude = intent.getStringExtra("longitude");
        timeSlotText = intent.getStringExtra("time_slot");
        extraAmountText = intent.getStringExtra("extra_amount");
        deliveryChargeText = intent.getStringExtra("delivery_charge");

        order_id = findViewById(R.id.order_id);
        order_address = findViewById(R.id.order_address);
        order_date = findViewById(R.id.order_date);
        order_status = findViewById(R.id.order_status);
        order_vehicle = findViewById(R.id.order_vehicle);

        gastype = findViewById(R.id.gastype);
        gasprice = findViewById(R.id.gasprice);

        customer_name = findViewById(R.id.customer_name);
        customer_mob = findViewById(R.id.customer_mob);

        comment = findViewById(R.id.comment);

        order_timeslot  = findViewById(R.id.order_timeslot);

        extra_ammount = findViewById(R.id.extra_ammount);
        delivery_charge = findViewById(R.id.delivery_charge);


        comment_layout = findViewById(R.id.comment_layout);

        if(order_commentText.equalsIgnoreCase("")){
            comment_layout.setVisibility(View.GONE);
        }
        else{
            comment.setText(order_commentText);
            comment_layout.setVisibility(View.VISIBLE);
        }

        customer_moblayout = findViewById(R.id.customer_moblayout);
        customer_moblayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+customer_mobText));
                startActivity(intent);
            }
        });

        view = findViewById(android.R.id.content);


        order_id.setText("#"+order_idText);
        order_address.setText(order_addressText);
        order_date.setText(order_dateText+" "+order_timeText);
        order_status.setText(order_statusText);
        order_vehicle.setText(vehicle_makeText+ " "+ vehicle_modelText+" "+vehicle_colorText+" "+vehicle_plateText);
        gastype.setText(gastypeText);
        gasprice.setText("$"+gaspriceText);
        extra_ammount.setText("$"+extraAmountText);
        delivery_charge.setText("$"+deliveryChargeText);
        /*if(extraAmountText.equals("0")){
            gasprice.setText("$"+gaspriceText);
        }
        else{
            gasprice.setText("$"+gaspriceText+"+$"+extraAmountText);
        }*/

        customer_name.setText(customer_nameText);
        customer_mob.setText(customer_mobText);
        order_timeslot.setText(timeSlotText);

        gas_totalamount = findViewById(R.id.gas_totalamount);

        gas_qty = findViewById(R.id.gas_qty);
        gas_qty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                gasQtyText = gas_qty.getText().toString();
                if(gasQtyText.length()>0){
                    //Log.e("gaspriceText",gaspriceText);
                   gasTotalamountText = Double.parseDouble(gasQtyText )* Double.parseDouble(gaspriceText);

                  // Log.e("Extra ",extraAmountText);
                  // Log.e("gasTotalamountText ", String.valueOf(gasTotalamountText));
                   //Log.e("dsdsdd", String.valueOf(gasTotalamountText));
                   gasTotalamountText = Double.parseDouble(String.format("%.2f", gasTotalamountText));
                   gasTotalamountText = gasTotalamountText + Double.parseDouble(extraAmountText);
                  // String gastamt = String.valueOf(gasTotalamountText);
                    gasTotalamountText = Double.parseDouble(new DecimalFormat("##.##").format(gasTotalamountText));
                   gas_totalamount.setText("(Gas Price * Quantity) + Extra Amount = $"+gasTotalamountText);
                }
                else{
                    gas_totalamount.setText("$"+"0");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        submit_order = findViewById(R.id.submit_order);
        submit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 gasQtyText = gas_qty.getText().toString();
                 if(gasQtyText.length()<=0){
                     Snackbar.make(view, "Enter Gas Quantity.", Snackbar.LENGTH_LONG).show();
                 }
                 else{
                     pDialog = new ProgressDialog(NewOrderParticular.this, R.style.AppCompatAlertDialogStyle);
                     // Showing progress dialog before making http request
                     pDialog.setMessage("Loading...");
                     pDialog.setCanceledOnTouchOutside(false);
                     pDialog.show();
                     submitOrderVolley();
                 }
            }
        });

        sharedPrefrenceData = new SharedPrefrenceData(this);
        access_tokene = sharedPrefrenceData.getDeliveryBoyAccessToken();
    }

    private void submitOrderVolley() {
//        HttpsTrustManager.allowAllSSL();

        String url = UrlConstants.SUBMITORDER+"?AccessTokan="+access_tokene+"&InvoiceNo="+invoice_idText+"&Qty="+gasQtyText+"&TotalAmount="+gasTotalamountText;
        Log.e("url ",url);
        // Pass second argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String Status = response.getString("Status");
                            JSONObject jsonObject = response.getJSONObject("Response");
                            if(Status.equalsIgnoreCase("Success")){
                               // String Message = jsonObject.getString("Message");
                                //Snackbar.make(view, Message, Snackbar.LENGTH_LONG).show();
                                Intent intent = new Intent(NewOrderParticular.this,Home.class);
                                startActivity(intent);
                            }
                            else{
                                pDialog.dismiss();
                                Snackbar.make(view, Status, Snackbar.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            pDialog.dismiss();
                            Log.e("JSONException", String.valueOf(e) );
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //VolleyLog.e("Error: ", error.getMessage());
                pDialog.dismiss();
                String message = null;
                Log.e("volleyError", String.valueOf(volleyError));

                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
                } else if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
                }
            }
        });

        req.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        /* Add your Requests to the RequestQueue to execute */
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        mRequestQueue.add(req);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.location) {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?saddr="
                            +""+"&daddr="+destination_latitude+","+destination_longitude));
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}