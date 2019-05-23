package com.datasoft_iot.tausif.sharedhomev1.callbackinterface;

public interface FCMUpdateResponseListener {

    void fCMUpdateResponseListenerSuccessful(String message);

    void FCMUpdateResponseListenerUnSuccessful(String message);
}
