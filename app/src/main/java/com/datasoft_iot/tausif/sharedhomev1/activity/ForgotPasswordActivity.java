package com.datasoft_iot.tausif.sharedhomev1.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.ForgotPasswordResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.controller.ForgotPasswordRequestController;

import dmax.dialog.SpotsDialog;

public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordResponseListener {

    TextInputLayout textInputLayoutForgotPassword;
    EditText editTextForgotPasswordMobileNumber;
    Button submitForgotPassword;

    ForgotPasswordRequestController forgotPasswordRequestController;

    public static SpotsDialog spotsDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        textInputLayoutForgotPassword = findViewById(R.id.text_input_layout_forgot_password_mobile_number);

        editTextForgotPasswordMobileNumber = findViewById(R.id.edit_text_forgot_password_mobile_number);

        submitForgotPassword = findViewById(R.id.button_forgot_password);

        spotsDialog = new SpotsDialog(this);



        forgotPasswordRequestController = new ForgotPasswordRequestController(this,this);

        submitForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobile_number = editTextForgotPasswordMobileNumber.getText().toString();

                if(mobile_number.isEmpty() ){

                    textInputLayoutForgotPassword.setError("Can't be empty");

                }else if(mobile_number.length()>11){

                    textInputLayoutForgotPassword.setError("Invalid mobile number");


                }


                else{

                    spotsDialog.show();

                    forgotPasswordRequestController.start(mobile_number);


                }



            }
        });


    }

    @Override
    public void forgotPasswordResponseSuccessful(String message) {

        spotsDialog.dismiss();
        Toast.makeText(this, "Please Check your mail", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
        finish();


    }

    @Override
    public void forgotPasswordUnsuccessful(String message) {

        spotsDialog.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
