package com.datasoft_iot.tausif.sharedhomev1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.model.SensorHistoryResponse;

import java.util.List;


public class SensorHistoryAdapter extends RecyclerView.Adapter<SensorHistoryAdapter.ViewHolder>{

    private List<SensorHistoryResponse> sensorHistoryResponses;
    private Context context;

    public SensorHistoryAdapter(Context context, List<SensorHistoryResponse> sensorHistory) {
        this.context = context;
        this.sensorHistoryResponses = sensorHistory;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_sensor_history, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Log.e("alarm_history","--------"+ sensorHistoryResponses.get(position).getAlarmname());

        viewHolder.mAdd.setText(sensorHistoryResponses.get(position).getStatus());
        viewHolder.mTextViewAlarmName.setText(sensorHistoryResponses.get(position).getAlarmname());
        viewHolder.mTextViewAlarmDate.setText(sensorHistoryResponses.get(position).getDate());

    }

    public void addData(List<SensorHistoryResponse> mAlarmHistoryResponse) {
        this.sensorHistoryResponses= mAlarmHistoryResponse;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return sensorHistoryResponses == null ? 0 : sensorHistoryResponses .size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView  mAdd,mTextViewAlarmName, mTextViewAlarmDate;

        public ViewHolder(View itemView) {
            super(itemView);

            mAdd = (TextView) itemView.findViewById(R.id.textView_sensorStatus);
            mTextViewAlarmName= (TextView) itemView.findViewById(R.id.textView_sensorName);
            mTextViewAlarmDate = (TextView) itemView.findViewById(R.id.textViewDate_sensor);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
