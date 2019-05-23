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
import android.widget.Toast;
import com.datasoft_iot.tausif.sharedhomev1.R;
import com.datasoft_iot.tausif.sharedhomev1.db.DatabaseHelper;
import com.datasoft_iot.tausif.sharedhomev1.model.UserInfo;
import com.datasoft_iot.tausif.sharedhomev1.network.Api;
import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;
import com.datasoft_iot.tausif.sharedhomev1.utils.AppConfig;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    Button mButtonNext;

    //qr code scanner object
    private IntentIntegrator qrScan;
    private boolean isShared=false;

    EditText mEditTextFirstName;
    EditText mEditTextLastName;
    EditText mEditTextEmail;
    EditText mEditTextMobile;
    EditText mEditTextPassword;
    EditText mEditTextConfirmPassword;

    TextInputLayout mTextInputLayoutFirstName;
    TextInputLayout mTextInputLayoutLastName;
    TextInputLayout mTextInputLayoutEmail;
    TextInputLayout mTextInputLayoutMobile;
    TextInputLayout mTextInputLayoutPassword;
    TextInputLayout mTextInputLayoutComfirmPassword;

    String firstName;
    String lastName;
    String email;
    String mobile_number;
    String passowrd;
    String confirmPassowrd;
//    CheckBox mCheckBox;

    UserInfo mUserInfo;
    DatabaseHelper mDatabaseHelper;

    MyPreferences mMyPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mButtonNext = findViewById(R.id.btn_next);
        mEditTextFirstName = findViewById(R.id.edit_text_first_name);
        mEditTextLastName = findViewById(R.id.edit_Text_last_name);
        mEditTextMobile = findViewById(R.id.edit_text_mobile_number);
        mEditTextEmail = findViewById(R.id.edit_text_email);
        mEditTextPassword = findViewById(R.id.edit_text_password);
        mEditTextConfirmPassword = findViewById(R.id.edit_text_confirm_password);


        mMyPreferences = MyPreferences.getPreferences(this);

//        mCheckBox = (CheckBox) findViewById(R.id.checkbox_existing_user);



        mTextInputLayoutFirstName = findViewById(R.id.text_input_layout_first_name);
        mTextInputLayoutLastName = findViewById(R.id.text_input_layout_last_name);
        mTextInputLayoutEmail = findViewById(R.id.text_input_layout_email);
        mTextInputLayoutMobile = findViewById(R.id.text_input_layout_mobile_number);
        mTextInputLayoutPassword = findViewById(R.id.text_input_layout_password);
        mTextInputLayoutComfirmPassword = findViewById(R.id.text_input_layout_confirm_password);


        //intializing scan object
//        qrScan = new IntentIntegrator(this);

        mButtonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppConfig.hideKeyboard(SignUpActivity.this);

                openDashboardActivity();
                Toast.makeText(SignUpActivity.this, "Booking Confirmed!", Toast.LENGTH_SHORT).show();


                /**if (validateEditTextField()) {

                    Toast.makeText(SignUpActivity.this, "Booking Confirmed!", Toast.LENGTH_SHORT).show();
                    //Network call
//                    openDashboardActivity();

                }**/
            }
        });


        mEditTextFirstName.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mTextInputLayoutFirstName.setError(null);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        mEditTextLastName.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mTextInputLayoutLastName.setError(null);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });


        mEditTextMobile.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mTextInputLayoutMobile.setError(null);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        mEditTextEmail.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mTextInputLayoutEmail.setError(null);
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
                    mTextInputLayoutPassword.setError(null);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }

        });

        mEditTextConfirmPassword.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mEditTextConfirmPassword.setError(null);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {

            }

        });


    }

    private void openDashboardActivity() {
        Intent intent  = new Intent(this, MainActivityV2.class);
        startActivity(intent);
    }


    //For check box
 /**   public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
          //  case R.id.checkbox_new_user:
//                if (checked)
//                  Toast.makeText(this, "Checked", Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(this, "Unchecked", Toast.LENGTH_SHORT).show();
               // break;
            case R.id.checkbox_existing_user:
                if (checked) {
                    isShared=true;
                } else{
                    isShared=false;
                }
                break;
        }
    }**/


    public boolean validateEditTextField() {


        firstName = mEditTextFirstName.getText().toString();
        lastName = mEditTextLastName.getText().toString();
        email = mEditTextEmail.getText().toString();
        mobile_number = mEditTextMobile.getText().toString();
        confirmPassowrd = mEditTextConfirmPassword.getText().toString();
        passowrd = mEditTextPassword.getText().toString();

        if (firstName.isEmpty()) {
            mTextInputLayoutFirstName.setError("Can't be empty");
            return false;
        }

        else if(!AppConfig.validateFirstName(firstName)){

            mTextInputLayoutFirstName.setError("Please provide a valid name !");
            return false;
        }

        else if (lastName.isEmpty()) {
            mTextInputLayoutLastName.setError("Can't be empty");
            return false;

        }
        else if(!AppConfig.validateLastName(lastName)){
            mTextInputLayoutLastName.setError("Please provide a valid name !");
            return false;
        }

        else if (email.isEmpty()) {
            mTextInputLayoutEmail.setError("Can't be empty");
            return false;

        }
        else if (!AppConfig.isEmailValid(email)) {
            mTextInputLayoutEmail.setError("Please provide a valid email");
            return false;

        }

        else if (mobile_number.isEmpty()) {
            mTextInputLayoutMobile.setError("Can't be empty");
            return false;

        } else if (passowrd.isEmpty()) {
            mTextInputLayoutPassword.setError("Can't be empty");
            return false;

        } else if (passowrd.length()<6) {
                mTextInputLayoutPassword.setError("length must be greater than or equal 6 ");
                return false;

        } else if (confirmPassowrd.isEmpty()) {
            mTextInputLayoutComfirmPassword.setError("Can't be empty");
            return false;

        } else if (!passowrd.equals(confirmPassowrd)) {
            mTextInputLayoutPassword.setError("Password doesn't match");
            return false;

        }


        return true;
    }


    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                //Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                String fullQr=result.getContents();
                Log.e("Full Qr",fullQr);
                String[] qrArray=fullQr.split(",");
                Log.e("Qr Array",qrArray[0]);
                isQrFree(qrArray[0]);

                // Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void isQrFree(final String qr){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api=retrofit.create(Api.class);


        final String addCode = "DMA"+qr;

        mMyPreferences.setQRCode(addCode);

        Log.e("Riday", mMyPreferences.getQrcode());

        Call<ResponseBody> call = api.get_qr_status(addCode);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String s=null;
                try {
                    s=response.body().string();
                    Log.e("Response",s);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(s!=null){
                    try {
                        JSONObject jsonObject=new JSONObject(s);
                        int status=jsonObject.getInt("status");

                        String message=jsonObject.getString("message");


                        if(status==200&&message.equals("available")){


                        }else{
                            Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                Toast.makeText(SignUpActivity.this,throwable.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
