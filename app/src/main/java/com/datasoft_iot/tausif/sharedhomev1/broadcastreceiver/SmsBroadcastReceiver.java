package com.datasoft_iot.tausif.sharedhomev1.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.AlarmedDataResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.ArmResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.controller.ArmStatusController;
import com.datasoft_iot.tausif.sharedhomev1.controller.BatteryStatusRequestController;
import com.datasoft_iot.tausif.sharedhomev1.controller.SendAlarmedDataController;
import com.datasoft_iot.tausif.sharedhomev1.db.DatabaseHelper;
import com.datasoft_iot.tausif.sharedhomev1.model.AlarmedData;
import com.datasoft_iot.tausif.sharedhomev1.model.SendAlarmedData;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * A broadcast receiver who listens for incoming SMS
 */

public class SmsBroadcastReceiver extends BroadcastReceiver implements AlarmedDataResponseListener,
        ArmResponseListener {

    //  SmsInfoController mSmsInfoController;

    SendAlarmedDataController mSendAlarmedDataController;

    // ArmStatusRequestController mArmStatusRequestController;
    BatteryStatusRequestController mBatteryStatusRequestController;


    List<SendAlarmedData> orderList = new ArrayList();

    List<SendAlarmedData> mSendAlarmedDataListFromDatabase = new ArrayList();

    DatabaseHelper mDatabaseHelper;

    AlarmedData mSendAlarmedData;

    MyPreferences mMyPreferences;

    Cursor mCursor;

    ArmStatusController mArmStatusController;


//    02 Zone is Wirless Zone
//    Zone Types: Closed
//    Siren: OFF

//    02 Zone is Wirless Zone
//    Zone Types: Emrgency
//    Siren: OFF


    @Override
    public void onReceive(Context context, Intent intent) {


        mDatabaseHelper = new DatabaseHelper(context);

        mMyPreferences = MyPreferences.getPreferences(context);

        mBatteryStatusRequestController = new BatteryStatusRequestController(context);

        mSendAlarmedDataController = new SendAlarmedDataController(this);

        mArmStatusController = new ArmStatusController(this);

        // getAlarmedDataFromDataBase();


        try {
            if (isOnline(context) && !mMyPreferences.isSentAlarmedData()) {


                Log.e("check data connection", "true");

                getAlarmedDataFromDataBase();

                deleteAllAlarmedData();

                // mMyPreferences.setSentAlarmedDataStatusCheck(true);


            } else {

                mMyPreferences.setSentAlarmedDataStatusCheck(false);


                Log.e("check data connection", "false");

                // networkStateListener.getNetworkStatus("offline");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            String smsSender = "";
            String smsBody = "";
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                smsBody += smsMessage.getMessageBody();

                smsSender += smsMessage.getDisplayOriginatingAddress();

                Log.e("Message", smsBody);
                Log.e("Mobile Number", smsSender);


            }


            //01313432951

            //mMyPreferences.getHostNumber()
            if (smsSender.substring(3).equals(mMyPreferences.getHostNumber())) {

                Log.e("Host Number", mMyPreferences.getHostNumber());

                if (smsBody.length() > 25) {

//                    String DetectorControlString[] = smsBody.split(" ");

                    Log.e("Control",  "invalid message");

              //      mArmStatusController.start(mMyPreferences.getUserID(),);

//                    DetectorControlString[7];

                }
                else{

                String battaryStatusString[] = smsBody.split(" ");


                char first = smsBody.charAt(0);
                char second = smsBody.charAt(1);
                System.out.println(first);

                int batteryStatus = Character.getNumericValue(first);


//            if(smsBody.equals("Arm")  || smsBody.equals("Remote Arm")){
//
//
//               mArmStatusRequestController.start(smsSender.substring(3),"1" );
//
//           }else if(smsBody.equals("Disarm") ||smsBody.equals("Remote Disarm") ){
//
//
//               mArmStatusRequestController.start(smsSender.substring(3),"0" );
//
//           }

//                Log.e("Battry", battaryStatusString[2]);

                if ( (batteryStatus == 1 || batteryStatus == 0) &&( second != ':' ) ) {


                    String[] splitMessage = smsBody.split(" ");
                    int index = smsBody.lastIndexOf(" ");
                    String title = splitMessage[0];
                    String type = splitMessage[1];
                    String date = splitMessage[2];
                    String time = smsBody.substring(index + 1);


                    sendBatteryStatusToServer(smsSender.substring(3), smsBody, context);


                } else {

                    sendToServer(smsSender.substring(3), smsBody, context);
                }


                }

            } else {

                Log.e("Host Number Error", "invalid host number");
            }

        }
    }


    public void sendBatteryStatusToServer(String mobile_no, String smsBody, Context context) {

        String[] smsParts;
        String pOne = null;
        String pTwo;
        String pThree;
        String pFour = null;


        try {

            smsParts = smsBody.split(" ");

            pOne = smsParts[0];
            pTwo = smsParts[1];
            pThree = smsParts[2];


            mBatteryStatusRequestController.start(mobile_no, pOne);

        } catch (Exception e) {


            Log.e("Error", "SMS Split error");

        }

        return;

    }


    public boolean sendToServer(String mobile_no, String smsBody, Context context) {


        // Message format
        // Motion 18th Floor Alarming 0r Smoke 18th Floor Alarming
        //M 01 ALarming
        String[] smsParts;
        String pOne = null;
        String pTwo;
        String pThree;
        String pFour = null;

        //  mobile_no ="1935899386";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String currentDateandTime = sdf.format(new Date());

        String[] parts = currentDateandTime.split(" ");
        String date1 = parts[0];
        String time = parts[1];


        Locale locale = new Locale("en");
        String pattern = "MM/dd/yyyy", date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, locale);
        date = simpleDateFormat.format(new Date());


        //  Log.e("Date", date);

        try {

            smsParts = smsBody.split(" ");
            pOne = smsParts[0];
            pTwo = smsParts[1];
            pThree = smsParts[2];
            // pFour  = smsParts[3];

            Log.e("pThree new", pThree);

            //Smoke 1-10

            if (pThree.equals("Alarm") && pOne.equals("Smoke") && pTwo.equals("01")) {


                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();


                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 1",
                                "ALS001",
                                time,
                                date));

                        mSendAlarmedData.Alarmed_data = orderList;


                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {


                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 1",
                                "ALS001",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfullly");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


            } else if (pThree.equals("Alarm") && pOne.equals("Smoke") && pTwo.equals("02")) {


                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();


                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 2",
                                "ALS002",
                                time,
                                date));

                        mSendAlarmedData.Alarmed_data = orderList;


                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {


                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 2",
                                "ALS002",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }


            } else if (pThree.equals("Alarm") && pOne.equals("Smoke") && pTwo.equals("03")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 3",
                                "ALS003",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 3",
                                "ALS003",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Smoke") && pTwo.equals("04")) {


                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();


                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 4",
                                "ALS004",
                                time,
                                date));

                        mSendAlarmedData.Alarmed_data = orderList;


                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {


                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 4",
                                "ALS004",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Smoke") && pTwo.equals("05")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 5",
                                "ALS005",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 5",
                                "ALS005",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Smoke") && pTwo.equals("06")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 6",
                                "ALS006",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 6",
                                "ALS006",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Smoke") && pTwo.equals("07")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 7",
                                "ALS007",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 7",
                                "ALS007",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Smoke") && pTwo.equals("08")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 8",
                                "ALS008",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 8",
                                "ALS008",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Smoke") && pTwo.equals("09")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 9",
                                "ALS009",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 9",
                                "ALS009",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Smoke") && pTwo.equals("10")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 10",
                                "ALS010",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Smoke 10",
                                "ALS0010",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Motion") && pTwo.equals("01")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 1",
                                "ALM001",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 1",
                                "ALM001",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Motion") && pTwo.equals("02")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 2",
                                "ALM002",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 2",
                                "ALM002",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Motion") && pTwo.equals("03")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 3",
                                "ALM003",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 3",
                                "ALM003",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Motion") && pTwo.equals("04")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 4",
                                "ALM004",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 4",
                                "ALM004",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Motion") && pTwo.equals("05")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 5",
                                "ALM005",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 5",
                                "ALM005",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Motion") && pTwo.equals("06")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 6",
                                "ALM006",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 6",
                                "ALM006",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Motion") && pTwo.equals("07")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 7",
                                "ALM007",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 7",
                                "ALM007",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Motion") && pTwo.equals("08")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 8",
                                "ALM008",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 8",
                                "ALM008",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Motion") && pTwo.equals("09")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 9",
                                "ALM009",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 9",
                                "ALM009",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Motion") && pTwo.equals("10")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Motion 10",
                                "ALM010",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Moition 1",
                                "ALM001",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Water") && pTwo.equals("01")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 1",
                                "ALW001",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 1",
                                "ALW001",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Water") && pTwo.equals("02")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 2",
                                "ALW002",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 2",
                                "ALW002",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Water") && pTwo.equals("03")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 3",
                                "ALW003",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 3",
                                "ALW003",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarming") && pOne.equals("Water") && pTwo.equals("04")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 4",
                                "ALW004",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 4",
                                "ALW004",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Water") && pTwo.equals("05")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 5",
                                "ALW005",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 5",
                                "ALW005",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Water") && pTwo.equals("06")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 6",
                                "ALW006",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 6",
                                "ALW006",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Water") && pTwo.equals("07")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 7",
                                "ALW007",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 7",
                                "ALW007",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("W") && pTwo.equals("08")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 8",
                                "ALW008",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 8",
                                "ALW008",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Water") && pTwo.equals("09")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 9",
                                "ALW009",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 9",
                                "ALW009",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Water") && pTwo.equals("10")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 10",
                                "ALW010",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Water 1",
                                "ALW001",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Gas") && pTwo.equals("01")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Gas 1",
                                "ALG001",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "GAS 1",
                                "ALG001",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Gas") && pTwo.equals("02")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Gas 2",
                                "ALG002",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "GAS 2",
                                "ALG002",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Gas") && pTwo.equals("03")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Gas 3",
                                "ALG003",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "GAS 3",
                                "ALG003",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Gas") && pTwo.equals("04")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Gas 4",
                                "ALG004",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "GAS 4",
                                "ALG004",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Gas") && pTwo.equals("05")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Gas 5",
                                "ALG005",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "GAS 5",
                                "ALG005",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Gas") && pTwo.equals("06")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Gas 6",
                                "ALG006",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "GAS 6",
                                "ALG006",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Gas") && pTwo.equals("07")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Gas 7",
                                "ALG007",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "GAS 7",
                                "ALG007",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Gas") && pTwo.equals("08")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Gas 8",
                                "ALG008",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "GAS 8",
                                "ALG008",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Gas") && pTwo.equals("09")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Gas 9",
                                "ALG009",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "GAS 9",
                                "ALG009",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else if (pThree.equals("Alarm") && pOne.equals("Gas") && pTwo.equals("10")) {
                try {
                    if (isOnline(context)) {

                        mSendAlarmedData = new AlarmedData();
                        orderList.add(new SendAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "Gas 10",
                                "ALG0010",
                                time,
                                date));
                        mSendAlarmedData.Alarmed_data = orderList;
                        mSendAlarmedDataController.start(mSendAlarmedData);

                        // networkStateListener.getNetworkStatus("online");
                    } else {
                        Log.e("insert database", "database called");

                        if (mDatabaseHelper.insertAlarmedData(mMyPreferences.getMobileNumber(),
                                mMyPreferences.getUserID(),
                                "GAS 10",
                                "ALG0010",
                                time,
                                date,
                                "0")) {
                            Log.e("Alarmed Data", "Insert Successfully");
                        } else {

                            Log.e("Alarmed Data", "went wrong");
                        }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            } else
                return false;

        } catch (Exception e) {

            Log.e("Error", "SMS Split error");

        }

        return false;

    }

    private boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            //should check null because in airplane mode it will be null
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void getAlarmedDataFromDataBase() {


        mCursor = mDatabaseHelper.getAlarmedList();

        //alarmData = new SendAlarmedData();


        if (mCursor.moveToFirst()) {
            do {


                mSendAlarmedDataListFromDatabase.add(new SendAlarmedData(
                        mCursor.getString(1),
                        mCursor.getString(2),
                        mCursor.getString(3),
                        mCursor.getString(4),
                        mCursor.getString(5),
                        mCursor.getString(6)));


                for (SendAlarmedData sendAlarmedData : mSendAlarmedDataListFromDatabase) {

                    Log.e("Alarm Type 1", sendAlarmedData.getAlarm_type());
                    Log.e("Mobile Number 2", sendAlarmedData.getMobile_number());
                    Log.e("Time 3", sendAlarmedData.getTime());


                }


            } while (mCursor.moveToNext());


        }

        mSendAlarmedData = new AlarmedData();
        mSendAlarmedData.Alarmed_data = mSendAlarmedDataListFromDatabase;

        if (mSendAlarmedDataListFromDatabase.isEmpty()) {


        } else {
            mSendAlarmedDataController.start(mSendAlarmedData);
            mMyPreferences.setSentAlarmedDataStatusCheck(true);
        }
    }


    public void deleteAllAlarmedData() {


        if (mDatabaseHelper.deleteAlarmedData(mDatabaseHelper)) {

            Log.e("Delete ALarmed", "successful");


        } else {

            Log.e("Delete ALarmed", "went wrong");

        }


    }


    @Override
    public void alarmedDataResponseSuccessful(String message) {

        mSendAlarmedDataListFromDatabase.clear();

        orderList.clear();

    }

    @Override
    public void alarmedDataResponseUnsuccessful(String message) {

        Log.e("Status", "succes");
    }

    @Override
    public void armResponseSuccessful(String message) {
        Log.e("Status", "failed");
    }

    @Override
    public void armResponseUnsuccessful(String message) {

    }
}
