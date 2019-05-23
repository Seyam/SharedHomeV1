package com.datasoft_iot.tausif.sharedhomev1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.model.ArmHistoryResponse;

import java.util.List;


public class ArmHistoryAdapter extends RecyclerView.Adapter<ArmHistoryAdapter.ViewHolder>{

    private List<ArmHistoryResponse> armHistoryResponses;

    private Context context;

    public ArmHistoryAdapter(Context context, List<ArmHistoryResponse> armHistory) {
        this.context = context;
        this.armHistoryResponses=armHistory;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.arm_history_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {



        Log.e("arh_history","--------"+ armHistoryResponses.get(position).getAlarm_name());

        viewHolder.alarm_name.setText(armHistoryResponses.get(position).getAlarm_name());

        if(armHistoryResponses.get(position).getWarm_status().equals("Disarm")){

            viewHolder.armStatus.setText("Deactivated");
        }else {

            viewHolder.armStatus.setText("Activated");
        }

        viewHolder.armDate.setText(armHistoryResponses.get(position).getArm_date());
        viewHolder.armTime.setText(armHistoryResponses.get(position).getArm_time());

    }

    public void addData(List<ArmHistoryResponse> armHistoryList) {
        armHistoryResponses= armHistoryList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return armHistoryResponses == null ? 0 : armHistoryResponses .size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView  alarm_name, armStatus, armDate, armTime;


        public ViewHolder(View itemView) {
            super(itemView);

            alarm_name = (TextView) itemView.findViewById(R.id.text_view_alarm_name);
            armStatus = (TextView) itemView.findViewById(R.id.textView_armStatus);
            armDate = (TextView) itemView.findViewById(R.id.textViewDate);
            armTime = itemView.findViewById(R.id.textViewTime);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
