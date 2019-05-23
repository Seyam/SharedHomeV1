package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.content.Context;
import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.SensorHistoryInterface;
import com.datasoft_iot.tausif.sharedhomev1.model.SensorHistoryResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SensorHistoryController implements Callback<List<SensorHistoryResponse>> {
    SensorHistoryInterface sensorHistoryInterface = null;
    MyPreferences mMyPreferences;
    Context mContext;

    public SensorHistoryController(Context context, SensorHistoryInterface sensorHistoryInterface) {

        mContext = context;
        this.sensorHistoryInterface = sensorHistoryInterface;
        mMyPreferences = MyPreferences.getPreferences(context);

    }


    public void start(String user_id) {
             Call<List<SensorHistoryResponse>> call = RetrofitClient
                .getInstance()
                .getApi().get_remove_adding_history(user_id);
        call.enqueue(this);
    }



    @Override
    public void onResponse(Call<List<SensorHistoryResponse>> call, Response<List<SensorHistoryResponse>> response) {
        if (response.body()== null) {
            sensorHistoryInterface.sensorHistoryInterfaceUnsuccessful("Empty Arm List");
        } else {
            sensorHistoryInterface.sensorHistoryInterfaceSuccessful(response.body());

        }
    }

    @Override
    public void onFailure(Call<List<SensorHistoryResponse>> call, Throwable t) {
        Log.e("--alarmH--","---entered---fail-");
        sensorHistoryInterface.sensorHistoryInterfaceUnsuccessful("Server Error");
    }
}
