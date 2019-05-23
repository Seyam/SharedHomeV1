package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.content.Context;
import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.BatteryStatusListResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.model.BatteryStatusListResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BatteryStatusListRequestController implements Callback<List<BatteryStatusListResponse>>{


    BatteryStatusListResponseListener mBatteryStatusListResponseListener= null;

    MyPreferences mMyPreferences;

    Context mContext;

    public BatteryStatusListRequestController(Context context, BatteryStatusListResponseListener batteryStatusListResponseListener ) {

        mContext = context;
        mBatteryStatusListResponseListener = batteryStatusListResponseListener;
        mMyPreferences = MyPreferences.getPreferences(context);

    }


    public void start(String user_id) {


        Call<List<BatteryStatusListResponse>> call = RetrofitClient
                .getInstance()
                .getApi().get_battery_status(user_id);

        call.enqueue(this);

    }


    @Override
    public void onResponse(Call<List<BatteryStatusListResponse>>call, Response<List <BatteryStatusListResponse> >response) {


        if (response.body()== null) {

            mBatteryStatusListResponseListener.batteryListResponseUnsuccessful("Empty Detector");

        } else {

            mBatteryStatusListResponseListener.batteyStatusListResponseSuccessful(response.body());
            Log.e("tag", String.valueOf(response.body()));

        }


    }

    @Override
    public void onFailure(Call<List<BatteryStatusListResponse> >call, Throwable t) {

        mBatteryStatusListResponseListener.batteryListResponseUnsuccessful("Server Error");

    }
}
