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
import com.datasoft_iot.tausif.sharedhomev1.model.UnseenAlarm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class UnseenAlarmHistoryAdapter extends RecyclerView.Adapter<UnseenAlarmHistoryAdapter.ViewHolder>{

    private List<UnseenAlarm> mUnseenAlarms;

    private Context context;

    public UnseenAlarmHistoryAdapter(Context context) {
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
        View view = inflater.inflate(R.layout.item_show_unseen_alarm_data, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


        Date date = new Date();
        //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String strDate= formatter.format(date);
        // System.out.println("Current Date " + strDate);
        Log.e(" Current Date", strDate);


        //stored time in date field and date in time field

        Log.e("Alam Type", mUnseenAlarms.get(position).getAlarmType());

        Log.e("Date", mUnseenAlarms.get(position).getTime());

        Log.e("Time ", mUnseenAlarms.get(position).getDate());


        viewHolder.mTextViewALarmType.setText(mUnseenAlarms.get(position).getAlarmType());

        String date_without_space = mUnseenAlarms.get(position).getTime().replaceAll("\\s","");

        if(strDate.equals(date_without_space)) {
            viewHolder.mTextViewAlarmTime.setText("Today");
            Log.e("Status ", "true");

        }else{
            viewHolder.mTextViewAlarmTime.setText(mUnseenAlarms.get(position).getTime());
            Log.e("Status ", "false");
            Log.e("Date in else ", mUnseenAlarms.get(position).getTime());

        }
        viewHolder.mTextViewAlarmDate.setText(mUnseenAlarms.get(position).getDate());

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

    public void addData(List<UnseenAlarm> selectedSensorLists) {
        mUnseenAlarms= selectedSensorLists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return mUnseenAlarms == null ? 0 : mUnseenAlarms .size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView  mTextViewALarmType, mTextViewAlarmDate, mTextViewAlarmTime;

        public ImageView mImageVieAlarIcon;

       // public ImageView cityImage;



        public ViewHolder(View itemView) {
            super(itemView);

            mTextViewALarmType = (TextView) itemView.findViewById(R.id.cardview_list_unseen_alarm_type);

            mTextViewAlarmDate = (TextView) itemView.findViewById(R.id.cardview_list_unseen_date);

            mTextViewAlarmTime = itemView.findViewById(R.id.cardview_list_unseen_time);


              mImageVieAlarIcon =itemView.findViewById(R.id.cardview_list_alarm_type_image);

          //  mTextViewSensorTypeCount = itemView.findViewById(R.id.text_view_sensor_type_cout_recyc);


        }

        @Override
        public void onClick(View view) {

        }
    }
}
