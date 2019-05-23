package com.datasoft_iot.tausif.sharedhomev1.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.datasoft_iot.tausif.sharedhomev1.Database.DataLogger;
import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.model.GasUsageResponse;
import com.datasoft_iot.tausif.sharedhomev1.network.Api;
import com.datasoft_iot.tausif.sharedhomev1.utils.AppConfig;
import com.github.pavlospt.CircleView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GasUsageShowActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private CircleView circleView;
    private LinearLayout linearLayout;
    private DataLogger dataLogger;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_usage_show);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        frameLayout = findViewById(R.id.frameLayout_Gas);
        linearLayout = findViewById(R.id.linearLayout_gas);
        circleView = findViewById(R.id.cv_gas);
        dataLogger = new DataLogger(getApplicationContext());

//        linearLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(GasUsageShowActivity.this, "No data available!", Toast.LENGTH_SHORT).show();
//            }
//        });

        configureToolbar(); //You must set the NoAction Bar theme from the manifest file to see this line in effect


        makeRequestForWater();
    }

    private void configureToolbar() {

        toolBar = findViewById(R.id.gasShowToolbar);
        toolBar.setTitle("Gas Usage");
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

        Call<GasUsageResponse> call = myApi.getGas("3e3a7d12-6a1a");

        call.enqueue(new Callback<GasUsageResponse>() {
            @Override
            public void onResponse(Call<GasUsageResponse> call, Response<GasUsageResponse> response) {
                Log.e("Has ","response");

                if(!response.isSuccessful()){
                    Log.e("Code: ",Integer.toString(response.code()));
                    Toast.makeText(GasUsageShowActivity.this, "Code: "+response.code(), Toast.LENGTH_SHORT).show();
                    return; //to leave this method from here
                }

//                Log.e("seyam", response.body().getBath_tub());
//                Log.e("seyam",response.body().getShower());
//                Log.e("seyam",response.body().getSink());
//                Log.e("seyam",response.body().getTotal());
//                Log.e("seyam",response.body().getUpdate_date());

                circleView.setTitleText(response.body().getTotal_gas()+ " m3");


            }

            @Override
            public void onFailure(Call<GasUsageResponse> call, Throwable t) {
                Snackbar snackbar = Snackbar.make(frameLayout, "Check your internet connection and try again!",Snackbar.LENGTH_LONG);
                snackbar.show();
//                Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
