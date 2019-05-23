package com.datasoft_iot.tausif.sharedhomev1.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.datasoft_iot.tausif.sharedhomev1.Database.DataLogger;
import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.adapter.ShowAlarmTypeAlarmAdapter;
import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.LogoutResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.model.AlarmListGridView;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MainActivityV2 extends AppCompatActivity implements LogoutResponseListener {

    private DrawerLayout drawerLayout;
    Toolbar toolBar;

    public static SpotsDialog spotsDialog;

    MyPreferences mMyPreferences;

    // To check UserType
    String value;


    String user_id="USER036";
    String fcm_token;

    TextView mTextViewUserName;

    int[] SensorImage = new int[40];
    int count, c;
    String[] sensoName = new String[40];
    AlarmListGridView mAlarmListGridView;
    List<AlarmListGridView> mListGridView = new ArrayList<>();
    ShowAlarmTypeAlarmAdapter mShowAlarmTypeAlarmAdapter;
    RecyclerView mRecyclerViewAlarmType;

    NavigationView navView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_v2);

        spotsDialog = new SpotsDialog(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mRecyclerViewAlarmType = findViewById(R.id.recyclerview_alarm_type_actvitiy);

        mMyPreferences = MyPreferences.getPreferences(this);

        mShowAlarmTypeAlarmAdapter = new ShowAlarmTypeAlarmAdapter(this);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        View header = ((NavigationView) findViewById(R.id.nav_view)).getHeaderView(0);


        mTextViewUserName = findViewById(R.id.text_view_user_name);


        String name[] = mMyPreferences.getUserName().split(" ");

//        mTextViewUserName.setText("Welcome ," + " " +name[0]);
        mTextViewUserName.setText("Welcome!");


        configureToolbar();
        configureNavigationDrawer();

        generateDataForGridViews();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.search_menu, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.search);
//
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setQueryHint(getString(R.string.search_hint));
//        searchView.setOnQueryTextListener(this);
//        searchView.setIconified(false);
//
//        this.menu = menu;


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void configureToolbar() {

        toolBar = findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolBar);
        toolBar.setTitleTextColor(Color.WHITE);
        ActionBar actionbar = getSupportActionBar();;
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionbar.setDisplayHomeAsUpEnabled(true);
    }

    private void configureNavigationDrawer() {

        navView = (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();


                    if (id == R.id.book) {

                        drawerLayout.closeDrawer(GravityCompat.START);

                        Intent intent = new Intent(getApplicationContext(), LocationSearchActivity.class);
                        startActivity(intent);

//                        logout();

                    }

                    else if (id == R.id.logout) {

                        drawerLayout.closeDrawer(GravityCompat.START);
                        logout();

//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                // Do something after 5s = 5000ms
////                                Toast.makeText(MainActivityV2.this, "yes", Toast.LENGTH_SHORT).show();
//
//
//                            }
//                        }, 150);

                    }
                    else if(id == R.id.power){

                        drawerLayout.closeDrawer(GravityCompat.START);

                        Intent intentAlarm = new Intent(MainActivityV2.this, PowerShowActivity2.class);
                        startActivity(intentAlarm);
                    }


                    else if(id == R.id.water){

                        drawerLayout.closeDrawer(GravityCompat.START);

                        Intent intentAlarm = new Intent(MainActivityV2.this, WaterShowActivity.class);
                        startActivity(intentAlarm);
                    }

                    else if(id == R.id.gas){

                        drawerLayout.closeDrawer(GravityCompat.START);

                        Intent intentAlarm = new Intent(MainActivityV2.this, GasUsageShowActivity.class);
                        startActivity(intentAlarm);
                    }


                return false;
            }
        });


    }

    private void logout() {

        drawerLayout.closeDrawer(GravityCompat.START);
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Log out now?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        clearSharedPref();
                        Intent intentAlarm = new Intent(MainActivityV2.this, LoginActivity.class);
                        intentAlarm.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentAlarm);
//                        finish();

                    }

                    private void clearSharedPref() {
                        DataLogger dataLogger = new DataLogger(getApplicationContext());
                        dataLogger.clearData();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        //2. now setup to change color of the button

//        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
//            @Override
//            public void onShow(DialogInterface dialogInterface) {
//                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.red2));
//            }
//        });
        alertDialog.show();

    }

    private void hideItem() {
        navView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navView.getMenu();
        nav_Menu.findItem(R.id.nav_approve_shared_list).setVisible(false);
        nav_Menu.findItem(R.id.nav_add_remove_drawer).setVisible(false);
        nav_Menu.findItem(R.id.shared_list).setVisible(false);
        nav_Menu.findItem(R.id.sensor_add_remove_history).setVisible(false);
        nav_Menu.findItem(R.id.sensor_add_remove_history).setVisible(false);
        nav_Menu.findItem(R.id.arm_history).setVisible(false);
        nav_Menu.findItem(R.id.nav_detector_control_drawer).setVisible(false);
        nav_Menu.findItem(R.id.power).setVisible(false);

    }


    private void generateDataForGridViews() {

        mAlarmListGridView = new AlarmListGridView(R.drawable.ic_oven, "ALM001", "Oven");
        mListGridView.add(mAlarmListGridView);
        mAlarmListGridView = new AlarmListGridView(R.drawable.ic_ac, "ALM002", "AC");
        mListGridView.add(mAlarmListGridView);
        mAlarmListGridView = new AlarmListGridView(R.drawable.ic_light, "ALM003", "Bed Light");
        mListGridView.add(mAlarmListGridView);
        mAlarmListGridView = new AlarmListGridView(R.drawable.ic_kettle, "ALM004", "Kettle");
        mListGridView.add(mAlarmListGridView);


        setupGridViewRecylerView();


    }


    public void setupGridViewRecylerView() {


        for (AlarmListGridView alarmListGridView : mListGridView) {

            Log.e("Device List: ", alarmListGridView.getSensorType());

        }

        mRecyclerViewAlarmType.setLayoutManager(new GridLayoutManager(this, 3));
        //  mRecyclerViewAlarmType.setLayoutManager(linearLayoutManager);
        mRecyclerViewAlarmType.setAdapter(mShowAlarmTypeAlarmAdapter);
        mShowAlarmTypeAlarmAdapter.addData(mListGridView);

//        progressDialog.dismiss();

        spotsDialog.dismiss();

    }



    @Override
    public void logoutResponseSuccessful(String message) {

        mMyPreferences.setLoginStatus(false);
        mMyPreferences.setUserId("");


        //Starting login activity
        Intent intent = new Intent(MainActivityV2.this, UserActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void logoutResponseUnsuccessful(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        logout();
    }
}
