package com.datasoft_iot.tausif.sharedhomev1.callbackinterface;

public interface DetectorControlCodeResponseListener {

    void detectorControlCodeSuccessful(String message);

    void detectorControlCodeUnsuccessful(String message);
}
