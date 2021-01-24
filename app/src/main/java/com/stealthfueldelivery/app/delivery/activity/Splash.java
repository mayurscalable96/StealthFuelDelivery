package com.stealthfueldelivery.app.delivery.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.stealthfueldelivery.app.delivery.R;
import com.stealthfueldelivery.app.delivery.others.HttpsTrustManager;
import com.stealthfueldelivery.app.delivery.others.SharedPrefrenceData;

public class Splash extends AppCompatActivity {

    Intent intent;
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    SharedPrefrenceData sharedPrefrenceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        HttpsTrustManager.allowAllSSL(this);

        sharedPrefrenceData = new SharedPrefrenceData(this);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                //sharedPrefrenceData.logout();
                //Log.e("id ",sharedPrefrenceData.getDeliveryBoyId());
               // Log.e("aaaaaad ",sharedPrefrenceData.getDeliveryBoyAccessToken());
                /* Create an Intent that will start the Menu-Activity. */
                if(sharedPrefrenceData.getDeliveryBoyId()!=null){
                    intent = new Intent(Splash.this,Home.class);
                    startActivity(intent);
                }else{
                    intent = new Intent(Splash.this,Login.class);
                    startActivity(intent);
                }


            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
