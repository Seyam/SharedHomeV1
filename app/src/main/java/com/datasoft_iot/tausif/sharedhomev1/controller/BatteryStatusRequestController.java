package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.content.Context;
import android.util.Log;


import com.datasoft_iot.tausif.sharedhomev1.model.BatteryStatusResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BatteryStatusRequestController implements Callback <BatteryStatusResponse>{




    Context mContext;

    public BatteryStatusRequestController(Context context) {

        mContext = context;

    }


    public void start(String host_number, String battery_status) {


        Call<BatteryStatusResponse>call = RetrofitClient
                .getInstance()
                .getApi().sendBatteryStatus(host_number, battery_status);

        call.enqueue(this);

    }


    @Override
    public void onResponse(Call<BatteryStatusResponse>call, Response<BatteryStatusResponse> response) {





        if (response.body()== null) {


           // mFCMTokenResponseListener.assignedHostResponseSuccessful(response.body().getMessage());

        } else {

            Log.e("BATTERY ", "Success");
           // mUserDetailsListResponseListener.responseSuccessful(response.body());

            //mFCMTokenResponseListener.assignedHostResponseSuccessful(response.body().getMessage());


        }


    }

    @Override
    public void onFailure(Call<BatteryStatusResponse>call, Throwable t) {

     //   mAlarmListResponseListener.responseUnsuccessful("Server Error");

    }
}
