package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.content.Context;
import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.AlarmListResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.model.AlarmListResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlarmListRequestController implements Callback<AlarmListResponse>{


    AlarmListResponseListener mAlarmListResponseListener = null;

    MyPreferences mMyPreferences;

    Context mContext;

    public AlarmListRequestController(Context context, AlarmListResponseListener alarmListResponseListener) {

        mContext = context;
        mAlarmListResponseListener = alarmListResponseListener;
        mMyPreferences = MyPreferences.getPreferences(context);

    }


    public void start(String user_id) {


        Call<AlarmListResponse>call = RetrofitClient
                .getInstance()
                .getApi().get_alarm_list(user_id);

        call.enqueue(this);

    }


    @Override
    public void onResponse(Call<AlarmListResponse>call, Response <AlarmListResponse> response) {



        if (response.body()== null) {

            mAlarmListResponseListener.alarmListResponseUnsuccessful("Empty Detector");

        } else {

         mAlarmListResponseListener.alarmListResponseSuccessful(response.body().getAlarmList());
            Log.e("tag", String.valueOf(response.body().getAlarmList()));

        }


    }

    @Override
    public void onFailure(Call<AlarmListResponse> call, Throwable t) {

        mAlarmListResponseListener.alarmListResponseUnsuccessful("Server Error");

    }
}
