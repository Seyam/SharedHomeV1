package com.datasoft_iot.tausif.sharedhomev1.callbackinterface;

import com.datasoft_iot.tausif.sharedhomev1.model.AlarmList;

import java.util.List;


public interface AlarmListResponseListener {

    void alarmListResponseSuccessful(List <AlarmList> alarmListResponse);

    void alarmListResponseUnsuccessful(String message);

}
