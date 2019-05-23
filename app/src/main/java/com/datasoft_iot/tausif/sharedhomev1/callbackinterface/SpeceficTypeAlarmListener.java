package com.datasoft_iot.tausif.sharedhomev1.callbackinterface;

import com.datasoft_iot.tausif.sharedhomev1.model.SpeceficAlarmDataResponse;

import java.util.List;

public interface SpeceficTypeAlarmListener {


    void responseSuccessful( List<SpeceficAlarmDataResponse> speceficTypeAlarmListenerList);

    void responseUnsuccessful(String message);

}
