package com.datasoft_iot.tausif.sharedhomev1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.model.AlarmListGridView;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;

import java.util.List;


public class ShowAlarmTypeAlarmAdapter extends RecyclerView.Adapter<ShowAlarmTypeAlarmAdapter.ViewHolder>{

    private List<AlarmListGridView> mSpeceficAlarmDataResponses;

    private Context context;

    MyPreferences mMyPreferences;

    public ShowAlarmTypeAlarmAdapter(Context context) {
        this.context = context;
        mMyPreferences = MyPreferences.getPreferences(context);

    }


//    public DeviceListAdapter(List<DeviceInfo> deviceInfos, int rowLayout, Context context) {
//        this.mDeviceInfos = deviceInfos;
//        this.rowLayout = rowLayout;
//       this.mContext = context;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.grid_single, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {



       Log.e("in adpater", mSpeceficAlarmDataResponses.get(position).getSensorType());


        viewHolder.tvApplianceName.setText(mSpeceficAlarmDataResponses.get(position).getSensorType());

        viewHolder.mImageVieAlarIcon.setImageResource(mSpeceficAlarmDataResponses.get(position).getSensorImage());




    }

    public void addData(List<AlarmListGridView> selectedSensorLists) {
        mSpeceficAlarmDataResponses = selectedSensorLists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return mSpeceficAlarmDataResponses == null ? 0 : mSpeceficAlarmDataResponses .size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView tvApplianceName;

        public ImageView mImageVieAlarIcon;

       // public ImageView cityImage;



        public ViewHolder(View itemView) {
            super(itemView);

//            mTextViewALarmName = (TextView) itemView.findViewById(R.id.grid_sensor_name);

            tvApplianceName = (TextView) itemView.findViewById(R.id.tvApplianceName);

            mImageVieAlarIcon =itemView.findViewById(R.id.grid_image);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
//                    final String alarm_id = mSpeceficAlarmDataResponses.get(position).getSensorAlarm();
                    Toast.makeText(context, mSpeceficAlarmDataResponses.get(position).getSensorType()+" not connected!", Toast.LENGTH_SHORT).show();
                }
            });


        }

        @Override
        public void onClick(View view) {


        }
    }
}
