package com.datasoft_iot.tausif.sharedhomev1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.activity.DatepickerActivity;
import com.datasoft_iot.tausif.sharedhomev1.model.RoomInfoResponse;

import java.util.List;


public class RoomInfoAdapter extends RecyclerView.Adapter<RoomInfoAdapter.ViewHolder> {

    private List<RoomInfoResponse> dataList;
    private Context context;


    public RoomInfoAdapter(List<RoomInfoResponse> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_room_info, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Glide.with(context).load(dataList.get(position).getImage_url())
                .thumbnail(0.5f)
                .into(holder.imageViewHotelPic);
        holder.txtAmenities.setText("Facilities: "+dataList.get(position).getFacility());
        holder.txtTotalBed.setText("Total bed: "+dataList.get(position).getTotalBed());
        holder.txtBedAvailable.setText(dataList.get(position).getNo_of_bed_available()+" available");
        holder.txtBedBooked.setText(dataList.get(position).getNo_of_bed_booked()+" booked");
        holder.txtKitchen.setText(dataList.get(position).getRoom_kitchen_no()+" kitchen");
        holder.txtWashroom.setText(dataList.get(position).getRoom_washroom_no().toString()+" washroom");

        holder.requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context.getApplicationContext(), "Hi", Toast.LENGTH_SHORT).show();
                openDatepickerActivity();
            }

            private void openDatepickerActivity() {
                Intent intent = new Intent(context, DatepickerActivity.class);
                intent.putExtra("room_uniq_id", dataList.get(position).getRoom());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });




    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        LinearLayout linearLayout;
        ImageView imageViewHotelPic;
        TextView txtBedAvailable, txtBedBooked, txtKitchen, txtWashroom, txtAmenities, txtTotalBed;
        Button requestButton;



        public ViewHolder(View itemView) {
            super(itemView);

            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout_field);
            imageViewHotelPic = itemView.findViewById(R.id.image_view_hotel_pic);
            txtBedAvailable = (TextView) itemView.findViewById(R.id.txt_bed_available_field);
            txtBedBooked = (TextView) itemView.findViewById(R.id.txt_bed_booked_field);
            txtKitchen = (TextView) itemView.findViewById(R.id.txt_kitchen_field);
            txtWashroom = (TextView) itemView.findViewById(R.id.txt_washroom_field);
            txtAmenities = (TextView) itemView.findViewById(R.id.txt_amenities_field);
            txtTotalBed =(TextView) itemView.findViewById(R.id.txt_total_bed_field);
            requestButton = (Button) itemView.findViewById(R.id.requestButton);
        }
    }

}