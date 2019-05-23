package com.datasoft_iot.tausif.sharedhomev1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.model.SelectedSensorList;

import java.util.List;


public class SelectedSensorListAdapter extends RecyclerView.Adapter<SelectedSensorListAdapter.ViewHolder>{

    private List<SelectedSensorList> mSelectedSensorLists;

    private Context context;

    public SelectedSensorListAdapter(Context context) {
        this.context = context;

    }


//    public DeviceListAdapter(List<DeviceInfo> deviceInfos, int rowLayout, Context context) {
//        this.mDeviceInfos = deviceInfos;
//        this.rowLayout = rowLayout;
//       this.mContext = context;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_show_selected_sensor_list, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {



        Log.e("Time", mSelectedSensorLists.get(position).getSensortype());

        viewHolder.mTextViewSensorType.setText(mSelectedSensorLists.get(position).getSensortype());

        viewHolder.mTextViewSensorAmout.setText(mSelectedSensorLists.get(position).getSensorQuantity());

     //   viewHolder.mTextViewSensorTypeCount.setText(String.valueOf(position+1));

        viewHolder.mImageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // SelectedSensorList removeItem = mSelectedSensorLists.get(position);
                mSelectedSensorLists.remove(viewHolder.getAdapterPosition());
                notifyItemRemoved(viewHolder.getAdapterPosition());


              //  Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addData(List<SelectedSensorList> selectedSensorLists) {
        mSelectedSensorLists= selectedSensorLists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return mSelectedSensorLists == null ? 0 : mSelectedSensorLists .size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView  mTextViewSensorType, mTextViewSensorAmout, mTextViewSensorTypeCount;

        public ImageView mImageViewDelete;

       // public ImageView cityImage;



        public ViewHolder(View itemView) {
            super(itemView);
            mTextViewSensorType = (TextView) itemView.findViewById(R.id.text_view_sensor_type_recyc);

            mTextViewSensorAmout = (TextView) itemView.findViewById(R.id.text_view_sensor_amount_recyc);

            mImageViewDelete =itemView.findViewById(R.id.image_view_delete);

          //  mTextViewSensorTypeCount = itemView.findViewById(R.id.text_view_sensor_type_cout_recyc);


        }

        @Override
        public void onClick(View view) {

        }
    }
}
