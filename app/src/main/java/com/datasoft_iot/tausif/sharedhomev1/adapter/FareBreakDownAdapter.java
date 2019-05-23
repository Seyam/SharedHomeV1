package com.datasoft_iot.tausif.sharedhomev1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.activity.SignUpActivity;
import com.datasoft_iot.tausif.sharedhomev1.model.RoomAvailabilityResponse;

import java.util.List;

public class FareBreakDownAdapter extends RecyclerView.Adapter<FareBreakDownAdapter.ViewHolder> {

    private List<RoomAvailabilityResponse> dataList;
    private Context context;
    String checkInDate, checkOutDate;

    public FareBreakDownAdapter(List<RoomAvailabilityResponse> dataList, Context context, String checkInDate, String checkOutDate) {
        this.dataList = dataList;
        this.context = context;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_farebreakdown, parent, false);
        return new FareBreakDownAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_checkin_date.setText(checkInDate);
        holder.txt_checkout_date.setText(checkOutDate);
        holder.heading_total_cost.setText(dataList.get(position).getCost()+"$ per night");
        holder.txt_cost_per_night.setText(dataList.get(position).getCost()+"$ x "+dataList.get(position).getDays_of_stay()+" night");
        holder.value_cost_per_night.setText(dataList.get(position).getTotal_rent()+"$");
        holder.value_service_charge.setText(dataList.get(position).getService_charge()+"$");
        holder.value_total_cost.setText(dataList.get(position).getTotal_cost()+"$");
        holder.payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();

                openSignUpAcitivity();

            }
        });

    }

    private void openSignUpAcitivity() {
            Intent intent = new Intent(context.getApplicationContext(), SignUpActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView heading_total_cost, value_total_cost,value_service_charge, txt_cost_per_night, value_cost_per_night, txt_checkout_date, txt_checkin_date;
        Button payButton;

        public ViewHolder(View itemView) {
            super(itemView);

            heading_total_cost = itemView.findViewById(R.id.heading_total_cost);
            txt_checkin_date = itemView.findViewById(R.id.txt_checkin_date);
            txt_checkout_date = itemView.findViewById(R.id.txt_checkout_date);
            txt_cost_per_night = itemView.findViewById(R.id.txt_cost_per_night);
            value_cost_per_night = itemView.findViewById(R.id.value_cost_per_night);
            value_service_charge = itemView.findViewById(R.id.value_service_charge);
            value_total_cost = itemView.findViewById(R.id.value_total_cost);
            payButton = itemView.findViewById(R.id.payButton);


        }
    }
}
