package com.datasoft_iot.tausif.sharedhomev1.callbackinterface;

public interface ArmStatusResponseListener {

    void armStatusResponseSuccessful(String message);

    void armStatusResponseUnsuccessful(String message);
}
