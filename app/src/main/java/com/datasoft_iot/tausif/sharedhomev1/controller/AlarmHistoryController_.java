package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.content.Context;
import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.AlarmListenerInterface;
import com.datasoft_iot.tausif.sharedhomev1.model.AlarmHistoryResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;


//Get ALl alarm type data
public class AlarmHistoryController_ implements Callback<List<AlarmHistoryResponse>> {
    AlarmListenerInterface alarmListResponseListener = null;

    MyPreferences mMyPreferences;

    Context mContext;

    public AlarmHistoryController_(Context context, AlarmListenerInterface alarmListResponseListener) {

        mContext = context;
        this.alarmListResponseListener = alarmListResponseListener;
        mMyPreferences = MyPreferences.getPreferences(context);

    }


    public void start(String user_id) {
        Call<List<AlarmHistoryResponse>> call = RetrofitClient
                .getInstance()
                .getApi().get_alarm_history(user_id);
        call.enqueue(this);
    }


    public void start(String user_id,String alarm_id) {
             Call<List<AlarmHistoryResponse>> call = RetrofitClient
                .getInstance()
                .getApi().get_alarm_history_(user_id,alarm_id);
        call.enqueue(this);
    }

    public void start(String user_id,String startDate, String endDate ) {
        Call<List<AlarmHistoryResponse>> call = RetrofitClient
                .getInstance()
                .getApi().get_alarm_history_byDate(user_id,startDate,endDate);
        call.enqueue(this);
    }

    public void start(String user_id, String alarm_id, String startDate, String endDate ) {
        Call<List<AlarmHistoryResponse>> call = RetrofitClient
                .getInstance()
                .getApi().get_alarm_history_by_date(user_id,alarm_id, startDate,endDate);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<AlarmHistoryResponse>> call, Response<List<AlarmHistoryResponse>> response) {

        Log.e("--alarmH--","---entered-n-"+response.body());
        if (response.body()== null) {
            alarmListResponseListener.alarmListenerInterfaceUnsuccessful("Empty Arm List");
        } else {
            alarmListResponseListener.alarmListenerInterfaceSuccessful(response.body());

        }
    }

    @Override
    public void onFailure(Call<List<AlarmHistoryResponse>> call, Throwable t) {
        Log.e("--alarmH--","---entered---fail-");
        alarmListResponseListener.alarmListenerInterfaceUnsuccessful("Server Error");
    }


//    @Override
//    public void onResponse(Call<List<AlarmHistoryResponse>> call, Response<List<AlarmHistoryResponse>> response) {
//        Log.e("--alarmH--","---entered-n-"+response.body());
//
//        if (response.body()== null) {
//            Log.e("--alarmH--","---entered-null-"+response.body());
//
//            alarmListResponseListener.alarmListenerInterfaceUnsuccessful("Empty Arm List");
//        } else {
//            Log.e("--alarmH--","---entered--"+response.body());
//            alarmListResponseListener.alarmListenerInterfaceSuccessful(response.body());
//
//        }
//    }
//
//    @Override
//    public void onFailure(Call<List<AlarmHistoryResponse>> call, Throwable t) {
//        alarmListResponseListener.alarmListenerInterfaceUnsuccessful("Server Error");
//
//    }
}
