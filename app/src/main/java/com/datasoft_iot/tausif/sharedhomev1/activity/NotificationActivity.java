package com.datasoft_iot.tausif.sharedhomev1.activity;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.adapter.UnseenAlarmHistoryAdapter;
import com.datasoft_iot.tausif.sharedhomev1.db.DatabaseHelper;
import com.datasoft_iot.tausif.sharedhomev1.model.UnseenAlarm;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

  //  public static boolean active = false;

    TextView mTextViewAlarmStatus;
    ImageView mImageViewNoti;
    Uri notification;
    Ringtone r;
    Animation animation;
    MediaPlayer player;
    String alarmType ;

    MyPreferences mMyPreferences;

    DatabaseHelper mDatabaseHelper;
    Cursor mCursor;

    //model
    UnseenAlarm mUnseenAlarm;
    ArrayList<UnseenAlarm>UnseenAlarmList = new ArrayList<>();

    UnseenAlarmHistoryAdapter mUnseenAlarmHistoryAdapter;

    RecyclerView mRecyclerViewUnseen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setTurnScreenOn(true);
            setShowWhenLocked(true);
        } else {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
            window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }

        setContentView(R.layout.activity_notification);



        mMyPreferences = MyPreferences.getPreferences(this);

        mDatabaseHelper = new  DatabaseHelper(this);


        mUnseenAlarmHistoryAdapter = new UnseenAlarmHistoryAdapter(this);


        mRecyclerViewUnseen = findViewById(R.id.recyclerview_unseen_alarm_history);


        alarmType = getIntent().getStringExtra("AlarmType");


//        ArrayList<String> ar1=getIntent().getExtras().getStringArrayList("list");





        mTextViewAlarmStatus = findViewById(R.id.text_view_noti_alarm);

      //  mTextViewAlarmStatus.setText(listString+ " Detected");

        blinkingImage();
        playRingtone();

        mImageViewNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                animation.cancel();


                if(mMyPreferences.getUserType().equals("4")) {

                    Log.e("User", "Shared");

                }else{

                    sendSMS(mMyPreferences.getHostNumber(), "12340#");

                }

                if(player.isPlaying()) {


                    player.stop();

                }
                KeyguardManager myKM = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
                boolean isPhoneLocked = myKM.inKeyguardRestrictedInputMode();

                if (isPhoneLocked){

                    Toast.makeText(NotificationActivity.this, "Please unlock phone", Toast.LENGTH_SHORT).show();
                    mTextViewAlarmStatus.setText("Please Unlock Phone");
                    Log.e("Lock", "true");
                }

                else {

                    Intent intent = new Intent(NotificationActivity.this, MainActivityV2.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                   if (mDatabaseHelper.deleteUnseenAll(mDatabaseHelper)){

                       Log.e("Database Op", "deleted success");

                   }

                       else{

                       Log.e("Database Op", "deleted  no success");
                    };



                }

            }
        });


        getUnseenALarmListDataBase();
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

    @Override
    protected void onStart() {
        super.onStart();
       // active = true;
    }

    @Override
    protected void onStop() {
        super.onStop();

       // active = false;

    }

    @Override
    protected void onPause() {
        super.onPause();

         player.stop();

        // finish();
    }


    public void getUnseenALarmListDataBase(){


        int id ;
        String alarmType;
        String time;
        String date;

        mCursor = mDatabaseHelper.getAllUnseenAlarmList();




        if (mCursor.moveToFirst()) {
            do {

                id =  mCursor.getInt(0);
                Log.e("ID", String.valueOf(id));
                alarmType =  mCursor.getString(1);
                Log.e("Unseen Alarm Type", alarmType);
                time = mCursor.getString(2);
                Log.e("Unseen Alarm time", time);
                date = mCursor.getString(3);
                Log.e("Unseen Alarm  date ", date);


               //Log.d("first name" , String.valueOf(mUserInfo.getFirstname()));
                UnseenAlarmList.add(new UnseenAlarm(alarmType, time, date));
            } while (mCursor.moveToNext());




        }
//
//        for(UnseenAlarm unseenAlarm : UnseenAlarmList){
//
//            Log.e("Unseen Alarm type final", unseenAlarm.getAlarmType());
//            Log.e("Unseen Alarm time final", unseenAlarm.getTime());
//            Log.e("Unseen Alarm date final", unseenAlarm.getDate());
//
//        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerViewUnseen.setLayoutManager(linearLayoutManager);
        mRecyclerViewUnseen.setAdapter(mUnseenAlarmHistoryAdapter);
        mUnseenAlarmHistoryAdapter.addData(UnseenAlarmList);


    }


    public  void playRingtone(){



        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        player = MediaPlayer.create(this,R.raw.fire_truck_siren);

        if(player.isPlaying()){
            player.stop();
        }else {
            player.setLooping(true);
            player.start();
        }


//        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//            r = RingtoneManager.getRingtone(getApplicationContext(), notification);
//            r.play();

    }

    public  void blinkingImage (){

        mImageViewNoti = findViewById(R.id.image_view_noti_alarm);
        animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(1000); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        mImageViewNoti.startAnimation(animation); //to start animation

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stop();
    }
}
