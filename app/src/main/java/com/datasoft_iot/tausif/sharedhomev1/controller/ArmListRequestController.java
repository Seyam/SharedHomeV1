package com.datasoft_iot.tausif.sharedhomev1.controller;

import android.content.Context;

import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.ArmHistoryListResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.model.ArmHistoryResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.RetrofitClient;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArmListRequestController implements Callback<List<ArmHistoryResponse>> {


    ArmHistoryListResponseListener mArmHistoryListResponseListener = null;

    MyPreferences mMyPreferences;

    Context mContext;

    public ArmListRequestController(Context context,ArmHistoryListResponseListener armHistoryListResponseListener) {

        mContext = context;
        mArmHistoryListResponseListener = armHistoryListResponseListener;
        mMyPreferences = MyPreferences.getPreferences(context);

    }


    public void start(String user_id) {
        Call<List<ArmHistoryResponse>>call = RetrofitClient
                .getInstance()
                .getApi().get_arm_history(user_id);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<ArmHistoryResponse>>call, Response <List<ArmHistoryResponse>> response) {

        if (response.body()== null) {
            mArmHistoryListResponseListener.armHistoryResponseUnsuccessful("Empty Arm List");
        } else {
            mArmHistoryListResponseListener.armHistoryListResponseSuccessful(response.body());

        }


    }

    @Override
    public void onFailure(Call<List<ArmHistoryResponse>> call, Throwable t) {
        mArmHistoryListResponseListener.armHistoryResponseUnsuccessful("Server Error");
    }
}
