package com.datasoft_iot.tausif.sharedhomev1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.OnSwitchClickListener;
import com.datasoft_iot.tausif.sharedhomev1.model.DetectorControlList;

import java.util.List;

import com.suke.widget.SwitchButton;


public class DetectorControlAdapter extends RecyclerView.Adapter<DetectorControlAdapter.ViewHolder>{

    private List<DetectorControlList> mDetectorControlLists;
    private Context context;

    private OnSwitchClickListener mOnSwitchClickListener;


    public DetectorControlAdapter(Context context, OnSwitchClickListener onSwitchClickListener) {
        this.context = context;
    //    this.mDetectorControlLists = sensorHistory;
        mOnSwitchClickListener = onSwitchClickListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_show_alarm_control_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
//        Log.e("alarm_history","--------"+ sensorHistoryResponses.get(position).getAlarmname());
//
//        viewHolder.mAdd.setText(sensorHistoryResponses.get(position).getStatus());
        viewHolder.mTextViewAlarmName.setText(mDetectorControlLists.get(position).getAlarmname());
//        viewHolder.mTextViewAlarmDate.setText(sensorHistoryResponses.get(position).getDate());


        if(mDetectorControlLists.get(position).getWarm_status().equals("1") ){
            viewHolder.switchButton.setChecked(true);
        }
        else{
            viewHolder.switchButton.setChecked(false);
            }

        viewHolder.switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {


                String alarm_id = mDetectorControlLists.get(position).getAlarmId();

                String arm_status = mDetectorControlLists.get(position).getWarm_status();


                //Toast.makeText(context, articles.get(position).getAuthor(), Toast.LENGTH_SHORT).show();

                Log.e("Result", alarm_id+ " "+arm_status);

                if(arm_status.equals("1")){
                    arm_status = "0";
                }else{
                    arm_status = "1";
                }

                mOnSwitchClickListener.onSwitchClick(alarm_id,arm_status);
            }
        });

    }

    public void addData(List<DetectorControlList> mAlarmHistoryResponse) {
        this.mDetectorControlLists= mAlarmHistoryResponse;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return mDetectorControlLists == null ? 0 : mDetectorControlLists .size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView mTextViewAlarmName;

        com.suke.widget.SwitchButton switchButton;




        public ViewHolder(View itemView) {
            super(itemView);


            switchButton = (com.suke.widget.SwitchButton)itemView.findViewById(R.id.switch_button_sensor_control);
            mTextViewAlarmName= (TextView) itemView.findViewById(R.id.text_view_control_sensor_type);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
