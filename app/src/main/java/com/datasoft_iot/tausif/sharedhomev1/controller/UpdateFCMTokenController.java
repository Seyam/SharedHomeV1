package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.content.Context;
import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.FCMUpdateResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.model.UpdateFCMResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateFCMTokenController implements Callback<UpdateFCMResponse> {


    FCMUpdateResponseListener mFCMUpdateResponseListener = null;

    MyPreferences mMyPreferences;

    Context mContext;

    public UpdateFCMTokenController(Context context, FCMUpdateResponseListener mFCMUpdateResponseListener){

        mContext =context;

        this.mFCMUpdateResponseListener = mFCMUpdateResponseListener;
        mMyPreferences = MyPreferences.getPreferences(context);

    }


    public  void start(String user_id, String fcmToken) {


        Call<UpdateFCMResponse> call = RetrofitClient
                .getInstance()
                .getApi().update_fcm(user_id, fcmToken);

        call.enqueue(this);

    }





    @Override
    public void onResponse(Call<UpdateFCMResponse> call, Response<UpdateFCMResponse> response) {

        if(response.body().getStatus().equals("200")){

//            mMyPreferences.setUserId(response.body().getUser_id());
//            mMyPreferences.setLoginStatus(true);

            Log.e("res succes", "success");
            mFCMUpdateResponseListener.fCMUpdateResponseListenerSuccessful(response.body().getMessage());

        }

        if(response.body().getStatus().equals("203")){

            Log.e("res succes", "failed");

        }


        if(response.body().getStatus().equals("202")){


            Log.e("res succes", "failed");

            mFCMUpdateResponseListener.FCMUpdateResponseListenerUnSuccessful(response.body().getMessage());

          //  mBaseResponseListener.responseUnsuccessful(response.body().getMessage());

        }
    }

    @Override
    public void onFailure(Call<UpdateFCMResponse> call, Throwable t) {


        mFCMUpdateResponseListener.FCMUpdateResponseListenerUnSuccessful("Server Error");

    }
}
