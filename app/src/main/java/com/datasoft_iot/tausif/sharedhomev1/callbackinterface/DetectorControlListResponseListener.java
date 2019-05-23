package com.datasoft_iot.tausif.sharedhomev1.callbackinterface;

import com.datasoft_iot.tausif.sharedhomev1.model.DetectorControlList;

import java.util.List;

public interface DetectorControlListResponseListener {

    void detectorAlarmListResponseSuccessful(List<DetectorControlList> alarmListResponse);

    void detectorAlarmListResponseUnsuccessful(String message);
}
