package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.content.Context;
import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.DetectorControlCodeResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.model.DetectorControlCodeResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetectorControlCodeController implements Callback<DetectorControlCodeResponse> {


    DetectorControlCodeResponseListener mDetectorControlCodeResponseListener = null;

    MyPreferences mMyPreferences;

    Context mContext;

    public DetectorControlCodeController(Context context, DetectorControlCodeResponseListener detectorControlCodeResponseListener){

        mContext =context;

        mDetectorControlCodeResponseListener = detectorControlCodeResponseListener;
        mMyPreferences = MyPreferences.getPreferences(context);

    }


    public  void start(String user_id,String alarm_id, String status) {


        Call<DetectorControlCodeResponse> call = RetrofitClient
                .getInstance()
                .getApi().get_detector_control_code(user_id,alarm_id,status);

        call.enqueue(this);

    }





    @Override
    public void onResponse(Call<DetectorControlCodeResponse> call, Response<DetectorControlCodeResponse> response) {

        if(response.body().getStatus().equals("200")){

//            mMyPreferences.setUserId(response.body().getUser_id());
//            mMyPreferences.setLoginStatus(true);
//
//            Log.e("res succes", "success");


            mDetectorControlCodeResponseListener.detectorControlCodeSuccessful(response.body().getMessage());

        }

        if(response.body().getStatus().equals("203")){


            Log.e("res succes", "failed");

            mDetectorControlCodeResponseListener.detectorControlCodeUnsuccessful(response.body().getMessage());

        }


        if(response.body().getStatus().equals("400")){


            Log.e("res succes", "failed");

            mDetectorControlCodeResponseListener.detectorControlCodeSuccessful(response.body().getMessage());

        }
    }

    @Override
    public void onFailure(Call<DetectorControlCodeResponse> call, Throwable t) {

        mDetectorControlCodeResponseListener.detectorControlCodeUnsuccessful("Server Error");

    }
}
