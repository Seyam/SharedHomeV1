package com.datasoft_iot.tausif.sharedhomev1.controller;

//import android.content.Context;
//
//import com.datasoft_iot.tausif.smartalarmv2.callbackinterface.SpeceficTypeAlarmListener;
//import com.datasoft_iot.tausif.smartalarmv2.model.SpeceficAlarmDataResponse;
//import com.datasoft_iot.tausif.smartalarmv2.network.RetrofitClient;
//import com.datasoft_iot.tausif.smartalarmv2.sharedpreferance.MyPreferences;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class SpeceficAlarmDartaRequestController implements Callback<List<SpeceficAlarmDataResponse>> {
//
//
//    SpeceficTypeAlarmListener mSpeceficTypeAlarmListener;
//
//
//    MyPreferences mMyPreferences;
//
//    Context mContext;
//
//    public SpeceficAlarmDartaRequestController(Context context, SpeceficTypeAlarmListener mAlarmListResponseListener) {
//
//        mContext = context;
//        this.mSpeceficTypeAlarmListener = mAlarmListResponseListener;
//        mMyPreferences = MyPreferences.getPreferences(context);
//
//    }
//
//
//    public void start(String user_id, String alarm_id) {
//
//
//        Call<List<SpeceficAlarmDataResponse>> call = RetrofitClient
//                .getInstance()
//                .getApi().get_specefic_alarm_data(user_id, alarm_id);
//
//        call.enqueue(this);
//
//    }
//
//
//    @Override
//    public void onResponse(Call<List<SpeceficAlarmDataResponse>> call, Response<List<SpeceficAlarmDataResponse>> response) {
//
//
//       if(response.body().isEmpty()){
//
//           mSpeceficTypeAlarmListener.responseUnsuccessful("No Data at this moment !");
//
//       }else
//
//       {
//           mSpeceficTypeAlarmListener.responseSuccessful(response.body());
//       }
//    }
//
//    @Override
//    public void onFailure(Call<List<SpeceficAlarmDataResponse>> call, Throwable t) {
//
//        mSpeceficTypeAlarmListener.responseUnsuccessful("Server Error !");
//
//    }
//}
