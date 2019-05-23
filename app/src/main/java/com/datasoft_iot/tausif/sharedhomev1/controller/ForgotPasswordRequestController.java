package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.content.Context;
import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.ForgotPasswordResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.model.ForgotPasswordResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordRequestController implements Callback<ForgotPasswordResponse> {


    ForgotPasswordResponseListener mForgotPasswordResponseListener;
    MyPreferences mMyPreferences;

    Context mContext;

    public ForgotPasswordRequestController(Context context, ForgotPasswordResponseListener forgotPasswordResponseListener){

        mContext =context;

        mForgotPasswordResponseListener = forgotPasswordResponseListener;
        mMyPreferences = MyPreferences.getPreferences(context);

    }


    public  void start(String mobile_number) {


        Call<ForgotPasswordResponse> call = RetrofitClient
                .getInstance()
                .getApi().send_forgot_password(mobile_number);
        call.enqueue(this);

    }





    @Override
    public void onResponse(Call<ForgotPasswordResponse> call, Response<ForgotPasswordResponse> response) {

        if(response.body().getStatus().equals("200")){

           Log.e("res succes", "success");
            mForgotPasswordResponseListener.forgotPasswordResponseSuccessful(response.body().getMessage());

        }

        if(response.body().getStatus().equals("201")){

            Log.e("res succes", "success");
            mForgotPasswordResponseListener.forgotPasswordUnsuccessful(response.body().getMessage());

        }

//        if(response.body().getStatus().equals("203")){
//
//
//            Log.e("res succes", "failed");
//
//            mBaseResponseListener.loginResponseUnsuccessful(response.body().getMessage());
//
//        }
//
//
//        if(response.body().getStatus().equals("202")){
//
//
//            Log.e("res success", "failed");
//            mBaseResponseListener.loginResponseUnsuccessful(response.body().getMessage());
//
//        }
    }

    @Override
    public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {

        mForgotPasswordResponseListener.forgotPasswordUnsuccessful("Server Error");

    }
}
