package com.stealthfueldelivery.app.delivery.fragement;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import com.stealthfueldelivery.app.delivery.activity.Login;
import com.stealthfueldelivery.app.delivery.activity.NewOrderParticular;
import com.stealthfueldelivery.app.delivery.adapter.NewOrderAdapter;
import com.stealthfueldelivery.app.delivery.geterseter.NewOrderItem;
import com.stealthfueldelivery.app.delivery.others.HttpsTrustManager;
import com.stealthfueldelivery.app.delivery.others.SharedPrefrenceData;
import com.stealthfueldelivery.app.delivery.others.UrlConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.M)
public class NewOrderAssigned extends Fragment implements RecyclerView.OnScrollChangeListener {

    RecyclerView recycleview_neworder;
    NewOrderAdapter adapter;
    List<NewOrderItem> newOrderItems;
    SharedPrefrenceData sharedPrefrenceData;
    String deliveryboy_id,access_token;
    View view ;
    private int requestCount = 0;
    ProgressBar progress_bar;
    public static Context context;
    SearchView search_neworder;
    SwipeRefreshLayout mSwipeRefreshLayout;


    public NewOrderAssigned() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_order_assigned, container, false);
        context = getActivity();
        progress_bar = view.findViewById(R.id.progress_bar);
        sharedPrefrenceData = new SharedPrefrenceData(context);
        deliveryboy_id = sharedPrefrenceData.getDeliveryBoyId();
        access_token = sharedPrefrenceData.getDeliveryBoyAccessToken();
        setOrderRecycleView();

        /*recycleview_neworder.addOnItemTouchListener(new RecyclerTouchListener(context, recycleview_neworder, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                NewOrderItem orderListItem1 = newOrderItems.get(position);
                Intent intent = new Intent(context, NewOrderParticular.class);
                intent.putExtra("invoice_id",orderListItem1.getInvoice_id());
                intent.putExtra("order_id",orderListItem1.getOrder_id());
                intent.putExtra("order_address",orderListItem1.getOrder_address());
                intent.putExtra("order_date",orderListItem1.getOrder_date());
                //intent.putExtra("order_time",orderListItem1.getOrder_time());
                intent.putExtra("order_status",orderListItem1.getOrder_status());
                intent.putExtra("order_comment",orderListItem1.getOrder_comment());

                intent.putExtra("vehicle_year",orderListItem1.getVehicle_year());
                intent.putExtra("vehicle_make",orderListItem1.getVehicle_make());
                intent.putExtra("vehicle_model",orderListItem1.getVehicle_model());
                intent.putExtra("vehicle_color",orderListItem1.getVehicle_color());
                intent.putExtra("vehicle_plate",orderListItem1.getVehicle_plateno());
                intent.putExtra("vehicle_state",orderListItem1.getVehicle_state());

                intent.putExtra("gasetype",orderListItem1.getGas_type());
                intent.putExtra("gaseprice",orderListItem1.getGas_price());

                intent.putExtra("customer_name",orderListItem1.getCustomer_name());
                intent.putExtra("customer_mob",orderListItem1.getCustomer_mob());
                intent.putExtra("latitude",orderListItem1.getLatitude());
                intent.putExtra("longitude",orderListItem1.getLongitude());

                intent.putExtra("time_slot",orderListItem1.getTime_slot());
                intent.putExtra("extra_amount",orderListItem1.getExtra_amount());
                // Snackbar.make(view, vehicalListItem.getVehical_id(), Snackbar.LENGTH_LONG).show();
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/

        search_neworder = view.findViewById(R.id.search_neworder);
        search_neworder.setQueryHint("Search new order");
        search_neworder.setIconified(false);
        search_neworder.clearFocus();

                // listening to search query text change
                search_neworder.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        // filter recycler view when query submitted
                        adapter.getFilter().filter(query);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String query) {
                        // filter recycler view when text is changed
                        adapter.getFilter().filter(query);
                        return false;
                    }
                });
        mSwipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestCount = 0;
                mSwipeRefreshLayout.setRefreshing(true);
                newOrderItems.clear();
                getOrderListVolley(deliveryboy_id,access_token,requestCount);

            }
        });

        return view;
    }


    private void setOrderRecycleView(){
        recycleview_neworder = view.findViewById(R.id.recycleview_neworder);
        newOrderItems = new ArrayList<>();
        adapter = new NewOrderAdapter(context,newOrderItems);
        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(context);
        recycleview_neworder.setLayoutManager(layoutManager);
        recycleview_neworder.setAdapter(adapter);
        recycleview_neworder.setOnScrollChangeListener(this);
        getOrderListVolley(deliveryboy_id,access_token,requestCount);
    }

    private void getOrderListVolley(String boy_id, String boy_accesstoken, final int req_count) {
//        HttpsTrustManager.allowAllSSL();
       //
        String url = UrlConstants.NEWORDER+"?DeliveryBoyID="+boy_id+"&AccessTokan="
                +boy_accesstoken+"&indexNo="+req_count;
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

                                JSONArray jsonArray = jsonObject.getJSONArray("OrderAssignDeliveryboy");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    NewOrderItem newOrderItemList = new NewOrderItem();
                                    String invoice_no = jsonObject1.getString("InvoiceNo");
                                    String order_id = jsonObject1.getString("OrderID");
                                    String order_date = jsonObject1.getString("Date");
                                    String order_time = jsonObject1.getString("Time");
                                    String order_address = jsonObject1.getString("Address");
                                    String order_comment = jsonObject1.getString("Comment");
                                    String order_status = jsonObject1.getString("orderstatus");
                                    String payment_status = jsonObject1.getString("PaymentStatus");
                                    String v_id = jsonObject1.getString("VID");
                                    String v_year = jsonObject1.getString("Year");
                                    String v_make = jsonObject1.getString("Make");
                                    String v_model = jsonObject1.getString("Model");
                                    String v_color = jsonObject1.getString("Color");
                                    String v_plateno = jsonObject1.getString("Tag");
                                    String v_state = jsonObject1.getString("TagState");
                                    String gasType = jsonObject1.getString("GasType");
                                    String gasprice = jsonObject1.getString("Price");
                                    String customer_name = jsonObject1.getString("CustFullName");
                                    String customer_mob = jsonObject1.getString("CustMobileNo");
                                    String latitud = jsonObject1.getString("latitude");
                                    String longitude = jsonObject1.getString("longitude");
                                    String time_slot = jsonObject1.getString("TimeSlot");
                                    String extra_amount = jsonObject1.getString("Extraamount");
                                    String deliveryCharge = jsonObject1.getString("Deliverycharge");

                                    // Log.e("dddddd",order_status);
                                    newOrderItemList.setInvoice_id(invoice_no);
                                    newOrderItemList.setOrder_id(order_id);
                                    newOrderItemList.setOrder_date(order_date);
                                    newOrderItemList.setOrder_time(order_time);
                                    newOrderItemList.setOrder_address(order_address);
                                    newOrderItemList.setOrder_comment(order_comment);
                                    newOrderItemList.setVehicle_year(v_year);
                                    newOrderItemList.setOrder_status(order_status);
                                    newOrderItemList.setOrder_paymentstatus(payment_status);
                                    newOrderItemList.setVehicle_year(v_year);
                                    newOrderItemList.setVehicle_make(v_make);
                                    newOrderItemList.setVehicle_model(v_model);
                                    newOrderItemList.setVehicle_color(v_color);
                                    newOrderItemList.setVehicle_plateno(v_plateno);
                                    newOrderItemList.setVehicle_state(v_state);
                                    newOrderItemList.setGas_type(gasType);
                                    newOrderItemList.setGas_price(gasprice);
                                    newOrderItemList.setCustomer_name(customer_name);
                                    newOrderItemList.setCustomer_mob(customer_mob);
                                    newOrderItemList.setLatitude(latitud);
                                    newOrderItemList.setLongitude(longitude);
                                    newOrderItemList.setTime_slot(time_slot);
                                    newOrderItemList.setExtra_amount(extra_amount);
                                    newOrderItemList.setDelivery_charge(deliveryCharge);
                                    if(deliveryCharge.equals("")){
                                        newOrderItemList.setDelivery_charge("0.0");
                                    }
                                    else{
                                        newOrderItemList.setDelivery_charge(deliveryCharge);
                                    }
                                    newOrderItems.add(newOrderItemList);
                                }

                            }
                            else{
                                progress_bar.setVisibility(View.GONE);
                               String ErrorMessage = jsonObject.getString("ErrorMessage");
                                if(ErrorMessage.equalsIgnoreCase("Invalid Token")){
                                    sharedPrefrenceData.logout();
                                    Intent intent = new Intent(context,Login.class);
                                    startActivity(intent);
                                }
                                /*if(req_count==0){
                                     String ErrorMessage = jsonObject.getString("ErrorMessage");
                                    Snackbar.make(view, ErrorMessage, Snackbar.LENGTH_LONG).show();
                                }*/
                                // String ErrorMessage = jsonObject.getString("ErrorMessage");
                                //Snackbar.make(view, ErrorMessage, Snackbar.LENGTH_LONG).show();
                            }

                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            // pDialog.dismiss();
                            //progress_bar.setVisibility(View.GONE);
                            Log.e("JSONException", String.valueOf(e) );
                            e.printStackTrace();
                        }

                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //VolleyLog.e("Error: ", error.getMessage());
                // pDialog.dismiss();
                mSwipeRefreshLayout.setRefreshing(false);
                progress_bar.setVisibility(View.GONE);
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
        RequestQueue mRequestQueue = Volley.newRequestQueue(context);
        mRequestQueue.add(req);
        requestCount++;

    }

    //This method would check that the recyclerview scroll has reached the bottom or not
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;

        }

        return false;
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (isLastItemDisplaying(recycleview_neworder)) {
            //Log.e("yes ","yes");
            //Calling the method getdata again
            getOrderListVolley(deliveryboy_id,access_token,requestCount);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            requestCount = 0;
        }
    }

    public static void recycleNewOrderClick(NewOrderItem orderListItem1){

        Intent intent = new Intent(context, NewOrderParticular.class);
        intent.putExtra("invoice_id",orderListItem1.getInvoice_id());
        intent.putExtra("order_id",orderListItem1.getOrder_id());
        intent.putExtra("order_address",orderListItem1.getOrder_address());
        intent.putExtra("order_date",orderListItem1.getOrder_date());
        //intent.putExtra("order_time",orderListItem1.getOrder_time());
        intent.putExtra("order_status",orderListItem1.getOrder_status());
        intent.putExtra("order_comment",orderListItem1.getOrder_comment());

        intent.putExtra("vehicle_year",orderListItem1.getVehicle_year());
        intent.putExtra("vehicle_make",orderListItem1.getVehicle_make());
        intent.putExtra("vehicle_model",orderListItem1.getVehicle_model());
        intent.putExtra("vehicle_color",orderListItem1.getVehicle_color());
        intent.putExtra("vehicle_plate",orderListItem1.getVehicle_plateno());
        intent.putExtra("vehicle_state",orderListItem1.getVehicle_state());

        intent.putExtra("gasetype",orderListItem1.getGas_type());
        intent.putExtra("gaseprice",orderListItem1.getGas_price());

        intent.putExtra("customer_name",orderListItem1.getCustomer_name());
        intent.putExtra("customer_mob",orderListItem1.getCustomer_mob());
        intent.putExtra("latitude",orderListItem1.getLatitude());
        intent.putExtra("longitude",orderListItem1.getLongitude());

        intent.putExtra("time_slot",orderListItem1.getTime_slot());
        intent.putExtra("extra_amount",orderListItem1.getExtra_amount());
        intent.putExtra("delivery_charge",orderListItem1.getDelivery_charge());
        // Snackbar.make(view, vehicalListItem.getVehical_id(), Snackbar.LENGTH_LONG).show();
        context.startActivity(intent);
    }
}
