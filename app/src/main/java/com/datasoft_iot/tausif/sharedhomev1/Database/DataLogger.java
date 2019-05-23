package com.datasoft_iot.tausif.sharedhomev1.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

public class DataLogger {

    private static final String SHARED_PREFS_NAME = "sharedPrefs";
    private Context context;

    public DataLogger(Context context) {
        this.context = context;
    }

    public void saveData(String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
        Log.e("Data"," Saved");
//        Toast.makeText(context, "Data Saved", Toast.LENGTH_SHORT).show();
    }

    public String loadData(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME,MODE_PRIVATE); //has to pass in proper context
        return sharedPreferences.getString(key,"");
    }

    public void clearData(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
