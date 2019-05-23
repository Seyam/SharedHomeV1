package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.content.Context;
import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.AddNewSensorResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.model.AddNewSensorResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRemoveSensorController implements Callback<AddNewSensorResponse> {


    AddNewSensorResponseListener mAddNewSensorResponseListener = null;

    MyPreferences mMyPreferences;

    Context mContext;

    public AddRemoveSensorController(Context context, AddNewSensorResponseListener addNewSensorResponseListener){

        mContext =context;

        mAddNewSensorResponseListener = addNewSensorResponseListener;
        mMyPreferences = MyPreferences.getPreferences(context);

    }


    public  void start(String user_id, String alarm_id) {


        Call<AddNewSensorResponse> call = RetrofitClient
                .getInstance()
                .getApi().add_new_sensor(user_id, alarm_id);

        call.enqueue(this);

    }

    public  void start_remove(String user_id, String alarm_id) {


        Call<AddNewSensorResponse> call = RetrofitClient
                .getInstance()
                .getApi().remove_sensor(user_id, alarm_id);

        call.enqueue(this);

    }





    @Override
    public void onResponse(Call<AddNewSensorResponse> call, Response<AddNewSensorResponse> response) {

        if(response.body().getStatus().equals("200")){



            mAddNewSensorResponseListener.responseSuccessful(response.body().getMessage());

            Log.e("res succes", "success");
       //     mBaseResponseListener.loginResponseSuccessful(response.body().getMessage());

        }

        if(response.body().getStatus().equals("201")){


            mAddNewSensorResponseListener.responseUnsuccessful(response.body().getMessage());

        }
    }

    @Override
    public void onFailure(Call<AddNewSensorResponse> call, Throwable t) {

        mAddNewSensorResponseListener.responseUnsuccessful("Server Error");

    }
}
