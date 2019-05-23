package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.content.Context;

import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.ArmHistoryListResponseByDateListener;
import com.datasoft_iot.tausif.sharedhomev1.model.ArmHistoryResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArmListRequestByDateController implements Callback<List<ArmHistoryResponse>> {


    ArmHistoryListResponseByDateListener mArmHistoryListResponseListener = null;

    MyPreferences mMyPreferences;

    Context mContext;

    public ArmListRequestByDateController(Context context, ArmHistoryListResponseByDateListener armHistoryListResponseListener) {

        mContext = context;
        mArmHistoryListResponseListener = armHistoryListResponseListener;
        mMyPreferences = MyPreferences.getPreferences(context);

    }

    public void start(String user_id,String from_date, String toDate) {
        Call<List<ArmHistoryResponse>>call = RetrofitClient
                .getInstance()
                .getApi().get_arm_historyByeDate(user_id,from_date,toDate);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<ArmHistoryResponse>>call, Response <List<ArmHistoryResponse>> response) {

        if (response.body()== null) {
            mArmHistoryListResponseListener.armHistoryListResponseByDateUnsuccessful("Empty Arm List");
        } else {
            mArmHistoryListResponseListener.armHistoryListResponseByDateSuccessful(response.body());

        }


    }

    @Override
    public void onFailure(Call<List<ArmHistoryResponse>> call, Throwable t) {
        mArmHistoryListResponseListener.armHistoryListResponseByDateUnsuccessful("Server Error");
    }
}
