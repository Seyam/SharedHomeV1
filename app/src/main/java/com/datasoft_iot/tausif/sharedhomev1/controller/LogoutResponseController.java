package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.content.Context;
import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.LogoutResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.model.LogoutResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogoutResponseController implements Callback<LogoutResponse> {


    LogoutResponseListener mLogoutResponseListener = null;

    MyPreferences mMyPreferences;

    Context mContext;

    public LogoutResponseController(Context context, LogoutResponseListener logoutResponseListener){

        mContext =context;

        mLogoutResponseListener = logoutResponseListener;
        mMyPreferences = MyPreferences.getPreferences(context);

    }




    public  void start(String user_id) {


        Call<LogoutResponse> call = RetrofitClient
                .getInstance()
                .getApi().get_logout_status(user_id);

        call.enqueue(this);

    }





    @Override
    public void onResponse(Call<LogoutResponse> call, Response<LogoutResponse> response) {

        if(response.body().getStatus().equals("200")){



            mLogoutResponseListener.logoutResponseSuccessful(response.body().getMessage());

            Log.e("res succes", "success");
       //     mBaseResponseListener.loginResponseSuccessful(response.body().getMessage());

        }

        if(response.body().getStatus().equals("201")){


            mLogoutResponseListener.logoutResponseUnsuccessful(response.body().getMessage());

        }
    }

    @Override
    public void onFailure(Call<LogoutResponse> call, Throwable t) {

        mLogoutResponseListener.logoutResponseSuccessful("Server Error");

    }
}
