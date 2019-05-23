package com.datasoft_iot.tausif.sharedhomev1.callbackinterface;

public interface AlarmedDataResponseListener {

    void alarmedDataResponseSuccessful(String message);

    void alarmedDataResponseUnsuccessful(String message);
}
