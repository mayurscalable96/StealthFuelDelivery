package com.stealthfueldelivery.app.delivery.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.stealthfueldelivery.app.delivery.R;
import com.stealthfueldelivery.app.delivery.fragement.AssignedOrderDelivered;
import com.stealthfueldelivery.app.delivery.geterseter.NewOrderItem;

import java.util.ArrayList;
import java.util.List;

public class DeliveredOrderAdapter extends RecyclerView.Adapter<DeliveredOrderAdapter.ViewHolder> implements Filterable {

    private List<NewOrderItem> newOrderItems;
    private List<NewOrderItem> newOrderItemFilterable;
    private Context context;

    public DeliveredOrderAdapter(Context context, List<NewOrderItem> newOrderItems) {
        this.newOrderItems = newOrderItems;
        this.context = context;
        this.newOrderItemFilterable = newOrderItems;

    }

    @Override
    public DeliveredOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivered_order_row, parent, false);

        DeliveredOrderAdapter.ViewHolder viewHolder = new DeliveredOrderAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DeliveredOrderAdapter.ViewHolder holder, int position) {
        NewOrderItem orderListItem = newOrderItemFilterable.get(position);
        holder.order_id.setText("#"+orderListItem.getOrder_id());
        holder.order_status.setText(orderListItem.getOrder_status());
        holder.order_address.setText(orderListItem.getOrder_address());
        holder.order_date.setText(orderListItem.getOrder_date() + " " + orderListItem.getOrder_time());
        holder.order_vehical.setText(orderListItem.getVehicle_make() + " " + orderListItem.getVehicle_model() + " "
                + orderListItem.getVehicle_color() + " " + orderListItem.getVehicle_plateno());
        holder.order_gastype.setText(orderListItem.getGas_type());

        if(orderListItem.getExtra_amount().equals("0")){
            holder.order_timeslot.setText(orderListItem.getTime_slot());
        }
        else{
            holder.order_timeslot.setText(orderListItem.getTime_slot()+" Extra amount is $"+orderListItem.getExtra_amount());
        }


    }

    @Override
    public int getItemCount() {
        return newOrderItemFilterable.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView order_id, order_status, order_address, order_date, order_vehical, order_gastype,order_timeslot;


        public ViewHolder(View view) {
            super(view);

            order_id = view.findViewById(R.id.order_id);
            order_status = view.findViewById(R.id.order_status);
            order_address = view.findViewById(R.id.order_address);
            order_date = view.findViewById(R.id.order_date);
            order_vehical = view.findViewById(R.id.order_vehical);
            order_gastype = view.findViewById(R.id.order_gastype);
            order_timeslot = view.findViewById(R.id.order_timeslot);

            view.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    AssignedOrderDelivered.recycleDeliveredOrderClick(newOrderItemFilterable.get(getAdapterPosition()));
                }
            });
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    newOrderItemFilterable = newOrderItems;
                } else {
                    List<NewOrderItem> filteredList = new ArrayList<>();
                    for (NewOrderItem row : newOrderItems) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getOrder_id().toLowerCase().contains(charString.toLowerCase())
                                || row.getOrder_status().toLowerCase().contains(charString.toLowerCase())
                                || row.getOrder_address().toLowerCase().contains(charString.toLowerCase())
                                || row.getOrder_date().toLowerCase().contains(charString.toLowerCase())
                                || row.getVehicle_make().toLowerCase().contains(charString.toLowerCase())
                                || row.getVehicle_model().toLowerCase().contains(charString.toLowerCase())
                                || row.getVehicle_color().toLowerCase().contains(charString.toLowerCase())
                                || row.getVehicle_plateno().toLowerCase().contains(charString.toLowerCase())
                                || row.getGas_type().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    newOrderItemFilterable = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = newOrderItemFilterable;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                newOrderItemFilterable = (ArrayList<NewOrderItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };

    }
}