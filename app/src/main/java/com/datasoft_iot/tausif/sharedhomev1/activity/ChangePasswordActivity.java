package com.datasoft_iot.tausif.sharedhomev1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.callbackinterface.ChangePasswordResponseListener;
import com.datasoft_iot.tausif.sharedhomev1.controller.ChangePasswordController;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;
import com.datasoft_iot.tausif.sharedhomev1.utils.AppConfig;

import dmax.dialog.SpotsDialog;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordResponseListener {


    EditText mEditTextCurrentPassword, mEditTextNewPassword, mEditTextConfirmNewPassword;

    TextInputLayout mTextInputLayoutCurrentPassword;
    TextInputLayout mTextInputLayoutNewPassword;
    TextInputLayout mTextInputLayoutConfirmNewPassword;
    Button mButtonSubmit;
    String currentPassword, newPassword, confirmNewPassword;

    public static SpotsDialog spotsDialog;


    MyPreferences mMyPreferences;

    ChangePasswordController mChangePasswordController;

    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mEditTextCurrentPassword = findViewById(R.id.edit_text_current_password);

        mEditTextNewPassword = findViewById(R.id.edit_text_new_password);

        mEditTextConfirmNewPassword = findViewById(R.id.edit_text_confirm_new_password);

        mButtonSubmit = findViewById(R.id.button_user_submit);

        spotsDialog = new SpotsDialog(this);


        mTextInputLayoutCurrentPassword = findViewById(R.id.text_input_layout_current_password);
        mTextInputLayoutNewPassword = findViewById(R.id.text_input_layout_new_password);
        mTextInputLayoutConfirmNewPassword = findViewById(R.id.text_input_layout_confirm_new_password);


        mChangePasswordController = new ChangePasswordController(this, this);

        mMyPreferences= MyPreferences.getPreferences(this);

        configureToolbar();

        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (AppConfig.isNetworkAvailable(ChangePasswordActivity.this)) {

                    currentPassword = mEditTextCurrentPassword.getText().toString();
                    newPassword = mEditTextNewPassword.getText().toString();
                    confirmNewPassword = mEditTextConfirmNewPassword.getText().toString();

                    if (currentPassword.isEmpty()) {

                        mTextInputLayoutCurrentPassword.setError("Can't be Empty");

                    } else if (newPassword.isEmpty()) {

                        mTextInputLayoutNewPassword.setError("Can't be Empty");
                    } else if (confirmNewPassword.isEmpty()) {

                        mTextInputLayoutConfirmNewPassword.setError("Can't be Empty");

                    }else if ( ! newPassword.equals(confirmNewPassword) ){


                        mTextInputLayoutNewPassword.setError("Doesn't match" );
                    }else {


                        spotsDialog.show();

                        mChangePasswordController.start(mMyPreferences.getUserID(),newPassword, currentPassword);

                    }


                } else {

                    Toast.makeText(ChangePasswordActivity.this, "Please check your data connection !", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void configureToolbar() {

        toolBar = findViewById(R.id.alarm_history);
        toolBar.setTitle("");
        setSupportActionBar(toolBar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public void changePasswordResponseSuccessful(String message) {

        startActivity(new Intent(ChangePasswordActivity.this,LoginActivity.class));
        finish();
        spotsDialog.dismiss();

    }

    @Override
    public void changePasswordResponseUnsuccessful(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        spotsDialog.dismiss();
    }
}
