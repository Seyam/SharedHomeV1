package com.datasoft_iot.tausif.sharedhomev1.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;
import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.activity.NotificationActivity;
import com.datasoft_iot.tausif.sharedhomev1.db.DatabaseHelper;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.ArrayList;
import java.util.Random;

public class MyFirebaseInstanceService extends FirebaseMessagingService {


    MyPreferences mMyPreferences;

    ArrayList<String> SensorAlert = new ArrayList<>();

    DatabaseHelper mDatabaseHelper;

    String host_number;

    String message;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        Log.e("Message Received", "From: " + remoteMessage.getFrom());

        //   showNotification("Alarm", "smoke");

        mMyPreferences = MyPreferences.getPreferences(this);

        mDatabaseHelper = new DatabaseHelper(this);

        // Check if message contains a data payload.

        if (mMyPreferences.isLogged()) {
//            if (remoteMessage.getData().size() > 0) {
                Log.e("Message Received", "" + remoteMessage.getData());

                message = remoteMessage.getData().get("message");

           //     host_number = remoteMessage.getData().get("host_number");


                //for arm dis arm status checking

//                else if (message.equals("1")) {
//                    MainActivity.flag = Integer.parseInt(message);
//                    String m = "Host Device Activated";
//                    Intent intent = new Intent(this, DialogArmNotification.class);
//                //    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("Status", m);
//                    this.startActivity(intent);
//
//                    MainActivity.setArmTitle();

              //  } else {


                    if (mMyPreferences.isNotificationShowed()) {

                        Log.e("Seen", "seen");
                        SensorAlert.clear();
                        String[] splitMessage = message.split(",");
                        int index = message.lastIndexOf(",");
                        String title = splitMessage[0];
                        String type = splitMessage[1];
                        String date = splitMessage[2];
                        String time =  message.substring(index+1);

                        mMyPreferences.setSeenNotificationStatus(false);

                   //     SensorAlert.add(type);


                        showNotification("Alarm", type + " " + "Detected");

                        if(mDatabaseHelper.insertUnseenAlarmData(type, time,date)){

                            Log.e("Database", "Unseen Alarm Stored Successfully");

                        }else{

                            Log.e("Database", "Unseen Alarm Stored Failed");

                        }




                        Log.e("Alarm Message", message);


                        Intent intent1 = new Intent();
                        intent1.setClassName(this, NotificationActivity.class.getName());
                        intent1.putExtra("AlarmType", type);
                     //   intent1.putStringArrayListExtra("list", SensorAlert);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        this.startActivity(intent1);


                    } else {

                        Log.e("UnSeen", "unseen");

                        String[] splitMessage = message.split(",");
                        int index = message.lastIndexOf(",");
                        String title = splitMessage[0];
                        String type = splitMessage[1];
                        String date = splitMessage[2];
                        String time = message.substring(index+1);

                        Log.e("after split", title+" "+ type+ " "+ date + " "+ time);


                        SensorAlert.add(type);

                        if(mDatabaseHelper.insertUnseenAlarmData(type,time,date)){

                            Log.e("Database", "Unseen Alarm Stored Successfully");

                        }else{

                            Log.e("Database", "Unseen Alarm Stored Failed");

                        }


                        showNotification("Alarm", type + " " + "Detected");

                        for(String s : SensorAlert){

                            Log.e("Final", s);

                        }





//
//                    if (NotificationActivity.active) {
//
//                        Log.e("Activity Status", "true");
//
//
//                    } else {


                        Intent intent1 = new Intent();
                        intent1.setClassName(this, NotificationActivity.class.getName());
                        intent1.putExtra("AlarmType", type);
                      //  intent1.putStringArrayListExtra("list", SensorAlert);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        this.startActivity(intent1);


                        // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                        //    }


                    }

          //  }

            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                Log.e("null", "Message Notification Body: " + remoteMessage.getNotification().getBody());
            }
        } else {

            Log.e("Alarm Status", "User logged Out ");
        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);

        //  Log.d("Tokenfirebase", s);
    }

    public  void sendSMS( String mobile_number, String SMSData){

        SmsManager sms = SmsManager.getDefault();

        if (mobile_number.length() == 11) {
            ArrayList<String> parts = sms.divideMessage(SMSData);
            sms.sendMultipartTextMessage("+88" + mobile_number, null, parts, null, null); // adding number and text
//            Toast.makeText(this, "Message ", Toast.LENGTH_SHORT).show();

            Log.e("SMS Status", "Sent");
        } else {
            // Toast.makeText(this, "Unknown Number format", Toast.LENGTH_SHORT).show();

            Log.e("SMS Status", "Not Sent");


        }
    }

    private void showNotification(String title, String body) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        String NOTIFICATION_CHANNEL_ID = "com.datasoft_iot.tausif.pushnotification.test";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("Alarm");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.alarm_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Info");

        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());


    }
}
