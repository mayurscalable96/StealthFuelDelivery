package com.stealthfueldelivery.app.delivery.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stealthfueldelivery.app.delivery.R;
import com.stealthfueldelivery.app.delivery.others.SharedPrefrenceData;

public class Account extends NavigationDrawerActivity {

    TextView deliveryboy_name,deliveryboy_mob,deliveryboy_email;
    SharedPrefrenceData sharedPrefrenceData;
    LinearLayout deliveryboy_detail_layout,change_pwd_layout,logout_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_account);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        View contentView = inflater.inflate(R.layout.activity_account, null, false);
        drawer.addView(contentView, 0);
        toolbartextView.setText("Account");

        sharedPrefrenceData = new SharedPrefrenceData(this);

        deliveryboy_name = findViewById(R.id.deliveryboy_name);
        deliveryboy_mob = findViewById(R.id.deliveryboy_mob);
        deliveryboy_email = findViewById(R.id.deliveryboy_email);

        deliveryboy_name.setText(sharedPrefrenceData.getDeliveryBoyName());
        deliveryboy_mob.setText(sharedPrefrenceData.getDeliveryBoyMob());
        deliveryboy_email.setText(sharedPrefrenceData.getDeliveryBoyEmail());

        change_pwd_layout = findViewById(R.id.change_pwd_layout);
        // change_pwd_layout.setOnClickListener(this);
        change_pwd_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Account.this, ChangePassword.class);
                startActivity(intent);
            }
        });

        logout_layout = findViewById(R.id.logout_layout);
        logout_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefrenceData.logout();
                intent = new Intent(Account.this,Login.class);
                startActivity(intent);
            }
        });
    }
}
