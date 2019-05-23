package com.datasoft_iot.tausif.sharedhomev1.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


//some of the needed imports
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


import com.datasoft_iot.tausif.sharedhomev1.R;

public class DatepickerActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    //private field variables in the Activity class
    private EditText etDate;
    private TextView checkIn;
    private TextView checkOut;
    boolean checkInDateFlag=false;
    boolean checkOutDateFlag=false;
    private Button button;
    private DatePickerDialog datePickerDialog;
    String date,checkInDate,checkOutDate, room_uniq_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datepicker_2);


        checkIn = (TextView) findViewById(R.id.textViewFromDateShow_alarm);
        checkOut =(TextView) findViewById(R.id.textViewToDateShow_alarm);
        button = (Button) findViewById(R.id.buttonSearch);

        room_uniq_id = getIntent().getStringExtra("room_uniq_id");




        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(DatepickerActivity.this, "checkin", Toast.LENGTH_SHORT).show();
                showDatePickerDialog();
                checkInDateFlag = true;
            }
        });

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(DatepickerActivity.this, "checkout", Toast.LENGTH_SHORT).show();
                showDatePickerDialog();
                checkOutDateFlag = true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDate();
            }
        });



    }



    private long convertDateStringToMillis(String input) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long milliseconds = date.getTime();
        long millisecondsFromNow = milliseconds - (new Date()).getTime();
        //Toast.makeText(this, "Milliseconds to future date="+millisecondsFromNow, Toast.LENGTH_SHORT).show();
        return millisecondsFromNow;
    }

    private void makeSearchRequest() {

        openFareBreakDownActivity();




    }

    private void openFareBreakDownActivity() {
        Intent intent = new Intent(getApplicationContext(), FareBreakDownActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("checkInDate",checkInDate);
        bundle.putString("checkOutDate",checkOutDate);
        bundle.putString("room_uniq_id",room_uniq_id);


        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void showDatePickerDialog(){
        datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        date = dayOfMonth+"/"+(month+1)+"/"+year;
        if(checkInDateFlag){
            checkIn.setText(date);
            checkIn.setError(null);
            checkInDate=String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(dayOfMonth);
            checkInDateFlag = false;
        }
        else {
            checkOut.setText(date);
            checkOut.setError(null);
            checkOutDate = String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(dayOfMonth);
            checkOutDateFlag = false;
        }
//        Toast.makeText(this, date ,Toast.LENGTH_SHORT).show();
    }



    private void checkDate() {
        if (checkIn.getText().toString().equals("") || checkOut.getText().toString().equals(""))
        {
            checkIn.setError(checkIn.getText().toString().equals("") ? "Empty Field" : null);
            checkOut.setError(checkOut.getText().toString().equals("") ? "Empty Field" : null);
            Toast.makeText(getApplicationContext(),"Field(s) can not be empty",Toast.LENGTH_SHORT).show();
        }

        else {

            long startDatemillis = convertDateStringToMillis(checkInDate);
            long endDatemillis = convertDateStringToMillis(checkOutDate);

            if (startDatemillis>endDatemillis)
            {
                Toast.makeText(getApplicationContext(),"Check-out date must be after check-in date!",Toast.LENGTH_LONG).show();
                checkOut.setError(checkOut.getText().toString().equals("") ? "Enter Valid Date" : null);
                checkIn.setError(checkIn.getText().toString().equals("") ? "Enter Valid Date" : null);
                // textViewFromDateShow.setError(textViewFromDateShow.getText().toString().equals("") ? "" : null);
            }
            else
            {
//                Log.e("---arm Status11","--entered---"+startDate+"---"+endDate);
                checkIn.setError(null);
                checkOut.setError(null);

                makeSearchRequest();
            }

        }
    }
}
