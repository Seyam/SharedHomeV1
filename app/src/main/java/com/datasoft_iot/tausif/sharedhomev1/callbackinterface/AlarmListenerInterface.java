package com.datasoft_iot.tausif.sharedhomev1.callbackinterface;

import com.datasoft_iot.tausif.sharedhomev1.model.AlarmHistoryResponse;

import java.util.List;

public interface AlarmListenerInterface {
    void alarmListenerInterfaceSuccessful(List<AlarmHistoryResponse> alarmListResponse_);
    void alarmListenerInterfaceUnsuccessful(String message);
}
