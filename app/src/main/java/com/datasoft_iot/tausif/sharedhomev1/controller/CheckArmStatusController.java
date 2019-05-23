package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.content.Context;
import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.ArmStatusResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.model.CheckArmResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckArmStatusController implements Callback<CheckArmResponse> {


    ArmStatusResponseListener mArmStatusResponseListener ;

    MyPreferences mMyPreferences;

    Context mContext;

    public CheckArmStatusController(Context context, ArmStatusResponseListener mArmStatusResponseListener){

        mContext =context;

       this.mArmStatusResponseListener = mArmStatusResponseListener;
        mMyPreferences = MyPreferences.getPreferences(context);

    }


    public  void start(String user_id) {


        Call<CheckArmResponse> call = RetrofitClient
                .getInstance()
                .getApi().get_arm_status(user_id);

        call.enqueue(this);

    }





    @Override
    public void onResponse(Call<CheckArmResponse> call, Response<CheckArmResponse> response) {

        if(response.body().getStatus().equals("200")){

//            mMyPreferences.setUserId(response.body().getUser_id());
//            mMyPreferences.setLoginStatus(true);
//
//            Log.e("res succes", "success");


            mArmStatusResponseListener.armStatusResponseSuccessful(response.body().getMessage());

        }

        if(response.body().getStatus().equals("203")){


            Log.e("res succes", "failed");

            mArmStatusResponseListener.armStatusResponseUnsuccessful(response.body().getMessage());

        }


        if(response.body().getStatus().equals("202")){


            Log.e("res succes", "failed");

            mArmStatusResponseListener.armStatusResponseUnsuccessful(response.body().getMessage());

        }
    }

    @Override
    public void onFailure(Call<CheckArmResponse> call, Throwable t) {

        mArmStatusResponseListener.armStatusResponseSuccessful("Server Error");

    }
}
