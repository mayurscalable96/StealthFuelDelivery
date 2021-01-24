package com.stealthfueldelivery.app.delivery.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.stealthfueldelivery.app.delivery.R;

public class DeliveredOrderParticular extends AppCompatActivity {

    Intent intent;
    String invoice_idText,order_idText,order_addressText,order_dateText,order_timeText,order_statusText;
    String vehicle_yearText,vehicle_makeText,vehicle_modelText,vehicle_plateText,vehicle_stateText,vehicle_colorText;
    String gastypeText,gaspriceText;
    String customer_nameText,customer_mobText;
    String gas_quantityText,gas_totalamountText;
    String order_completedateText,order_commentText;
    String ratingText,ratingDescriptionText;
    String timeSlotText,extraAmountText;
    String deliveryChargeText;

    TextView order_id,order_address,order_date,order_status;
    TextView order_vehicle;
    TextView gastype,gasprice;
    TextView gas_quantity,gas_totalamount,total_amount,gas_deliverycharges;
    TextView customer_name,customer_mob;
    TextView comment;
    TextView order_delivereddate;
    TextView ratingdescription_show;
    TextView order_timeslot;
    TextView extra_ammount,delivery_charge;

    private ProgressDialog pDialog;
    android.support.v7.widget.Toolbar toolbar;
    TextView toolbartextView;

    LinearLayout comment_layout;

    RatingBar ratingBar_show;
    LinearLayout customer_moblayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivered_order_particular);

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
        gas_quantityText = intent.getStringExtra("gas_qty");
        gas_totalamountText = intent.getStringExtra("total_amount");
        customer_nameText = intent.getStringExtra("customer_name");
        customer_mobText = intent.getStringExtra("customer_mob");
        order_commentText = intent.getStringExtra("order_comment");
        order_completedateText = intent.getStringExtra("order_completedate");
        ratingText = intent.getStringExtra("rating");
        ratingDescriptionText = intent.getStringExtra("rating_description");
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
        gas_quantity = findViewById(R.id.gas_quantity);
        gas_totalamount = findViewById(R.id.gas_totalamount);
        comment = findViewById(R.id.comment);
        order_delivereddate = findViewById(R.id.order_delivereddate);
        ratingBar_show = findViewById(R.id.ratingBar_show);
        ratingdescription_show = findViewById(R.id.ratingdescription_show);
        order_timeslot  = findViewById(R.id.order_timeslot);
        total_amount  = findViewById(R.id.total_amount);
        gas_deliverycharges  = findViewById(R.id.gas_deliverycharges);


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

        order_id.setText(order_idText);
        order_address.setText(order_addressText);
        order_date.setText(order_dateText+" "+order_timeText);
        order_status.setText(order_statusText);
        order_vehicle.setText(vehicle_makeText+ " "+ vehicle_modelText+" "+vehicle_colorText+" "+vehicle_plateText);
        gastype.setText(gastypeText);
        gas_quantity.setText(gas_quantityText+ " Gallon");
        gas_totalamount.setText("(Gas Price * Quantity) + Extra Amount = $"+gas_totalamountText);
        customer_name.setText(customer_nameText);
        customer_mob.setText(customer_mobText);
        order_delivereddate.setText(order_completedateText);
        order_timeslot.setText(timeSlotText);
        gasprice.setText("$"+gaspriceText);
        extra_ammount.setText("$"+extraAmountText);
        delivery_charge.setText("$"+deliveryChargeText);
        gas_deliverycharges.setText("$"+deliveryChargeText);

        Double gasTotal = Double.valueOf(gas_totalamountText);
        Double dCharge = Double.valueOf(deliveryChargeText);
        String total = String.valueOf(gasTotal+dCharge);




        total_amount.setText("$"+ total);

       /* if(extraAmountText.equals("0")){
            gasprice.setText("$"+gaspriceText);
        }
        else{
            gasprice.setText("$"+gaspriceText+"+$"+extraAmountText);
        }*/

        if(!ratingText.equals("")) {

            ratingBar_show.setRating(Float.parseFloat(ratingText));
            ratingdescription_show.setText(ratingDescriptionText);
        }

        toolbartextView = findViewById(R.id.toolbartextView);
        toolbartextView.setText("Delivered Order Detail");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // enabling action bar app icon and behaving it as toggle button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        customer_moblayout = findViewById(R.id.customer_moblayout);
        customer_moblayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+customer_mobText));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
