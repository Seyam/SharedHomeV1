package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.content.Context;
import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.BaseResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.model.RegResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationRequestController implements Callback<RegResponse> {


    BaseResponseListener mBaseResponseListener = null;

    Context mContext;

    MyPreferences mMyPreferences;

    public RegistrationRequestController(Context context ,BaseResponseListener baseResponseListener){

        mContext = context;
        mBaseResponseListener = baseResponseListener;

        mMyPreferences = MyPreferences.getPreferences(context);


    }


    public  void start(String firstName,
                       String lastName,
                       String passoword,
                       String mobile_number,
                       String email,
                       String alarm_id,
                       String qr_id,
                       String fcmToken) {


        Call<RegResponse> call = RetrofitClient
                .getInstance()
                .getApi().insert(firstName, lastName, passoword, mobile_number, email, alarm_id, qr_id, fcmToken);

        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<RegResponse> call, Response<RegResponse> response) {

        if(response.body().getStatus().equals("200")){




            mMyPreferences.setHostNumber(response.body().getHost_number());
            mMyPreferences.setMobileNumber(response.body().getMobile_number());
            Log.e("Host Number", mMyPreferences.getHostNumber());
            mBaseResponseListener.responseSuccessful(response.body().getMessage());

        }

        if(response.body().getStatus().equals("403")){


            Log.e("res succes", "failed");

            mBaseResponseListener.responseUnsuccessful(response.body().getMessage());

        }
    }

    @Override
    public void onFailure(Call<RegResponse> call, Throwable t) {

        mBaseResponseListener.responseUnsuccessful("Server Error");

    }
}
