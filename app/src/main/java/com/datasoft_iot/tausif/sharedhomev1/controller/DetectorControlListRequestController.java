package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.content.Context;
import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.DetectorControlListResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.model.DetectorControlListResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetectorControlListRequestController implements Callback<DetectorControlListResponse>{


    DetectorControlListResponseListener mDetectorControlListResponseListener = null;

    MyPreferences mMyPreferences;

    Context mContext;


    public DetectorControlListRequestController(Context context, DetectorControlListResponseListener detectorControlListResponseListener) {

        mContext = context;
        mDetectorControlListResponseListener = detectorControlListResponseListener;
        mMyPreferences = MyPreferences.getPreferences(context);

    }


    public void start(String user_id) {


        Call<DetectorControlListResponse>call = RetrofitClient
                .getInstance()
                .getApi().get_detector_control_list(user_id);

        call.enqueue(this);

    }


    @Override
    public void onResponse(Call<DetectorControlListResponse>call, Response <DetectorControlListResponse> response) {



        if (response.body()== null) {

            mDetectorControlListResponseListener.detectorAlarmListResponseUnsuccessful("Empty Detector");

        } else {

            mDetectorControlListResponseListener.detectorAlarmListResponseSuccessful(response.body().getAlarmList());
            Log.e("tag", String.valueOf(response.body().getAlarmList()));

        }


    }

    @Override
    public void onFailure(Call<DetectorControlListResponse> call, Throwable t) {

        mDetectorControlListResponseListener.detectorAlarmListResponseUnsuccessful("Server Error");

    }
}
