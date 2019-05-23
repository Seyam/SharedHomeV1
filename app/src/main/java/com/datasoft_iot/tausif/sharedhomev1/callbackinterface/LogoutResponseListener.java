package com.datasoft_iot.tausif.sharedhomev1.callbackinterface;

public interface LogoutResponseListener {

    void logoutResponseSuccessful(String message);

    void logoutResponseUnsuccessful(String message);
}
