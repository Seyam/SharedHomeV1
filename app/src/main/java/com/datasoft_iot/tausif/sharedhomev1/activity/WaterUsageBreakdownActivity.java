package com.datasoft_iot.tausif.sharedhomev1.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.TextView;

import com.datasoft_iot.tausif.sharedhomev1.Database.DataLogger;
import com.datasoft_iot.tausif.sharedhomev1.R;

public class WaterUsageBreakdownActivity extends AppCompatActivity {

    private TextView sink, bathTub, shower, totalWater, date;
    private DataLogger dataLogger;
    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_usage_breakdown);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        configureToolbar();

        dataLogger = new DataLogger(getApplicationContext());

        bathTub = findViewById(R.id.bathtubUsage);
        sink = findViewById(R.id.sinkUsage);
        shower = findViewById(R.id.showerUsage);
        totalWater = findViewById(R.id.totalWater);
        date = findViewById(R.id.txt_date_water);

        date.setText("until "+dataLogger.loadData("update_date"));
        bathTub.setText(dataLogger.loadData("bath_tub")+ " Litre");
        sink.setText(dataLogger.loadData("sink") + " Litre");
        shower.setText(dataLogger.loadData("shower") + " Litre");
        totalWater.setText(dataLogger.loadData("total") + " Litre");
    }

    private void configureToolbar() {
        toolBar = findViewById(R.id.waterBreakdownToolbar);
        toolBar.setTitle("");
        setSupportActionBar(toolBar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
    }
}
