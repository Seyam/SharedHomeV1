package com.datasoft_iot.tausif.sharedhomev1.callbackinterface;

public interface BaseResponseListener {

    void responseSuccessful(String message);

    void responseUnsuccessful(String message);
}
