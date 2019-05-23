package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.content.Context;
import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.LoginResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.model.LoginResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRequestController implements Callback<LoginResponse> {


    LoginResponseListener mBaseResponseListener = null;

    MyPreferences mMyPreferences;

    Context mContext;

    public LoginRequestController(Context context,LoginResponseListener baseResponseListener){

        mContext =context;

        mBaseResponseListener = baseResponseListener;
        mMyPreferences = MyPreferences.getPreferences(context);

    }


    public  void start(String mobile_number, String password) {


        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi().check_login(mobile_number, password);

        call.enqueue(this);

    }





    @Override
    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

        if(response.body().getStatus().equals("200") ){


            Log.e("name", response.body().getName());

            //Log.e(" host number", response.body().getHostnumber());
            Log.e(" mobile number", response.body().getMobile_number());


            mMyPreferences.setUserId(response.body().getUser_id());
            mMyPreferences.setLoginStatus(true);

            mMyPreferences.setUserName(response.body().getName());

            mMyPreferences.setHostNumber(response.body().getHost_number());

            mMyPreferences.setMobileNumber(response.body().getMobile_number());

       //     mMyPreferences.setHostNumber(response.body().getHostnumber());

            Log.e("res succes", "success");
            mBaseResponseListener.loginResponseSuccessful(response.body().getUser_type());

        }

        if(response.body().getStatus().equals("201")){

            mMyPreferences.setUserId(response.body().getUser_id());
            mMyPreferences.setLoginStatus(true);

            Log.e("res succes", "success");
            mBaseResponseListener.loginResponseUnsuccessful(response.body().getMessage());

        }

        if(response.body().getStatus().equals("203")){


            Log.e("res succes", "failed");

            mBaseResponseListener.loginResponseUnsuccessful(response.body().getMessage());

        }


        if(response.body().getStatus().equals("202")){


            Log.e("res success", "failed");
            mBaseResponseListener.loginResponseUnsuccessful(response.body().getMessage());

        }
    }

    @Override
    public void onFailure(Call<LoginResponse> call, Throwable t) {

        mBaseResponseListener.loginResponseUnsuccessful("Server Error");

    }
}
