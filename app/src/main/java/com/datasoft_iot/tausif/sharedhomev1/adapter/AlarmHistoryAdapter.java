package com.datasoft_iot.tausif.sharedhomev1.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.model.AlarmHistoryResponse;

import java.util.List;


public class AlarmHistoryAdapter extends RecyclerView.Adapter<AlarmHistoryAdapter.ViewHolder>{

    private List<AlarmHistoryResponse> mAlarmHistoryResponse;
    private Context context;

    public AlarmHistoryAdapter(Context context,  List<AlarmHistoryResponse> alarmHistory) {
        this.context = context;
        this.mAlarmHistoryResponse = alarmHistory;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_alarm_history, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Log.e("alarm_history","--------"+ mAlarmHistoryResponse.get(position).getRoomNo());

      //  viewHolder.mTextViewRoomName.setText(mAlarmHistoryResponse.get(position).getRoomNo());
        viewHolder.mTextViewAlarmName.setText(mAlarmHistoryResponse.get(position).getAlarmName());
        viewHolder.mTextViewAlarmDate.setText(mAlarmHistoryResponse.get(position).getAlarmDate());
        viewHolder.mTextViewAlarmTime.setText(mAlarmHistoryResponse.get(position).getAlarmTime());

    }

    public void addData(List<AlarmHistoryResponse> mAlarmHistoryResponse) {
        this.mAlarmHistoryResponse= mAlarmHistoryResponse;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return mAlarmHistoryResponse == null ? 0 : mAlarmHistoryResponse .size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public TextView  mTextViewRoomName,mTextViewAlarmName, mTextViewAlarmDate, mTextViewAlarmTime;

        public ViewHolder(View itemView) {
            super(itemView);

          //  mTextViewRoomName = (TextView) itemView.findViewById(R.id.textView_roomName);
            mTextViewAlarmName= (TextView) itemView.findViewById(R.id.textView_alarmName);
            mTextViewAlarmDate = (TextView) itemView.findViewById(R.id.textViewDate_alarm);
            mTextViewAlarmTime = (TextView) itemView.findViewById(R.id.textViewTime_alarm);
        }

        @Override
        public void onClick(View view) {




        }
    }
}
