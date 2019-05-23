package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.util.Log;


import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.AlarmedDataResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.model.AlarmedData;
import com.datasoft_iot.tausif.sharedhomev1.model.AlarmedDataResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendAlarmedDataController implements Callback<AlarmedDataResponse> {


    AlarmedDataResponseListener mAlarmedDataResponseListener = null;

    public SendAlarmedDataController(AlarmedDataResponseListener alarmedDataResponseListener){

        mAlarmedDataResponseListener = alarmedDataResponseListener;

    }


    public  void start(AlarmedData alarmedData) {


        Call<AlarmedDataResponse> call = RetrofitClient
                .getInstance()
                .getApi().createOrder(alarmedData);

        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<AlarmedDataResponse> call, Response<AlarmedDataResponse> response) {

        if(response.body().getStatus().equals("200")){


            Log.e("res succes", "success");
            mAlarmedDataResponseListener.alarmedDataResponseSuccessful(response.body().getMessage());

        }

        if(response.body().getStatus().equals("403")){


            Log.e("res succes", "failed");

            mAlarmedDataResponseListener.alarmedDataResponseUnsuccessful(response.body().getMessage());

        }
    }

    @Override
    public void onFailure(Call<AlarmedDataResponse> call, Throwable t) {

        mAlarmedDataResponseListener.alarmedDataResponseUnsuccessful("Server Error");

    }
}
