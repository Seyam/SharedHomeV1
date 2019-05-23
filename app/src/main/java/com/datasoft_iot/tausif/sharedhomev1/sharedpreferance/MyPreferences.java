package com.datasoft_iot.tausif.sharedhomev1.sharedpreferance;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.utils.AppConfig;


public class MyPreferences {

    private static MyPreferences myPreferences;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private MyPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(AppConfig.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    public static MyPreferences getPreferences(Context context) {
        if (myPreferences == null)
            myPreferences = new MyPreferences(context);
        return myPreferences;
    }

    public void setUserId(String userId){

        editor.putString(AppConfig.USERID, userId);
        editor.apply();

        Log.e("User ID", "true");
    }

    public String getUserID(){
        //if no data is available for Config.USER_NAME then this getString() method returns
        //a default value that is mentioned in second parameter
        return sharedPreferences.getString(AppConfig.USERID, "null");
    }

    public void setFCMToken(String fcmToken){

        editor.putString(AppConfig.FCMTOKEN, fcmToken);
        editor.apply();

        Log.e("FCM Token", "true");
    }

    public String getFCMToken(){
        //if no data is available for Config.USER_NAME then this getString() method returns
        //a default value that is mentioned in second parameter
        return sharedPreferences.getString(AppConfig.FCMTOKEN, "null");
    }



    public void setLoginStatus(boolean isLogged){
        editor.putBoolean(AppConfig.IS_LOGGED, isLogged);
        editor.apply();
    }



    public boolean isLogged(){
        return sharedPreferences.getBoolean(AppConfig.IS_LOGGED, false); //assume the default value is false
    }


    //When alarm come via push notification, check whether user already seen or not
    public void setSeenNotificationStatus(boolean isNotificationShowed){
        editor.putBoolean(AppConfig.IS_NOTIFICATION_SHOWED, isNotificationShowed);
        editor.apply();
    }

    public boolean isNotificationShowed(){

        return sharedPreferences.getBoolean(AppConfig.IS_NOTIFICATION_SHOWED, false);


    }



    public void setQRCode(String qrCode){

        editor.putString(AppConfig.QRCODE, qrCode);
        editor.apply();

      //  Log.e("FCM Token", "true");
    }

    public String getQrcode(){
        //if no data is available for Config.USER_NAME then this getString() method returns
        //a default value that is mentioned in second parameter
        return sharedPreferences.getString(AppConfig.QRCODE, "null");
    }

    public void setUserName(String userName){

        editor.putString(AppConfig.USERNAME, userName);
        editor.apply();

     //   Log.e("FCM Token", "true");
    }

    public String getUserName(){
        //if no data is available for Config.USER_NAME then this getString() method returns
        //a default value that is mentioned in second parameter
        return sharedPreferences.getString(AppConfig.USERNAME, "null");
    }


    public void setMobileNumber(String mobileNumber){

        editor.putString(AppConfig.MOBILE_NUMBER, mobileNumber);
        editor.apply();

        //   Log.e("FCM Token", "true");
    }

    public String getMobileNumber(){
        //if no data is available for Config.USER_NAME then this getString() method returns
        //a default value that is mentioned in second parameter
        return sharedPreferences.getString(AppConfig.MOBILE_NUMBER, "null");
    }

    public void setHostNumber(String hostNumber){

        editor.putString(AppConfig.HOST_NUMBER, hostNumber);
        editor.apply();

        //   Log.e("FCM Token", "true");
    }

    public String getHostNumber(){
        //if no data is available for Config.USER_NAME then this getString() method returns
        //a default value that is mentioned in second parameter
        return sharedPreferences.getString(AppConfig.HOST_NUMBER, "null");
    }



    public void setUserType(String userType){

        editor.putString(AppConfig.USERTYPE, userType);
        editor.apply();

        //   Log.e("FCM Token", "true");
    }

    public String getUserType(){
        //if no data is available for Config.USER_NAME then this getString() method returns
        //a default value that is mentioned in second parameter
        return sharedPreferences.getString(AppConfig.USERTYPE, "null");
    }


    //Check Data Connection Status
    public void setSentAlarmedDataStatusCheck(boolean isAlarmedDataSent){
        editor.putBoolean(AppConfig.IS_ALARMED_DATA_SENT, isAlarmedDataSent);
        editor.apply();
    }

    public boolean isSentAlarmedData(){

        return sharedPreferences.getBoolean(AppConfig.IS_ALARMED_DATA_SENT, false);


    }


    public void setALarmId(String aLarmId){

        editor.putString(AppConfig.ALARM_ID, aLarmId);
        editor.apply();

       // Log.e("FCM Token", "true");
    }

    public String getAlarmId(){
        //if no data is available for Config.USER_NAME then this getString() method returns
        //a default value that is mentioned in second parameter
        return sharedPreferences.getString(AppConfig.ALARM_ID, "null");
    }





}
