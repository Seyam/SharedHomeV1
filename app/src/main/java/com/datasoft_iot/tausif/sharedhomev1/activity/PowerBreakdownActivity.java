package com.datasoft_iot.tausif.sharedhomev1.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.TextView;

import com.datasoft_iot.tausif.sharedhomev1.Database.DataLogger;
import com.datasoft_iot.tausif.sharedhomev1.R;

public class PowerBreakdownActivity extends AppCompatActivity {

    private TextView ac, kettle, light, oven, total, date;
    private DataLogger dataLogger;
    private Toolbar toolBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power_breakdown);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        configureToolbar();

        dataLogger = new DataLogger(getApplicationContext());

        ac = findViewById(R.id.acPower);
        kettle = findViewById(R.id.kettlePower);
        light = findViewById(R.id.lightPower);
        oven = findViewById(R.id.ovenPower);
        total = findViewById(R.id.totalPower);
        date = findViewById(R.id.txt_date);

        date.setText("until "+dataLogger.loadData("date"));
        ac.setText(dataLogger.loadData("ac")+ " KWh");
        kettle.setText(dataLogger.loadData("kettle") + " KWh");
        light.setText(dataLogger.loadData("light") + " KWh");
        oven.setText(dataLogger.loadData("oven") + " KWh");
        total.setText(dataLogger.loadData("total") + " KWh");


    }

    private void configureToolbar() {
        toolBar = findViewById(R.id.powerBreakdownToolbar);
        toolBar.setTitle("");
        setSupportActionBar(toolBar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
    }
}
