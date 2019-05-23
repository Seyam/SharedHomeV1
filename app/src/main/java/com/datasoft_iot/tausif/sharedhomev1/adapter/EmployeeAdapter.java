package com.datasoft_iot.tausif.sharedhomev1.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.activity.RoomInfoActivity;
import com.datasoft_iot.tausif.sharedhomev1.model.ApartmentListResponse;

import java.util.List;


public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private List<ApartmentListResponse> dataList;
    private Context context;


    public EmployeeAdapter(List<ApartmentListResponse> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_apartment_list_data, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, final int position) {
//        holder.txtId.setText(dataList.get(position).getId().toString());
        holder.txtName.setText(dataList.get(position).getApartment_name());
        holder.txtDescription.setText(dataList.get(position).getDescription());
        holder.txtAvgFare.setText(dataList.get(position).getAvg_fare()+"$ per night");
        holder.mRatingBar.setRating(dataList.get(position).getRating());

        Glide.with(context).load(dataList.get(position).getImage_url())
                .thumbnail(0.5f)
                .into(holder.imageViewHotelPic);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "You clicked: "+dataList.get(position).getApartment_name(), Toast.LENGTH_SHORT).show();
                openRoomInfoActivity();

            }

            private void openRoomInfoActivity() {
                Intent intent = new Intent(context, RoomInfoActivity.class);
                intent.putExtra("apartment_id",dataList.get(position).getApartment_id());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }


        });

//        if(dataList.get(position).getTempValue() > 20.0){
//            holder.linearLayout.setBackgroundColor(Color.RED);
//        }
//        else {
//            holder.linearLayout.setBackgroundColor(Color.GREEN);
//        }

    }



    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout;
        ImageView imageViewHotelPic;
        TextView txtName, txtDescription, txtAvgFare;
        RatingBar mRatingBar;



        EmployeeViewHolder(View itemView) {
            super(itemView);
//            txtId = (TextView) itemView.findViewById(R.id.txt_id_field);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout_field);
            imageViewHotelPic = itemView.findViewById(R.id.image_view_hotel_pic);
            txtName = (TextView) itemView.findViewById(R.id.txt_name_field);
            txtDescription = (TextView) itemView.findViewById(R.id.txt_description_field);
            txtAvgFare = (TextView) itemView.findViewById(R.id.txt_avgFare_field);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);

        }
    }
}