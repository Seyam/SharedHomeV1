package com.datasoft_iot.tausif.sharedhomev1.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.service.ServiceManager;

public class NetworkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {

        if(checkInternet(context))
        {

            Log.e("Check data connection", "true");

         //   Toast.makeText(context, "Network Available Do operations",Toast.LENGTH_LONG).show();
        }else{

            Log.e("Check data connection", "false");

        }

    }

    boolean checkInternet(Context context) {
        ServiceManager serviceManager = new ServiceManager(context);
        if (serviceManager.isNetworkAvailable()) {
            return true;
        } else {
            return false;
        }
    }

}
