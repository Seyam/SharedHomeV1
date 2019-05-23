package com.datasoft_iot.tausif.sharedhomev1.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.datasoft_iot.tausif.sharedhomev1.Database.DataLogger;
import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.model.PowerBreakdownResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.Api;
import com.datasoft_iot.tausif.sharedhomev1.utils.AppConfig;
import com.github.pavlospt.CircleView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PowerShowActivity2 extends AppCompatActivity {

    private Toolbar toolBar;
    private CircleView circleView;
    private LinearLayout linearLayout;
    private DataLogger dataLogger;
    private String titleText = "";
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_show);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        linearLayout = findViewById(R.id.circleViewLayout);
        circleView = findViewById(R.id.cv_power);
        frameLayout = findViewById(R.id.frameLayout_Power);
        dataLogger = new DataLogger(getApplicationContext());

        circleView.setTitleText(titleText);


            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    Log.e("title",circleView.getTitleText());
//                    Toast.makeText(PowerShowActivity2.this, circleView.getTitleText().getBytes().toString(), Toast.LENGTH_SHORT).show();


                    if(!(titleText.equals(""))){
                        Intent intent = new Intent(getApplicationContext(), PowerBreakdownActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Snackbar snackbar = Snackbar.make(frameLayout, "Check your internet connection and try again!",Snackbar.LENGTH_LONG);
                        snackbar.show();
//                        Toast.makeText(getApplicationContext(), "Check your internet connection.", Toast.LENGTH_SHORT).show();
                    }

                }
            });




        configureToolbar(); //You must set the NoAction Bar theme from the manifest file to see this line in effect


        makeRequestForPower();
    }

    private void makeRequestForPower() {

        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.Base_URL_local)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api myApi = mRetrofit.create(Api.class);

        Call<PowerBreakdownResponse> call = myApi.getPower("3e3a7d12-6a1a");

        call.enqueue(new Callback<PowerBreakdownResponse>() {
            @Override
            public void onResponse(Call<PowerBreakdownResponse> call, Response<PowerBreakdownResponse> response) {
                Log.e("Has ","response");

                if(!response.isSuccessful()){
                    Log.e("Code: ",Integer.toString(response.code()));
                    Toast.makeText(PowerShowActivity2.this, "Code: "+response.code(), Toast.LENGTH_SHORT).show();
                    return; //to leave this method from here
                }

                titleText = response.body().getTotal()+ " KWh";

                circleView.setTitleText(titleText);

                dataLogger.saveData("date",response.body().getUpdate_date());
                dataLogger.saveData("ac",response.body().getAc());
                dataLogger.saveData("kettle",response.body().getKettle());
                dataLogger.saveData("light",response.body().getLight());
                dataLogger.saveData("oven",response.body().getOven());
                dataLogger.saveData("total",response.body().getTotal());


//                    Log.e("seyam", response.body().getAc());
//                    Log.e("seyam",response.body().getKettle());
//                    Log.e("seyam",response.body().getTotal());
//                    Log.e("seyam",response.body().getLight());
//                    Log.e("seyam",response.body().getUpdate_date());

            }

            @Override
            public void onFailure(Call<PowerBreakdownResponse> call, Throwable t) {
                Snackbar snackbar = Snackbar.make(frameLayout, "Check your internet connection and try again!",Snackbar.LENGTH_LONG);
                snackbar.show();
//                Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void configureToolbar() {

        toolBar = findViewById(R.id.powerShowToolbar);
        toolBar.setTitle("Power Usage");
        toolBar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolBar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);

    }
}


