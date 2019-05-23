package com.datasoft_iot.tausif.sharedhomev1.callbackinterface;

public interface LoginResponseListener {

    void loginResponseSuccessful(String message);

    void loginResponseUnsuccessful(String message);
}
