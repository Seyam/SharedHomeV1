package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.ArmResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.model.BaseResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArmStatusController implements Callback<BaseResponse> {


    ArmResponseListener mBaseResponseListener = null;

    MyPreferences mMyPreferences;

   // Context mContext;

    public ArmStatusController(ArmResponseListener armResponseListener){

      //  mContext =context;

        mBaseResponseListener = armResponseListener;
      // mMyPreferences = MyPreferences.getPreferences(context);

    }


    public  void start(String user_id, String alarm_id, String arm_status) {


        Call<BaseResponse> call = RetrofitClient
                .getInstance()
                .getApi().send_arm_status_alarm_id(user_id, alarm_id,arm_status);

        call.enqueue(this);

    }





    @Override
    public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

        if(response.body().getStatus().equals("200")){

//            mMyPreferences.setUserId(response.body().getUser_id());
//            mMyPreferences.setLoginStatus(true);
//
//            Log.e("res succes", "success");


           mBaseResponseListener.armResponseSuccessful(response.body().getMessage());

        }

        if(response.body().getStatus().equals("203")){


            Log.e("res succes", "failed");

            mBaseResponseListener.armResponseUnsuccessful(response.body().getMessage());

        }


        if(response.body().getStatus().equals("202")){


            Log.e("res succes", "failed");

            mBaseResponseListener.armResponseUnsuccessful(response.body().getMessage());

        }
    }

    @Override
    public void onFailure(Call<BaseResponse> call, Throwable t) {

        mBaseResponseListener.armResponseUnsuccessful("Server Error");

    }
}
