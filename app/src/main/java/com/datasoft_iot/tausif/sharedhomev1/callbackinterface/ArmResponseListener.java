package com.datasoft_iot.tausif.sharedhomev1.callbackinterface;

public interface ArmResponseListener {

    void armResponseSuccessful(String message);

    void armResponseUnsuccessful(String message);
}
