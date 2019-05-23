package com.datasoft_iot.tausif.sharedhomev1.activity;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.TouchToUnLockView;
import com.datasoft_iot.tausif.sharedhomev1.adapter.CustomGrid;
import com.datasoft_iot.tausif.sharedhomev1.adapter.ShowAlarmTypeAlarmAdapter;
import com.datasoft_iot.tausif.sharedhomev1.broadcastreceiver.SmsBroadcastReceiver;
import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.AlarmListResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.ArmResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.ArmStatusResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.FCMUpdateResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.controller.AlarmListRequestController;
import com.datasoft_iot.tausif.sharedhomev1.controller.ArmStatusController;
import com.datasoft_iot.tausif.sharedhomev1.controller.CheckArmStatusController;
import com.datasoft_iot.tausif.sharedhomev1.controller.UpdateFCMTokenController;
import com.datasoft_iot.tausif.sharedhomev1.db.DatabaseHelper;
import com.datasoft_iot.tausif.sharedhomev1.model.AlarmList;
import com.datasoft_iot.tausif.sharedhomev1.model.AlarmListGridView;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;
import com.datasoft_iot.tausif.sharedhomev1.utils.AppConfig;
import com.datasoft_iot.tausif.sharedhomev1.utils.ViewUtils;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.SEND_SMS;


public class MainActivity extends AppCompatActivity implements
        AlarmListResponseListener, ArmResponseListener,
        FCMUpdateResponseListener, OnMenuItemClickListener,
        OnMenuItemLongClickListener, ArmStatusResponseListener {

    //  SweetAlertDialog pDialog;
    public static SpotsDialog spotsDialog;
    public static int flag = 0;
    TextView tvNavApplianceName, mTextViewAlarmTitle, mTextViewArmStatus ,mTextViewHostNumber;
    MyPreferences mMyPreferences;
    Toolbar toolbarBottom;
    CustomGrid adapterSensorType;
    GridView gridViewSensorType;
    String[] apartmentStatus;
    List<String> mSensorTypesId = new ArrayList<>();
    ArrayList<String> commons = new ArrayList<>();
    String[] number_of_sensor_string_array;
    AlarmListRequestController mAlarmListRequestController;
    UpdateFCMTokenController mUpdateFCMTokenController;
    ArmStatusController mArmStatusController;
    CheckArmStatusController mCheckArmStatusController;
    DatabaseHelper mDatabaseHelper;
    Cursor mCursor;
    String user_id;
    int[] SensorImage = new int[40];
    int count, c;
    String[] sensoName = new String[40];
    AlarmListGridView mAlarmListGridView;
    List<AlarmListGridView> mListGridView = new ArrayList<>();
    ShowAlarmTypeAlarmAdapter mShowAlarmTypeAlarmAdapter;
    RecyclerView mRecyclerViewAlarmType;
    String disarm;
    String passkey_settings;
    String lock_battery_alert;
    String fcm_token;
    MenuItem mMenuItem;
    String value;
    NavigationView navigationView;
    private ImageView bottomSliderUpAnimation;
    //  ProgressDialog progressDialog;
    private SlidingUpPanelLayout mLayout;
    private TouchToUnLockView mUnlockView;
    private View mContainerView;
    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;


    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int SMS_PERMISSION_CODE = 0;


    private BroadcastReceiver mNetworkReceiver;



    private DrawerLayout drawerLayout;

    Toolbar toolBar;




    public static void setArmTitle() {

        spotsDialog.dismiss();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mMyPreferences = MyPreferences.getPreferences(this);

        mMyPreferences.setSeenNotificationStatus(false);

        mDatabaseHelper = new DatabaseHelper(MainActivity.this);


        value = mMyPreferences.getUserType();

        // Log.e("User Type", value);

        if (value.equals("4")) {

            hideItem();
        }



        apartmentStatus = getResources().getStringArray(R.array.my_apartment_status_array);
        mTextViewAlarmTitle = findViewById(R.id.txtv_UnlockTips);
        mTextViewArmStatus = findViewById(R.id.text_view_arm_status);



        mShowAlarmTypeAlarmAdapter = new ShowAlarmTypeAlarmAdapter(this);

        fcm_token = mMyPreferences.getFCMToken();
        user_id = mMyPreferences.getUserID();

        Log.e("User ID", user_id);

        //  pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);

        spotsDialog = new SpotsDialog(this);



        mAlarmListRequestController = new AlarmListRequestController(this, this);

        mUpdateFCMTokenController = new UpdateFCMTokenController(this, this);

        mCheckArmStatusController = new CheckArmStatusController(this, this);

       // mArmStatusController = new ArmStatusController(this, this);

        if (AppConfig.isNetworkAvailable(MainActivity.this)) {

            spotsDialog.show();

            mUpdateFCMTokenController.start(user_id, fcm_token);
            mAlarmListRequestController.start(user_id);
            mCheckArmStatusController.start(user_id);

        } else {


            spotsDialog.dismiss();
            Toast.makeText(this, "Data connection is unavailable !", Toast.LENGTH_SHORT).show();
        }


        // String s = alarmListResponses.getAlarm_name();

        mRecyclerViewAlarmType = findViewById(R.id.recyclerview_alarm_type_actvitiy);


        View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);
        tvNavApplianceName = header.findViewById(R.id.tvApartmentName);

        mTextViewHostNumber = header.findViewById(R.id.textViewHostNumber);

        mTextViewHostNumber.setText(mMyPreferences.getMobileNumber());

        tvNavApplianceName.setText(mMyPreferences.getUserName());





        bottomSliderUpAnimation = (ImageView) findViewById(R.id.bottomSliderUpAnimation);
        Glide
                .with(getApplicationContext())
                .load(R.drawable.upp_arrow_final)
                //.asGif()
                .into(bottomSliderUpAnimation);


        //SlidingUpPanelLayout
        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        mLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {
                //     Log.i(TAG, "onPanelSlide, offset " + slideOffset);
                //   Log.e("onPanel_Slide", "__________");
            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

                //Log.i(TAG, "onPanelStateChanged " + newState);

                toolbarBottom = (Toolbar) findViewById(R.id.toolbarBottom);
                setSupportActionBar(toolbarBottom);
                getSupportActionBar().setTitle("Quick Status");

                FrameLayout slidingTopView = findViewById(R.id.slidingTopView);

                if (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    Log.e("onPanel_state_down", "__________");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);


                    slidingTopView.setVisibility(View.VISIBLE);
                    bottomSliderUpAnimation.setVisibility(View.VISIBLE);
                    Glide
                            .with(getApplicationContext())
                            .load(R.drawable.upp_arrow_final)
                            //.asGif()
                            .into(bottomSliderUpAnimation);
                } else {
                    Log.e("onPanel_state_up", "__________");
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_button);

                    Glide
                            .with(getApplicationContext())
                            .load(R.drawable.arrow_down)
                            //.asGif()
                            .into(bottomSliderUpAnimation);


//                    bottomSliderUpAnimation.setVisibility(View.GONE);
//                    slidingTopView.setVisibility(View.GONE);

                }
            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });


        gridViewSensorType = (GridView) findViewById(R.id.gridApartmentStatus);


        //--------------------set UI------------------------//
//        adapterSensorType = new CustomGrid(MainActivity.this, apartmentStatus, apartmentStatusImage, false);
//
//        gridViewSensorType = (GridView) findViewById(R.id.gridViewSensorType);
//
//        gridViewSensorType.setVerticalScrollBarEnabled(false);
//
//        gridViewSensorType.setAdapter(adapterSensorType);
//
//
//        gridViewSensorType.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
//                switch (position) {
//                    case 0:
//                        break;
//                    case 1:
//                      //  Intent intent = new Intent(MainActivity.this, ApartmentStatusActivity.class);
//                        //startActivity(intent);
//                        break;
//                }
//                return true;
//            }
//        });

        initSlideToUnlock();


        //for right side menu bar
        fragmentManager = getSupportFragmentManager();
        initMenuFragment();

        disarm = getResources().getString(R.string.disarm);
//        passkey_settings = getResources().getString(R.string.passkey_settings);
//        lock_battery_alert = getResources().getString(R.string.lock_battery_alert);



        // Read Phone state, SMS
        if (!checkPermission()) {
            requestPermission();
        } else {
            Log.e("Status" , "Connection already Granted");
            //     Toast.makeText(MainActivity.this, "Connection Already Granted", Toast.LENGTH_LONG).show()
        }

        if (!hasReadSmsPermission()) {
            showRequestPermissionsInfoAlertDialog();
        }

        mNetworkReceiver = new SmsBroadcastReceiver();


        registerNetworkBroadcastForNougat();



        configureNavigationDrawer();
        configureToolbar();


    }

    //Right hand Menu Bar
    private void initMenuFragment() {

        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(true);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private void initSlideToUnlock() {

        mContainerView = ViewUtils.get(this, R.id.layFront);

        mUnlockView = ViewUtils.get(this, R.id.tulv_UnlockView);

        mUnlockView.setOnTouchToUnlockListener(new TouchToUnLockView.OnTouchToUnlockListener() {
            @Override
            public void onTouchLockArea() {
                if (mContainerView != null) {
                    mContainerView.setBackgroundColor(Color.parseColor("#66000000"));
                    //  Toast.makeText(MainActivity.this, "Helloo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onSlidePercent(float percent) {
                if (mContainerView != null) {
                    mContainerView.setAlpha(1 - percent < 0.05f ? 0.05f : 1 - percent);
                    mContainerView.setScaleX(1 + (percent > 1f ? 1f : percent) * 0.08f);
                    mContainerView.setScaleY(1 + (percent > 1f ? 1f : percent) * 0.08f);
                }
            }

            @Override
            public void onSlideToUnlock() {


                if (flag == 0) {

                    //    Toast.makeText(MainActivity.this, "Armed", Toast.LENGTH_SHORT).show();


                    if (AppConfig.isNetworkAvailable(MainActivity.this)) {

                        spotsDialog.show();
                       // mArmStatusController.start(user_id, "1");
                    } else {

                        Toast.makeText(MainActivity.this, "Data connection is unavailable !", Toast.LENGTH_SHORT).show();

                    }

                    //     flag = 1;

                } else if (flag == 1) {


                    //   Toast.makeText(MainActivity.this, "Unarmed", Toast.LENGTH_SHORT).show();

                    if (AppConfig.isNetworkAvailable(MainActivity.this)) {

                        spotsDialog.show();

                      //  mArmStatusController.start(user_id, "0");

                    } else {

                        Toast.makeText(MainActivity.this, "Data connection is unavailable !", Toast.LENGTH_SHORT).show();

                    }


                    //   flag = 0;

                }


                //    Toast.makeText(MainActivity.this, " Arm Clicked", Toast.LENGTH_SHORT).show();
                //  final SharedPreferences sharedPreferences2 = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                //Log.d("________POC_Client", "onSlideToUnlock check client :" + sharedPreferences2.getString(Config.DATA_IP_ADDRESS, null));
//                //finish();
//                if (Utils.isWifiAvailable(context)) {
//                    //Client myClient = new Client(callback, "192.168.4.177", 8080);
//                    // Log.e("________POC_Client doorLockIpAddress_sp_client", "" + sharedPreferences.getString(Config.DATA_IP_ADDRESS, null) + " configData.getLeaseholderId() " + configData.getLeaseholderId());
//                    Client myClient = new Client(callback, sharedPreferences.getString(Config.DATA_IP_ADDRESS, null), 8080);
//                    myClient.execute();
//                } else {
//                    Toast.makeText(context, R.string.wifi_not_enable, Toast.LENGTH_SHORT).show();
//                }

                //unlock();
                //Log.e("TAG_______", "unlock clicked");
            }

            @Override
            public void onSlideAbort() {
                if (mContainerView != null) {
                    mContainerView.setAlpha(1.0f);
                    mContainerView.setBackgroundColor(0);
                    mContainerView.setScaleX(1f);
                    mContainerView.setScaleY(1f);
                }
            }
        });
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//
//        mMenuItem = item;
//
////        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
////            @Override
////            public boolean onMenuItemClick(MenuItem item) {
////                item.setEnabled(true);
////                item.setTitle(Html.fromHtml("<font color='#ff3824'>Settings</font>"));
////                return false;
////            }
////        });
//
//
//        int id = item.getItemId();
//
////        if (id == R.id.powerAnalytics) {
////
////            if (AppConfig.isNetworkAvailable(this)) {
//////                Intent changePass = new Intent(this, PowerAnalyticsActivity.class);
//////                changePass.putExtra("EXTRA_BOOKING_ID", booking_id);
//////                startActivity(changePass);
////            } else {
////                Toast.makeText(MainActivity.this, R.string.no_internet_connection, Toast.LENGTH_LONG).show();
////            }
////
////        }
////
//        if (value.equals("4")) {
//
//
//            if (id == R.id.logout) {
//
//                logout();
//
//            }
//
//            else if (id == R.id.alarm_battery_status) {
//
//                Intent intentAlarm = new Intent(this, BatteryStatusActivity.class);
//                startActivity(intentAlarm);
//            }
//
//
//            else if (id == R.id.change_password) {
//
//                Intent intentAlarm = new Intent(this, ChangePasswordActivity.class);
//                startActivity(intentAlarm);
//            }
//
//            else if (id == R.id.alarm_history) {
//
//                Intent intentAlarm = new Intent(this, AlarmHistoryActivity.class);
//                startActivity(intentAlarm);
//            }
//
//
//            else if (id == R.id.alarm_battery_status) {
//
//                Intent intentAlarm = new Intent(this, BatteryStatusActivity.class);
//                startActivity(intentAlarm);
//            }
//
//        }
//
//        else {
//
//            if (id == R.id.logout) {
//
//                logout();
//
//
//            } else if (id == R.id.nav_add_remove_drawer) {
//
//
//                Intent intent = new Intent(MainActivity.this, AddNewSensorActivity.class);
//
//                startActivity(intent);
//
//            } else if (id == R.id.nav_approve_shared_list) {
//
//
//                Intent intent = new Intent(MainActivity.this, ShowPendingSharedActvity.class);
//
//                startActivity(intent);
//
//            } else if (id == R.id.arm_history) {
//
//                Intent intentAlarm = new Intent(this, ArmHistoryActivity.class);
//                startActivity(intentAlarm);
//            }
//
//            else if (id == R.id.alarm_approved_account) {
//
//                Intent intentAlarm = new Intent(this, ArmHistoryActivity.class);
//                startActivity(intentAlarm);
//            }
//
//            else if (id == R.id.shared_list) {
//
//                Intent intent = new Intent(this, SharedListActivity.class);
//                startActivity(intent);
//            }
//
//
//            else if (id == R.id.alarm_history) {
//
//                Intent intentAlarm = new Intent(this, AlarmHistoryActivity.class);
//                startActivity(intentAlarm);
//            }
//            else if (id == R.id.sensor_add_remove_history) {
//
//                Intent intentAlarm = new Intent(this, SensorHistoryActivity.class);
//                startActivity(intentAlarm);
//            }
//
//            else if (id == R.id.alarm_battery_status) {
//
//                Intent intentAlarm = new Intent(this, BatteryStatusActivity.class);
//                startActivity(intentAlarm);
//            }
//            else if (id == R.id.change_password) {
//
//                Intent intentAlarm = new Intent(this, ChangePasswordActivity.class);
//                startActivity(intentAlarm);
//            }
//
//
//        }
//
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//
//        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
//            @Override
//            public void onDrawerSlide(View drawerView, float slideOffset) {
//            }
//
//            @Override
//            public void onDrawerOpened(View drawerView) {
//            }
//
//            @Override
//            public void onDrawerClosed(View drawerView) {
//
//                /*Log.e("drawerClose", "__________");
//
//                if (R.id.changeLanguage == drawerView.getId()) {
//
//                    Log.e("drawerClose", "__________");
//
//                    Resources resources = getResources();
//                    SharedPreferences sharedPreferences = getSharedPreferences("localePref", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                    if (sharedPreferences.getString(LOCALE_KEY, ENGLISH_LOCALE).equals(LOCALE_JAPANESE)) {
//                        locale = new Locale(ENGLISH_LOCALE);
//                        editor.putString(LOCALE_KEY, ENGLISH_LOCALE);
//                    } else {
//                        locale = new Locale(LOCALE_JAPANESE);
//                        editor.putString(LOCALE_KEY, LOCALE_JAPANESE);
//                    }
//                    editor.apply();
//
//                    Configuration configuration = resources.getConfiguration();
//                    configuration.setLocale(locale);
//                    getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());
//                    recreate();
//                }*/
//            }
//
//            @Override
//            public void onDrawerStateChanged(int newState) {
//            }
//        });
//
//        return true;
//    }

    @Override
    protected void onResume() {
        super.onResume();
        mUnlockView.startAnim();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mUnlockView.stopAnim();
    }

    private void logout() {
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {


                        mMyPreferences.setLoginStatus(false);
                        mMyPreferences.setUserId("");


                        //Starting login activity
                        Intent intent = new Intent(MainActivity.this, UserActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void convertToStringArrayV1(List<AlarmList> alarmListResponse) {


        //  String alarm_id  = alarmListResponse.getAlarmType();
        // String number_of_sensor  = alarmListResponse.getNumber_of_sensor();

//
//        String alarm_id = "AL001,AL002,AL003";
//
//        String number_of_sensor = "2,1,4";

//
//        String[] alarm_id_string_array = alarm_id.split(",");
//        number_of_sensor_string_array = number_of_sensor.split(",");

//        Log.e(" Alarm Type Size", String.valueOf(alarm_id_string_array.length));
//        Log.e(" Nu. alarm size", String.valueOf(number_of_sensor_string_array.length));

//
//        for( int i = 0; i< number_of_sensor_string_array.length; i++){
//
//            Log.e("Data", number_of_sensor_string_array[i]);
//
//
//        }

//        int ct = 0;
//
//        int addCounting = 0;
//
//        for (String alarm_id1 : alarm_id_string_array) {
//
//            if (alarm_id1.equals("AL001")) {
//
//                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke,"AL001", "Smoke",number_of_sensor_string_array[ct]);
//                mListGridView.add(mAlarmListGridView);
//                SensorImage[count++] = R.drawable.ic_smoke;
//                sensoName[c++] = "Smoke";
//           //     Log.e("Type", "Smoke");
//
//                ct++;
//
//
//                addCounting++;
//                Log.e("loop count", String.valueOf(addCounting));
//
//
//            } else if (alarm_id1.equals("AL002")) {
//
//
//                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_motion,"AL002", "Motion",number_of_sensor_string_array[ct]);
//                mListGridView.add(mAlarmListGridView);
//                sensoName[c++] = "Motion";
//                SensorImage[count++] = R.drawable.ic_motion;
//             //   Log.e("Type", "Motion");
//
//                ct++;
//
//                addCounting++;
//                Log.e("loop count", String.valueOf(addCounting));
//
//
//            } else if (alarm_id1.equals("AL003")) {
//
//             //   Log.e("Type", String.valueOf(ct));
//
//                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_gas,"AL003", "GAS Leak",number_of_sensor_string_array[ct]);
//
//                mListGridView.add(mAlarmListGridView);
//
//                SensorImage[count++] = R.drawable.ic_gas;
//                sensoName[c++] = "Gas Leak";
//             //   Log.e("Type", "Gas Leak");
//
//                ct++;
//
//                addCounting++;
//                Log.e("loop count", String.valueOf(addCounting));
//
//
//            } else if (alarm_id1.equals("AL004")) {
//
//
//                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_shock,"AL004", "Shock",number_of_sensor_string_array[ct]);
//                mListGridView.add(mAlarmListGridView);
//                ct++;
//
//                SensorImage[count++] = R.drawable.ic_shock;
//                sensoName[c++] = "Shock";
//                Log.e("Type", "Shock");
//
//                addCounting++;
//                Log.e("loop count", String.valueOf(addCounting));
//
//            } else if (alarm_id1.equals("AL005")) {
//
//
//                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_co2,"AL005", "CO2",number_of_sensor_string_array[ct]);
//                mListGridView.add(mAlarmListGridView);
//                ct++;
//                sensoName[c++] = "CO2 ";
//                SensorImage[count++] = R.drawable.ic_co2;
//                Log.e("Type", "CO2");
//
//                addCounting++;
//                Log.e("loop count", String.valueOf(addCounting));
//
//            } else if (alarm_id1.equals("AL006")) {
//
//                Log.e("Type", String.valueOf(number_of_sensor_string_array[0]));
//
//                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_water_leak,"AL006", "Water Leak",number_of_sensor_string_array[ct]);
//                mListGridView.add(mAlarmListGridView);
//                ct++;
//
//                sensoName[c++] = "Water Leak";
//                SensorImage[count++] = R.drawable.ic_water_leak;
//                Log.e("Type", "Water Leak");
//
//                addCounting++;
//                Log.e("loop count", String.valueOf(addCounting));
//            }
//
//
//
//            for(AlarmListGridView alarmListGridView :mListGridView ){
//
//                Log.e("Alarm List" ,alarmListGridView.getSensorType());
//            }


        int ct = 0;

        int addCounting = 0;

        for (AlarmList s : alarmListResponse) {


//            String value = s.substring(0,3);
//
//            Log.e("substring", value);


            int m = 1;


            String concatValue = null;


            if (s.getAlarmId().equals("ALS001")) {

                // mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke,"ALS001", "Smoke",number_of_sensor_string_array[ct]);


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke, "ALS001", "Smoke 1");
                mListGridView.add(mAlarmListGridView);
                SensorImage[count++] = R.drawable.ic_smoke;
                sensoName[c++] = "Smoke";
                //     Log.e("Type", "Smoke");

                ct++;


                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALS002")) {

                // mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke,"ALS001", "Smoke",number_of_sensor_string_array[ct]);


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke, "ALS002", "Smoke 2");
                mListGridView.add(mAlarmListGridView);
                SensorImage[count++] = R.drawable.ic_smoke;
                sensoName[c++] = "Smoke";
                //     Log.e("Type", "Smoke");
                ct++;
                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALS003")) {

                // mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke,"ALS001", "Smoke",number_of_sensor_string_array[ct]);


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke, "ALS003", "Smoke 3");
                mListGridView.add(mAlarmListGridView);
                SensorImage[count++] = R.drawable.ic_smoke;
                sensoName[c++] = "Smoke";
                //     Log.e("Type", "Smoke");

                ct++;


                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALS004")) {

                // mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke,"ALS001", "Smoke",number_of_sensor_string_array[ct]);


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke, "ALS004", "Smoke 4");
                mListGridView.add(mAlarmListGridView);
                SensorImage[count++] = R.drawable.ic_smoke;
                sensoName[c++] = "Smoke";
                //     Log.e("Type", "Smoke");

                ct++;


                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALS005")) {

                // mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke,"ALS001", "Smoke",number_of_sensor_string_array[ct]);


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke, "ALS005", "Smoke 5");
                mListGridView.add(mAlarmListGridView);
                SensorImage[count++] = R.drawable.ic_smoke;
                sensoName[c++] = "Smoke";
                //     Log.e("Type", "Smoke");

                ct++;


                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALS006")) {

                // mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke,"ALS001", "Smoke",number_of_sensor_string_array[ct]);


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke, "ALS006", "Smoke 6");
                mListGridView.add(mAlarmListGridView);
                SensorImage[count++] = R.drawable.ic_smoke;
                sensoName[c++] = "Smoke";
                //     Log.e("Type", "Smoke");

                ct++;


                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALS007")) {

                // mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke,"ALS001", "Smoke",number_of_sensor_string_array[ct]);


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke, "ALS007", "Smoke 7");
                mListGridView.add(mAlarmListGridView);
                SensorImage[count++] = R.drawable.ic_smoke;
                sensoName[c++] = "Smoke";
                //     Log.e("Type", "Smoke");

                ct++;


                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALS008")) {

                // mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke,"ALS001", "Smoke",number_of_sensor_string_array[ct]);


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke, "ALS008", "Smoke 8");
                mListGridView.add(mAlarmListGridView);
                SensorImage[count++] = R.drawable.ic_smoke;
                sensoName[c++] = "Smoke";
                //     Log.e("Type", "Smoke");

                ct++;


                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALS009")) {

                // mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke,"ALS001", "Smoke",number_of_sensor_string_array[ct]);


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke, "ALS009", "Smoke 9");
                mListGridView.add(mAlarmListGridView);
                SensorImage[count++] = R.drawable.ic_smoke;
                sensoName[c++] = "Smoke";
                //     Log.e("Type", "Smoke");

                ct++;


                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALS010")) {

                // mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke,"ALS001", "Smoke",number_of_sensor_string_array[ct]);


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke, "ALS010", "Smoke 10");
                mListGridView.add(mAlarmListGridView);
                SensorImage[count++] = R.drawable.ic_smoke;
                sensoName[c++] = "Smoke";
                //     Log.e("Type", "Smoke");

                ct++;


                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALM001")) {

                // mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke,"ALS001", "Smoke",number_of_sensor_string_array[ct]);


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_motion, "ALM001", "Motion 1");
                mListGridView.add(mAlarmListGridView);
                SensorImage[count++] = R.drawable.ic_motion;
                sensoName[c++] = "Motion";
                //     Log.e("Type", "Smoke");

                ct++;
                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALM002")) {

                // mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke,"ALS001", "Smoke",number_of_sensor_string_array[ct]);


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_motion, "ALM002", "Motion 2");
                mListGridView.add(mAlarmListGridView);
                SensorImage[count++] = R.drawable.ic_motion;
                sensoName[c++] = "Motion";
                //     Log.e("Type", "Smoke");

                ct++;


                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALM003")) {

                // mAlarmListGridView = new AlarmListGridView(R.drawable.ic_smoke,"ALS001", "Smoke",number_of_sensor_string_array[ct]);


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_motion, "ALM003", "Motion 3");
                mListGridView.add(mAlarmListGridView);
                SensorImage[count++] = R.drawable.ic_motion;
                sensoName[c++] = "Motion";
                //     Log.e("Type", "Smoke");

                ct++;


                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALM004")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_motion, "ALM004", "Motion 4");
                mListGridView.add(mAlarmListGridView);
                sensoName[c++] = "Motion";
                SensorImage[count++] = R.drawable.ic_motion;
                //   Log.e("Type", "Motion");

                ct++;

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALM005")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_motion, "ALM005", "Motion 5");
                mListGridView.add(mAlarmListGridView);
                sensoName[c++] = "Motion";
                SensorImage[count++] = R.drawable.ic_motion;
                //   Log.e("Type", "Motion");

                ct++;

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALM006")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_motion, "ALM006", "Motion 6");
                mListGridView.add(mAlarmListGridView);
                sensoName[c++] = "Motion";
                SensorImage[count++] = R.drawable.ic_motion;
                //   Log.e("Type", "Motion");

                ct++;

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALM007")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_motion, "ALM007", "Motion 7");
                mListGridView.add(mAlarmListGridView);
                sensoName[c++] = "Motion";
                SensorImage[count++] = R.drawable.ic_motion;
                //   Log.e("Type", "Motion");

                ct++;

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALM008")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_motion, "ALM008", "Motion 8");
                mListGridView.add(mAlarmListGridView);
                sensoName[c++] = "Motion";
                SensorImage[count++] = R.drawable.ic_motion;
                //   Log.e("Type", "Motion");

                ct++;

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALM009")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_motion, "ALM009", "Motion 9");
                mListGridView.add(mAlarmListGridView);
                sensoName[c++] = "Motion";
                SensorImage[count++] = R.drawable.ic_motion;
                //   Log.e("Type", "Motion");

                ct++;

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALM010")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_motion, "ALM0010", "Motion 10");
                mListGridView.add(mAlarmListGridView);
                sensoName[c++] = "Motion";
                SensorImage[count++] = R.drawable.ic_motion;
                //   Log.e("Type", "Motion");

                ct++;

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALG001")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_gas, "ALG001", "GAS 1");
                mListGridView.add(mAlarmListGridView);
                sensoName[c++] = "GAS";
                SensorImage[count++] = R.drawable.ic_gas;
                //   Log.e("Type", "Motion");

                ct++;

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALG002")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_gas, "ALG002", "GAS 2");
                mListGridView.add(mAlarmListGridView);
                sensoName[c++] = "GAS";
                SensorImage[count++] = R.drawable.ic_gas;
                //   Log.e("Type", "Motion");

                ct++;

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALG003")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_gas, "ALG003", "GAS 3");
                mListGridView.add(mAlarmListGridView);
                sensoName[c++] = "GAS";
                SensorImage[count++] = R.drawable.ic_gas;
                //   Log.e("Type", "Motion");

                ct++;

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALG004")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_gas, "ALG004", "GAS 4");
                mListGridView.add(mAlarmListGridView);
                sensoName[c++] = "GAS";
                SensorImage[count++] = R.drawable.ic_gas;
                //   Log.e("Type", "Motion");
                ct++;
                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALG005")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_gas, "ALG005", "GAS 5");
                mListGridView.add(mAlarmListGridView);
                sensoName[c++] = "GAS";
                SensorImage[count++] = R.drawable.ic_gas;
                //   Log.e("Type", "Motion");

                ct++;

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALG006")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_gas, "ALG006", "GAS 6");
                mListGridView.add(mAlarmListGridView);
                sensoName[c++] = "GAS";
                SensorImage[count++] = R.drawable.ic_gas;
                //   Log.e("Type", "Motion");

                ct++;

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALG007")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_gas, "ALG007", "GAS 7");
                mListGridView.add(mAlarmListGridView);
                sensoName[c++] = "GAS";
                SensorImage[count++] = R.drawable.ic_gas;
                //   Log.e("Type", "Motion");

                ct++;

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALG008")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_gas, "ALG008", "GAS 8");
                mListGridView.add(mAlarmListGridView);
                sensoName[c++] = "GAS";
                SensorImage[count++] = R.drawable.ic_gas;
                //   Log.e("Type", "Motion");

                ct++;

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALG009")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_gas, "ALG009", "GAS 9");
                mListGridView.add(mAlarmListGridView);
                sensoName[c++] = "GAS";
                SensorImage[count++] = R.drawable.ic_gas;
                //   Log.e("Type", "Motion");

                ct++;

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALG010")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_gas, "ALG010", "GAS 10");
                mListGridView.add(mAlarmListGridView);
                sensoName[c++] = "GAS";
                SensorImage[count++] = R.drawable.ic_gas;
                //   Log.e("Type", "Motion");

                ct++;

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));


            } else if (s.getAlarmId().equals("ALW001")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_water_leak, "ALW001", "Water Leak 1");
                mListGridView.add(mAlarmListGridView);
                ct++;
                SensorImage[count++] = R.drawable.ic_water_leak;
                sensoName[c++] = "Water Leak";

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));

            } else if (s.getAlarmId().equals("ALW002")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_water_leak, "ALW002", "Water Leak 2");
                mListGridView.add(mAlarmListGridView);
                ct++;
                SensorImage[count++] = R.drawable.ic_water_leak;
                sensoName[c++] = "Water Leak";

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));

            } else if (s.getAlarmId().equals("ALW003")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_water_leak, "ALW003", "Water Leak 3");
                mListGridView.add(mAlarmListGridView);
                ct++;
                SensorImage[count++] = R.drawable.ic_water_leak;
                sensoName[c++] = "Water Leak";

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));

            } else if (s.getAlarmId().equals("ALW004")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_water_leak, "ALW004", "Water Leak 4");
                mListGridView.add(mAlarmListGridView);
                ct++;
                SensorImage[count++] = R.drawable.ic_water_leak;
                sensoName[c++] = "Water Leak";

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));

            } else if (s.getAlarmId().equals("ALW005")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_water_leak, "ALW005", "Water Leak 5");
                mListGridView.add(mAlarmListGridView);
                ct++;
                SensorImage[count++] = R.drawable.ic_water_leak;
                sensoName[c++] = "Water Leak";

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));

            } else if (s.getAlarmId().equals("ALW006")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_water_leak, "ALW006", "Water Leak 6");
                mListGridView.add(mAlarmListGridView);
                ct++;
                SensorImage[count++] = R.drawable.ic_water_leak;
                sensoName[c++] = "Water Leak";

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));

            } else if (s.getAlarmId().equals("ALW007")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_water_leak, "ALW007", "Water Leak 7");
                mListGridView.add(mAlarmListGridView);
                ct++;
                SensorImage[count++] = R.drawable.ic_water_leak;
                sensoName[c++] = "Water Leak";

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));

            } else if (s.getAlarmId().equals("ALW008")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_water_leak, "ALW008", "Water Leak 8");
                mListGridView.add(mAlarmListGridView);
                ct++;
                SensorImage[count++] = R.drawable.ic_water_leak;
                sensoName[c++] = "Water Leak";

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));

            } else if (s.getAlarmId().equals("ALW009")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_water_leak, "ALW009", "Water Leak 9");
                mListGridView.add(mAlarmListGridView);
                ct++;
                SensorImage[count++] = R.drawable.ic_water_leak;
                sensoName[c++] = "Water Leak";

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));

            } else if (s.getAlarmId().equals("ALW010")) {


                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_water_leak, "ALW010", "Water Leak 10");
                mListGridView.add(mAlarmListGridView);
                ct++;
                SensorImage[count++] = R.drawable.ic_water_leak;
                sensoName[c++] = "Water Leak";

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));

            } else {

                Log.e("Comapre alarm id", "No data found");


            }


            /*else if (alarm_id1.equals("AL006")) {

                Log.e("Type", String.valueOf(number_of_sensor_string_array[0]));

                mAlarmListGridView = new AlarmListGridView(R.drawable.ic_water_leak,"AL006", "Water Leak",number_of_sensor_string_array[ct]);
                mListGridView.add(mAlarmListGridView);
                ct++;

                sensoName[c++] = "Water Leak";
                SensorImage[count++] = R.drawable.ic_water_leak;
                Log.e("Type", "Water Leak");

                addCounting++;
                Log.e("loop count", String.valueOf(addCounting));
            }*/


            for (AlarmListGridView alarmListGridView : mListGridView) {

                Log.e("Alarm List", alarmListGridView.getSensorType());
            }

        }

//        for (String alarm_id2 : number_of_sensor_string_array ){
//
//            Log.e("Number of Alarm ", alarm_id2);
//
//        }

        // setupGridView();

        setupGridViewReceylerView();

    }

    public void setupGridViewReceylerView() {


        for (AlarmListGridView alarmListGridView : mListGridView) {

            Log.e("Alarm List", alarmListGridView.getSensorAlarm());

        }

        mRecyclerViewAlarmType.setLayoutManager(new GridLayoutManager(this, 3));
        //  mRecyclerViewAlarmType.setLayoutManager(linearLayoutManager);
        mRecyclerViewAlarmType.setAdapter(mShowAlarmTypeAlarmAdapter);
        mShowAlarmTypeAlarmAdapter.addData(mListGridView);

//        progressDialog.dismiss();

        spotsDialog.cancel();

    }

    @Override
    public void armResponseSuccessful(String message) {


//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//
//        spotsDialog.dismiss();

    }

    @Override
    public void armResponseUnsuccessful(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        spotsDialog.dismiss();

    }

    @Override
    public void fCMUpdateResponseListenerSuccessful(String message) {
        spotsDialog.dismiss();

    }

    @Override
    public void FCMUpdateResponseListenerUnSuccessful(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        spotsDialog.dismiss();

    }

    private List<MenuObject> getMenuObjects() {

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
//        close.setResource(R.drawable.close_icon_small);
        close.setBgColor(1);

        /*MenuObject send = new MenuObject("Door Key");
        send.setResource(R.drawable.lock_key_close);
        send.setBgColor(1);*/

        MenuObject like = new MenuObject("Disarm");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ic_unlock_icon);
        like.setBitmap(b);
        like.setBgColor(1);

//        MenuObject passkey_reset = new MenuObject(passkey_settings);
//        passkey_reset.setResource(R.drawable.passkey_reset);
//        passkey_reset.setBgColor(1);

        /*MenuObject share_pass_key = new MenuObject("Share Pass Key");
        share_pass_key.setResource(R.drawable.share_pass_key);
        share_pass_key.setBgColor(1);*/

//        MenuObject addFr = new MenuObject(lock_battery_alert);
//        BitmapDrawable bd = new BitmapDrawable(getResources(),
//                BitmapFactory.decodeResource(getResources(), R.drawable.lock_battery_full));
//        addFr.setDrawable(bd);
//        addFr.setBgColor(1);

        menuObjects.add(close);
//        menuObjects.add(send);
        menuObjects.add(like);
        //    menuObjects.add(passkey_reset);
//        menuObjects.add(share_pass_key);
        //  menuObjects.add(addFr);
        return menuObjects;
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {

        switch (position) {
//            case 0:
//
//                Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
//                break;
            case 1:
                //Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();

              //  mArmStatusController.start(user_id, "0");

                //   actionLock();

                break;
        }

    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {

    }

    @Override
    public void alarmListResponseSuccessful(List<AlarmList> alarmListResponse) {


        for (AlarmList  s : alarmListResponse) {


            Log.e("Data", s.getAlarmname());
        }

        convertToStringArrayV1(alarmListResponse);
        spotsDialog.dismiss();

    }




    @Override
    public void alarmListResponseUnsuccessful(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        spotsDialog.dismiss();

    }

    @Override
    public void armStatusResponseSuccessful(String message) {

        if (message.equals("1")) {
            mTextViewArmStatus.setText("Host Device Activated ");


        } else if (message.equals("0")) {
            mTextViewArmStatus.setText("Host Device Deactivated " + "\n" + "Slide to Activate !");


        }

    }


    @Override
    public void armStatusResponseUnsuccessful(String message) {

    }

    private void hideItem() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_approve_shared_list).setVisible(false);
        nav_Menu.findItem(R.id.nav_add_remove_drawer).setVisible(false);
        nav_Menu.findItem(R.id.shared_list).setVisible(false);
        nav_Menu.findItem(R.id.sensor_add_remove_history).setVisible(false);
        nav_Menu.findItem(R.id.sensor_add_remove_history).setVisible(false);
        nav_Menu.findItem(R.id.arm_history).setVisible(false);
    }


    //SMS Permission


    /**
     * Optional informative alert dialog to explain the user why the app needs the Read/Send SMS permission
     */
    private void showRequestPermissionsInfoAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.permission_alert_dialog_title);
        builder.setMessage(R.string.permission_dialog_message);
        builder.setPositiveButton(R.string.action_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                requestReadAndSendSmsPermission();
            }
        });
        builder.show();
    }

    /**
     * Runtime permission shenanigans
     */
    private boolean hasReadSmsPermission() {
        return ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_SMS)) {
            Log.d("Permission", "shouldShowRequestPermissionRationale(), no permission requested");
            return;
        }
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS},
                SMS_PERMISSION_CODE);
    }




    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{READ_PHONE_STATE, SEND_SMS}, PERMISSION_REQUEST_CODE);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted) {

                        //    Toast.makeText(MainActivity.this, "Permisson Granted", Toast.LENGTH_SHORT).show();
                        // Snackbar.make(view, "Permission Granted", Snackbar.LENGTH_LONG).show();

                    }
                    else {

                        Toast.makeText(MainActivity.this, "Permisson Denied", Toast.LENGTH_SHORT).show();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{READ_PHONE_STATE, SEND_SMS},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



    // network change
    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkChanges();
    }


    private void configureToolbar() {

        toolBar = findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolBar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    private void configureNavigationDrawer() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                if (value.equals("4")) {


                    if (id == R.id.logout) {

                        logout();

                    }

                }

                else {

                    if (id == R.id.logout) {

                        logout();


                    }

                }


                return false;
            }
        });

//        View header = navView.getHeaderView(0);
//
//        textViewUsername = (TextView) header.findViewById(R.id.text_view_username);
        // textViewUserMail= (TextView)header.findViewById(R.id.text_view_mail);
    }



}

