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
import com.datasoft_iot.tausif.sharedhomev1.model.WaterUsageResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.Api;
import com.datasoft_iot.tausif.sharedhomev1.utils.AppConfig;
import com.github.pavlospt.CircleView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WaterShowActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private CircleView circleView;
    private LinearLayout linearLayout;
    private DataLogger dataLogger;
    private String titleText = "";
    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_show);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        linearLayout = findViewById(R.id.linearLayout_water);
        circleView = findViewById(R.id.cv_water);
        frameLayout = findViewById(R.id.frameLayout_Water);
        dataLogger = new DataLogger(getApplicationContext());


            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!(titleText.equals(""))){
                        Intent intent = new Intent(getApplicationContext(), WaterUsageBreakdownActivity.class);
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


        makeRequestForWater();
    }

    private void configureToolbar() {

        toolBar = findViewById(R.id.waterShowToolbar);
        toolBar.setTitle("Water Usage");
        setSupportActionBar(toolBar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
    }

    private void makeRequestForWater() {
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.Base_URL_local)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api myApi = mRetrofit.create(Api.class);

        Call<WaterUsageResponse> call = myApi.getWater("3e3a7d12-6a1a");

        call.enqueue(new Callback<WaterUsageResponse>() {
            @Override
            public void onResponse(Call<WaterUsageResponse> call, Response<WaterUsageResponse> response) {
                Log.e("Has ","response");

                if(!response.isSuccessful()){
                    Log.e("Code: ",Integer.toString(response.code()));
                    Toast.makeText(WaterShowActivity.this, "Code: "+response.code(), Toast.LENGTH_SHORT).show();
                    return; //to leave this method from here
                }

                    Log.e("seyam", response.body().getBath_tub());
                    Log.e("seyam",response.body().getShower());
                    Log.e("seyam",response.body().getSink());
                    Log.e("seyam",response.body().getTotal());
                    Log.e("seyam",response.body().getUpdate_date());

                    titleText = response.body().getTotal()+ " Litre";

                circleView.setTitleText(titleText);

                dataLogger.saveData("bath_tub",response.body().getBath_tub());
                dataLogger.saveData("sink",response.body().getSink());
                dataLogger.saveData("shower",response.body().getShower());
                dataLogger.saveData("total",response.body().getTotal());
                dataLogger.saveData("update_date",response.body().getUpdate_date());

            }

            @Override
            public void onFailure(Call<WaterUsageResponse> call, Throwable t) {
                Snackbar snackbar = Snackbar.make(frameLayout, "Check your internet connection and try again!",Snackbar.LENGTH_LONG);
                snackbar.show();
//                Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
