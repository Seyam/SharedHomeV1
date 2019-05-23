package com.datasoft_iot.tausif.sharedhomev1.service;

import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.sharedpreferance.MyPreferences;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    MyPreferences mMyPreferences;
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("Token",refreshedToken);

        mMyPreferences = MyPreferences.getPreferences(this);
        mMyPreferences.setFCMToken(refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //  sendRegistrationToServer(refreshedToken);
    }

}
