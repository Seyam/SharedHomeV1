package com.datasoft_iot.tausif.sharedhomev1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.model.BatteryStatusListResponse;

import java.util.List;


public class BatteryStatusListAdapter extends RecyclerView.Adapter<BatteryStatusListAdapter.ViewHolder>{

    private List<BatteryStatusListResponse> mBatteryStatusListResponseList;

    private Context context;

    public BatteryStatusListAdapter(Context context) {
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
        View view = inflater.inflate(R.layout.item_show__alarm_battery_status, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {



//        Log.e("Alam Type", mBatteryStatusListResponseList.get(position).getAlarm_name());
//
//        Log.e("Date", mBatteryStatusListResponseList.get(position).getBettery_status());
//
//        Log.e("Time ", mBatteryStatusListResponseList.get(position).getTime());


        viewHolder.mTextViewALarmType.setText(mBatteryStatusListResponseList.get(position).getAlarm_name());

        viewHolder.mTextViewAlarmDate.setText(mBatteryStatusListResponseList.get(position).getBettery_status());

        viewHolder.mTextViewAlarmTime.setText(mBatteryStatusListResponseList.get(position).getTime());

        viewHolder.mTextViewAlarmDate_orginal.setText(mBatteryStatusListResponseList.get(position).getDate());


        //   viewHolder.mTextViewSensorTypeCount.setText(String.valueOf(position+1));

//        viewHolder.mImageViewDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//               // SelectedSensorList removeItem = mSelectedSensorLists.get(position);
//                mSelectedSensorLists.remove(viewHolder.getAdapterPosition());
//                notifyItemRemoved(viewHolder.getAdapterPosition());
//
//
//              //  Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    public void addData(List<BatteryStatusListResponse> selectedSensorLists) {
        mBatteryStatusListResponseList= selectedSensorLists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return mBatteryStatusListResponseList == null ? 0 : mBatteryStatusListResponseList .size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView  mTextViewALarmType, mTextViewAlarmDate, mTextViewAlarmTime, mTextViewAlarmDate_orginal;

        public ImageView mImageVieAlarIcon;

       // public ImageView cityImage;



        public ViewHolder(View itemView) {
            super(itemView);

            mTextViewALarmType = (TextView) itemView.findViewById(R.id.cardview_list_unseen_alarm_type);

            mTextViewAlarmDate = (TextView) itemView.findViewById(R.id.cardview_list_unseen_date);

            mTextViewAlarmTime = itemView.findViewById(R.id.cardview_list_unseen_time);


            mImageVieAlarIcon =itemView.findViewById(R.id.cardview_list_alarm_type_image);

            mTextViewAlarmDate_orginal = itemView.findViewById(R.id.cardview_list_unseen_date_orginal);

          //  mTextViewSensorTypeCount = itemView.findViewById(R.id.text_view_sensor_type_cout_recyc);


        }

        @Override
        public void onClick(View view) {

        }
    }
}
