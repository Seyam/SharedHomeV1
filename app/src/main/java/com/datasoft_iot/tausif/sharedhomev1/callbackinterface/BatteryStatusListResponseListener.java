package com.datasoft_iot.tausif.sharedhomev1.callbackinterface;

import com.datasoft_iot.tausif.sharedhomev1.model.BatteryStatusListResponse;

import java.util.List;

public interface BatteryStatusListResponseListener {

    void batteyStatusListResponseSuccessful(List<BatteryStatusListResponse> batteryStatusListResponseList);

    void batteryListResponseUnsuccessful(String message);
}
