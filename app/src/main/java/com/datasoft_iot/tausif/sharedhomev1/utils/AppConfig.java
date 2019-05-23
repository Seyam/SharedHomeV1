package com.datasoft_iot.tausif.sharedhomev1.utils;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.datasoft_iot.tausif.sharedhomev1.model.SensorType;
import java.util.ArrayList;

public class AppConfig {

    //public static final String BASE_URL = "http://192.168.4.161/alarm/";

   // Alarm-April

  // public static final String BASE_URL = "http://192.168.2.84/alarm/";

    //192.168.4.161

   // public static final String BASE_URL = "http://192.168.4.161/alarm/";


    //192.168.6.226

  //  public static final String BASE_URL = "http://192.168.6.221/alarm/";

   // public static final String BASE_URL = "http://192.168.4.193/smart-home/";

   // public static final String BASE_URL = "http://192.168.4.253/smart-home/";

   //192.168.4.237

   public static final String BASE_URL = "http://3.18.42.77/smart-home-gp/";

    public static final String Base_URL = "http://192.168.4.252:8000/";

    public static final String Base_URL_local = "http://182.163.112.207:8070/";//http://182.163.112.207:8070/

    public static final String SHARED_PREFERENCES_NAME = "com.datasoft_iot.tausif.smartalarmv2.smart_alarm_shared_preferences";

    public static final String USERID = "user_id";

    public static final String FCMTOKEN = "fcm_token";

    public static final String IS_LOGGED = "is_logged";

    public static final String ALARM_ID = "alarm_id";

    public static final String USERTYPE = "user_type";

    public static final String QRCODE = "qr_code";

    public static final String AGE = "age";

    public static final String IS_NOTIFICATION_SHOWED = "IS_NOTIFICATION_SHOWED";

    public static final String IS_ALARMED_DATA_SENT = "is_alarmed_data_sent";

    public static final String USERNAME = "user_name";

    public static final String  HOST_NUMBER = "host_number";

    public static final String MOBILE_NUMBER = "MOBILE_NUMBER";

    public static ArrayList<String> getTypesOfSensor()    {
        ArrayList<String> typesOfSensor = new ArrayList<String>();
        typesOfSensor.add(". . .");
        typesOfSensor.add("Smoke");
        typesOfSensor.add("Motion");
        typesOfSensor.add("Gas Leak");
        typesOfSensor.add("Water Leak");
        return(typesOfSensor);
    }



    public static ArrayList<String> getTypesOfSensorDemo()    {
        ArrayList<String> typesOfSensor = new ArrayList<String>();
        typesOfSensor.add("Select Item");
        typesOfSensor.add("ALS001");
        typesOfSensor.add("ALM001");
        typesOfSensor.add("ALM002");
        typesOfSensor.add("ALS003");
        return(typesOfSensor);
    }

    public static ArrayList<String> getTypesOfAction()    {
        ArrayList<String> typesOfSensor = new ArrayList<String>();
        typesOfSensor.add("Select Item");
        typesOfSensor.add("Add");
        typesOfSensor.add("Remove");
        return(typesOfSensor);
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    public static boolean validateFirstName( String firstName )
    {
        Boolean status =firstName.matches( "[A-Z][a-zA-Z]*" );
        Log.e("FirstName Status", String.valueOf(status));

      //  return firstName.matches( "[A-Z][a-zA-Z]*" );

        return firstName.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*" );


    } // end method validateFirstName

    // validate last name
    public static boolean validateLastName( String lastName )
    {
        return lastName.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*" );
    } // end method validateLastName


    public static boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }



    public static ArrayList<SensorType> getTypesOfSensorV2()    {

        ArrayList<SensorType> typesOfSensor = new ArrayList<SensorType>();
        typesOfSensor.add(new SensorType("AL001","smoke"));
        typesOfSensor.add(new SensorType("AL002","motion"));
        typesOfSensor.add(new SensorType("AL003","gas leak"));
        typesOfSensor.add(new SensorType("AL004","dhock"));
        typesOfSensor.add(new SensorType("AL005","co2"));
        typesOfSensor.add(new SensorType("AL006","water Leak"));

        return(typesOfSensor);
    }


    public static ArrayList<String> getTypesOfSensorId()    {
        ArrayList<String> typesOfSensorId = new ArrayList<String>();
        typesOfSensorId.add("AL001");
        typesOfSensorId.add("Al002");
        typesOfSensorId.add("AL003");
        typesOfSensorId.add("AL004");
        typesOfSensorId.add("AL005");
        typesOfSensorId.add("AL006");
        return(typesOfSensorId);
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}



