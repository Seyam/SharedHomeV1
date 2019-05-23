package com.datasoft_iot.tausif.sharedhomev1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.LoginResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.controller.LoginRequestController;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;
import com.datasoft_iot.tausif.sharedhomev1.utils.AppConfig;

import dmax.dialog.SpotsDialog;

public class LoginActivity extends AppCompatActivity implements LoginResponseListener {


    Button mButtonUserLogin;
    EditText mEditTextMobileNumber, mEditTextPassword;
    LoginRequestController mLoginRequestController;
    String mobile_number, password;

    TextInputLayout mTextInputLayoutloginMobileNumber;
    TextInputLayout mTextInputLayoutloginPassoword;

    MyPreferences mMyPreferences;

    // SweetAlertDialog pDialog;
    //private NewtonCradleLoading newtonCradleLoading;

    // SweetAlertDialog pDialog;


    public SpotsDialog spotsDialog;

    TextView mTextViewForgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mEditTextMobileNumber = findViewById(R.id.edit_text_user_mobile_number);
        mEditTextPassword = findViewById(R.id.edit_text_user_password);
        mButtonUserLogin = findViewById(R.id.button_user_login);

        mTextViewForgetPassword = findViewById(R.id.text_view_forget_password);

//        mMyPreferences = MyPreferences.getPreferences(this);

        // pDialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE);


        // newtonCradleLoading = (NewtonCradleLoading) findViewById(R.id.newton_cradle_loading);


        //  pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);


        spotsDialog = new SpotsDialog(this);

        mTextInputLayoutloginMobileNumber = findViewById(R.id.text_input_layout_login_mobile_number);
        mTextInputLayoutloginPassoword = findViewById(R.id.text_input_layout_login_password);


//        mLoginRequestController = new LoginRequestController(this, this);

        mButtonUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (AppConfig.isNetworkAvailable(LoginActivity.this)) {
                    String mobile_number = mEditTextMobileNumber.getText().toString();

                    Log.e("Data", mobile_number);


                    password = mEditTextPassword.getText().toString();

                    if (mobile_number.isEmpty()) {

                        mTextInputLayoutloginMobileNumber.setError("Can't be empty");

                    } else if (password.isEmpty()) {

                        mTextInputLayoutloginPassoword.setError("Can't be empty");
                    } else {

//                        Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginActivity.this, MainActivityV2.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

//                        mLoginRequestController.start(mobile_number, password);

//                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                    pDialog.setTitleText("Loading");
//                    pDialog.setCancelable(false);
//                    pDialog.show();

//                        spotsDialog.show();
//                    newtonCradleLoading.start();
//                    newtonCradleLoading.setVisibility(View.VISIBLE);

                        AppConfig.hideKeyboard(LoginActivity.this);
                    }

                }else{

                    Toast.makeText(LoginActivity.this, "Check internet!", Toast.LENGTH_SHORT).show();

                }
            }
        });


        mEditTextMobileNumber.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mTextInputLayoutloginMobileNumber.setError(null);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        mEditTextPassword.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mTextInputLayoutloginPassoword.setError(null);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });


        mTextViewForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));

                //Toast.makeText(LoginActivity.this, "Coming Soon !", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public void loginResponseSuccessful(String message) {

        //  newtonCradleLoading.stop();

        // Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

//        pDialog.cancel();

            spotsDialog.dismiss();

            mMyPreferences.setUserType(message);
//            Intent intent = new Intent(LoginActivity.this, MainActivityV2.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);



        }




    @Override
    public void loginResponseUnsuccessful(String message) {

        //   newtonCradleLoading.stop();
        spotsDialog.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
}
