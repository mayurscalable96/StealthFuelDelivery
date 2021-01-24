package com.stealthfueldelivery.app.delivery.others;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefrenceData {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;
    private static final String PREFER_NAME = "MyPref";

    public SharedPrefrenceData(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public String getDeliveryBoyId() {
        return pref.getString("deliveryboy_id",null);
    }

    public void setDeliveryBoyId(String deliveryboy_id) {
        editor.putString("deliveryboy_id", deliveryboy_id);
        editor.commit();
    }

    public String getDeliveryBoyName() {
        return pref.getString("deliveryboy_name",null);
    }

    public void setDeliveryBoyName(String deliveryboy_name) {
        editor.putString("deliveryboy_name", deliveryboy_name);
        editor.commit();
    }

    public String getDeliveryBoyMob() {
        return pref.getString("deliveryboy_mob",null);
    }

    public void settDeliveryBoyMob(String deliveryboy_mob) {
        editor.putString("deliveryboy_mob", deliveryboy_mob);
        editor.commit();
    }

    public String getDeliveryBoyEmail() {
        return pref.getString("deliveryboy_email",null);
    }

    public void setDeliveryBoyEmail(String deliveryboy_email) {
        editor.putString("deliveryboy_email", deliveryboy_email);
        editor.commit();
    }

    public String getDeliveryBoyAccessToken() {
        return pref.getString("deliveryboy_access_token",null);
    }

    public void setDeliveryBoyAccessToken(String deliveryboy_access_token) {
        editor.putString("deliveryboy_access_token", deliveryboy_access_token);
        editor.commit();
    }

    public String getDeliveryBoyAddress() {
        return pref.getString("deliveryboy_address",null);
    }

    public void setDeliveryBoyAddress(String deliveryboy_address) {
        editor.putString("deliveryboy_address", deliveryboy_address);
        editor.commit();
    }

    public String getDeliveryBoyZipCode() {
        return pref.getString("deliveryboy_zipcode",null);
    }

    public void setDeliveryBoyZipCode(String deliveryboy_zipcode) {
        editor.putString("deliveryboy_zipcode", deliveryboy_zipcode);
        editor.commit();
    }

    public void logout(){
        //editor.clear();
        editor.remove("deliveryboy_id");
        editor.remove("deliveryboy_name");
        editor.remove("deliveryboy_mob");
        editor.remove("deliveryboy_email");
        editor.remove("deliveryboy_access_token");
        editor.remove("deliveryboy_address");
        editor.remove("deliveryboy_zipcode");
        editor.commit();

    }

    //this method will save the device token to shared preferences
    public boolean setDeviceToken(String token) {
        editor.putString("devicetoken", token);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getDeviceToken() {
        String token = pref.getString("devicetoken", null);
        //if (token == null) {
        //  token = "";
        //}
        //   Log.e("ewewewew",token);
        return token;
    }
}
