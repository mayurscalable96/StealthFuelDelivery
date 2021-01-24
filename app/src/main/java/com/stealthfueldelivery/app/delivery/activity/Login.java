package com.stealthfueldelivery.app.delivery.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.stealthfueldelivery.app.delivery.others.HttpsTrustManager;
import com.stealthfueldelivery.app.delivery.R;
import com.stealthfueldelivery.app.delivery.others.SharedPrefrenceData;
import com.stealthfueldelivery.app.delivery.others.UrlConstants;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements View.OnClickListener{

    TextView newaccount,forgotpwd,toolbartextView;
    EditText login_email,login_pwd;
    Button login;
    Intent intent;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String login_emailText,login_pwdtext;
    View view;
    private ProgressDialog pDialog;
    private android.support.v7.widget.Toolbar toolbar;
    SharedPrefrenceData sharedPrefrenceData;
    String device_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // enabling action bar app icon and behaving it as toggle button
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbartextView = findViewById(R.id.toolbartextView);
        toolbartextView.setText("Login");
        toolbartextView.setGravity(Gravity.CENTER);

        view = findViewById(android.R.id.content);

        login_email = findViewById(R.id.login_email);
        login_pwd = findViewById(R.id.login_pwd);


        login = findViewById(R.id.login);
        login.setOnClickListener(this);
        forgotpwd = findViewById(R.id.forgotpwd);
        forgotpwd.setOnClickListener(this);

        sharedPrefrenceData = new SharedPrefrenceData(this);
        device_id = sharedPrefrenceData.getDeviceToken();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.login:
                checkEditext();
                break;
            case R.id.forgotpwd:{
                intent = new Intent(this,ForgotPassword.class);
                startActivity(intent);
                break;
            }
            default:
                break;
        }
    }

    public void checkEditext(){
        login_emailText = login_email.getText().toString();
        login_pwdtext = login_pwd.getText().toString();

        if(!login_emailText.matches(emailPattern)){
            Snackbar.make(view, "Enter Correct Email", Snackbar.LENGTH_LONG).show();
            return;
        }
        if(login_pwdtext.length()<=4){
            Snackbar.make(view, "Password must be greater than 4 character", Snackbar.LENGTH_LONG).show();
            return;
        }
        else{
            //Snackbar.make(view, "", Snackbar.LENGTH_LONG).show();
            pDialog = new ProgressDialog(Login.this, R.style.AppCompatAlertDialogStyle);
            // Showing progress dialog before making http request
            pDialog.setMessage("Loading...");
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
            login();

        }

    }

    private void login() {
//        HttpsTrustManager.allowAllSSL();

        String url = UrlConstants.LOGIN+"?EmailId="+login_emailText+"&Password="+login_pwdtext+"&DeviceID="+device_id;
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
                                String Message = jsonObject.getString("Message");

                                String deliveryBoy_id = jsonObject.getString("DeliveryBoyID");
                                String deliveryBoy_name = jsonObject.getString("Name");
                                String deliveryBoy_email = jsonObject.getString("EmailID");
                                String deliveryBoy_mob = jsonObject.getString("MobileNo");
                                String deliveryBoy_access_token = jsonObject.getString("AccessToken");
                                String deliveryBoy_address = jsonObject.getString("Address");
                                String deliveryBoy_zipcode = jsonObject.getString("ZipCode");
                                sharedPrefrenceData.setDeliveryBoyId(deliveryBoy_id);
                                sharedPrefrenceData.setDeliveryBoyName(deliveryBoy_name);
                                sharedPrefrenceData.setDeliveryBoyEmail(deliveryBoy_email);
                                sharedPrefrenceData.settDeliveryBoyMob(deliveryBoy_mob);
                                sharedPrefrenceData.setDeliveryBoyAccessToken(deliveryBoy_access_token);
                                sharedPrefrenceData.setDeliveryBoyZipCode(deliveryBoy_zipcode);

                                pDialog.dismiss();
                                Snackbar.make(view, Message, Snackbar.LENGTH_LONG).show();
                                Intent intent = new Intent(Login.this,Home.class);
                                startActivity(intent);

                            }
                            else{
                                pDialog.dismiss();
                                String ErrorMessage = jsonObject.getString("ErrorMessage");
                                Snackbar.make(view, ErrorMessage, Snackbar.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            pDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pDialog.dismiss();
                //VolleyLog.e("Error: ", error.getMessage());
                String message = null;
                Snackbar snackbar;
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

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
