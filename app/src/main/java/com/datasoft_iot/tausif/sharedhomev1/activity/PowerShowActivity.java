package com.datasoft_iot.tausif.sharedhomev1.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.datasoft_iot.tausif.sharedhomev1.MqttHelper.MQTTClient;
import com.datasoft_iot.tausif.sharedhomev1.R;
import com.github.pavlospt.CircleView;

import org.eclipse.paho.client.mqttv3.MqttException;

public class PowerShowActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private CircleView circleView;
    private MQTTClient mqttClient;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_show);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        linearLayout = findViewById(R.id.circleViewLayout);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PowerBreakdownActivity.class);
                startActivity(intent);
            }
        });



        circleView = findViewById(R.id.cv_power);
        mqttClient = new MQTTClient(this);

        try {
            mqttClient.connectToMQTT();
            mqttClient.subscribeToMQTT();
        } catch (MqttException e) {
            e.printStackTrace();
        }

        configureToolbar();
    }


    public void updateView(String data){
        String dt[] = data.split(" ");
        circleView.setTitleText(dt[0]+" KWh");
//        circleView.setShowSubtitle(true);
//        circleView.setSubtitleSize(4);
//        circleView.setSubtitleText("KWh");
    }


    private void configureToolbar() {

        toolBar = findViewById(R.id.alarm_history);
        toolBar.setTitle("");
        setSupportActionBar(toolBar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);

    }
}
