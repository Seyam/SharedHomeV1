package com.datasoft_iot.tausif.sharedhomev1.network;

import com.datasoft_iot.tausif.sharedhomev1.model.AddNewSensorResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.AlarmHistoryResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.AlarmListResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.AlarmedData;
import com.datasoft_iot.tausif.sharedhomev1.model.AlarmedDataResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.ApartmentListResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.ArmHistoryResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.BaseResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.BatteryStatusListResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.BatteryStatusResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.ChangePasswordResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.DetectorControlCodeResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.DetectorControlListResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.ForgotPasswordResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.GasUsageResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.LoginResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.LogoutResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.PowerBreakdownResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.RegResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.RoomAvailabilityResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.RoomInfoResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.SensorHistoryResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.UpdateFCMResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.CheckArmResponse;
import com.datasoft_iot.tausif.sharedhomev1.model.WaterUsageResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

    // qr add korte hobe
    @FormUrlEncoded
    @POST("registration")
    Call<RegResponse> insert(
            @Field("firstname") String firstname,
            @Field("lastname") String lastname,
            @Field("password") String password,
            @Field("mobile_number") String mobile_number,
            @Field("email") String email,
            @Field("alarm_id") String alarm_id,
            @Field("qr_id") String qr_id,
            @Field("FCMToken") String FCMToken
    );


    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> check_login(
            @Field("mobile_number") String mobile_number,
            @Field("password") String password
    );


    @FormUrlEncoded
    @POST("userwise_alarm_name")
    Call<AlarmListResponse> get_alarm_list(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("last_arm_status")
    Call<DetectorControlListResponse> get_detector_control_list(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("update_fcmtoken")
    Call<UpdateFCMResponse> update_fcm(
            @Field("user_id") String user_id,
            @Field("FCMToken") String FCMToken
    );


    @FormUrlEncoded
    @POST("user_arm")
    Call<BaseResponse> send_arm_status(
                    @Field("user_id") String user_id,
                    @Field("warm_status") String arm_status
            );


    @FormUrlEncoded
    @POST("user_arm_alarm_id")
    Call<BaseResponse> send_arm_status_alarm_id(
            @Field("user_id") String user_id,
            @Field("alarm_id") String alarm_id,
            @Field("warm_status") String arm_status
    );


    @FormUrlEncoded
    @POST("check_arm_status")
    Call<CheckArmResponse> get_arm_status(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("add_new_detector")
    Call<AddNewSensorResponse> add_new_sensor(
            @Field("user_id") String user_id,
            @Field("alarm_id") String alarm_id
    );


    @FormUrlEncoded
    @POST("forgot_password")
    Call<ForgotPasswordResponse> send_forgot_password(
            @Field("mobile_number") String mobile_number
    );

    @FormUrlEncoded
    @POST("remove_detector")
    Call<AddNewSensorResponse> remove_sensor(
            @Field("user_id") String user_id,
            @Field("alarm_id") String alarm_id
    );


    @FormUrlEncoded
    @POST("arm_history")
    Call<List<ArmHistoryResponse>> get_arm_history(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("check_qr_status")
    Call<ResponseBody> get_qr_status(
            @Field("qr_id") String qr_id
    );

    @FormUrlEncoded
    @POST("shared_registration")
    Call<ResponseBody> shared_registration(
            @Field("firstname") String fname,
            @Field("lastname") String lname,
            @Field("email") String email,
            @Field("mobile_number") String mobile_number,
            @Field("password") String password,
            @Field("owner_mobile_number") String ownerNumber,
            @Field("FCMToken") String fcm
    );


    @FormUrlEncoded
    @POST("datetodate_arm_history")
    Call<List<ArmHistoryResponse>> get_arm_historyByeDate(
            @Field("user_id") String user_id,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );


    @FormUrlEncoded
    @POST("battery_history")
    Call<List<BatteryStatusListResponse>> get_battery_status(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("request_list")
    Call<ResponseBody> get_request_list(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("shared_user_approve")
    Call<ResponseBody> shared_user_approval(
            @Field("id[]") ArrayList<String> id
//            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("shared_user_remove")
    Call<ResponseBody> shared_user_removal(
            @Field("id[]") ArrayList<String> id
    );

    @FormUrlEncoded
    @POST("approved_list")
    Call<ResponseBody> shared_approved_list(
            @Field("user_id") String user_id

    );


    @FormUrlEncoded
    @POST("alarm_history")
    Call<List<AlarmHistoryResponse>> get_alarm_data(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("user_id_alarm_id_wise_alarm")
    Call<List<AlarmHistoryResponse>> get_alarm_history_(
            @Field("user_id") String user_id,
            @Field("alarm_id") String alarm_id
    );


    // for both current and start date and end date
    @FormUrlEncoded
    @POST("alarm_id_wise_alarm_history")
    Call<List<AlarmHistoryResponse>> get_alarm_history_by_date(
            @Field("user_id") String user_id,
            @Field("alarm_id") String alarm_id,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );

    //

    @FormUrlEncoded
    @POST("datetodate_alarm_history")
    Call<List<AlarmHistoryResponse>> get_alarm_history_byDate(
            @Field("user_id") String user_id,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date
    );


    @FormUrlEncoded
    @POST("datetodate_alarm_history")
    Call<List<AlarmHistoryResponse>> get_alarm_history(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("remove_adding_history")
    Call<List<SensorHistoryResponse>> get_remove_adding_history(
            @Field("user_id") String user_id
    );


    @FormUrlEncoded
    @POST("change_password")
    Call<ChangePasswordResponse> change_password(
            @Field("user_id") String user_id,
            @Field("update_password") String update_password,
            @Field("current_password") String current_password
    );


    @FormUrlEncoded
    @POST("detector_control_code")
    Call<DetectorControlCodeResponse> get_detector_control_code(
            @Field("user_id") String user_id,
            @Field("alarm_id") String alarm_id,
            @Field("status") String status
    );

    @FormUrlEncoded
    @POST("bettery_status")
    Call<BatteryStatusResponse> sendBatteryStatus(@Field("host_number") String host_number,
                                                  @Field("battery_status") String battery_status);


    @POST("insert_alarm_from_json")
    Call<AlarmedDataResponse> createOrder(@Body AlarmedData order);


    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("apartment_list")
    Call<List<ApartmentListResponse>> getApartmentList(
            @Field("location") String location
    );


    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("room_info")
    Call<List<RoomInfoResponse>> getRoomInfo(
            @Field("apartment_id") String apartment_id
    );



    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("request_availabliity")
    Call<List<RoomAvailabilityResponse>> getRoomAvailability(
            @Field("from_date") String from_date,
            @Field("to_date") String to_date,
            @Field("room_uniq_id") String room_uniq_id
    );



    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("power_consumption_data")
    Call<PowerBreakdownResponse> getPower(
            @Field("booking_ref_no") String booking_ref_no
    );

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("water_consumption_data")
    Call<WaterUsageResponse> getWater(
            @Field("booking_ref_no") String booking_ref_no
    );

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("gas_consumption_data")
    Call<GasUsageResponse> getGas(
            @Field("booking_ref_no") String booking_ref_no
    );


//    @FormUrlEncoded
//    @POST("registration")
//    Call<BaseResponse> insert(@Body UserInfo userInfo);


//    @FormUrlEncoded
//    @POST("login")
//    Call<LoginResponse> login(
//            @Field("name") String name,
//            @Field("password") String password
//
//    );



    @FormUrlEncoded
    @POST("app_logout")
    Call <LogoutResponse> get_logout_status(
            @Field("user_id") String user_id
    );

}
