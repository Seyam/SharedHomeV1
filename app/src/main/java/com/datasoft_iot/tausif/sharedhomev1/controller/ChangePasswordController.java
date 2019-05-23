package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.content.Context;
import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.ChangePasswordResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.model.ChangePasswordResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordController implements Callback<ChangePasswordResponse> {


    ChangePasswordResponseListener mChangePasswordResponseListener = null;

    MyPreferences mMyPreferences;

    Context mContext;

    public ChangePasswordController(Context context, ChangePasswordResponseListener changePasswordResponseListener){

        mContext =context;

        mChangePasswordResponseListener = changePasswordResponseListener;
        mMyPreferences = MyPreferences.getPreferences(context);

    }


    public  void start(String user_id, String update_password, String current_password) {


        Call<ChangePasswordResponse> call = RetrofitClient
                .getInstance()
                .getApi().change_password(user_id,update_password, current_password);

        call.enqueue(this);

    }





    @Override
    public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {

        if(response.body().getStatus().equals("200")){

//            mMyPreferences.setUserId(response.body().getUser_id());
//            mMyPreferences.setLoginStatus(true);
//
//            Log.e("res succes", "success");


            mChangePasswordResponseListener.changePasswordResponseSuccessful(response.body().getMessage());

        }

        if(response.body().getStatus().equals("203")){


            Log.e("res succes", "failed");

            mChangePasswordResponseListener.changePasswordResponseUnsuccessful(response.body().getMessage());

        }


        if(response.body().getStatus().equals("400")){


            Log.e("res succes", "failed");

            mChangePasswordResponseListener.changePasswordResponseUnsuccessful(response.body().getMessage());

        }
    }

    @Override
    public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {

        mChangePasswordResponseListener.changePasswordResponseUnsuccessful("Server Error");

    }
}
