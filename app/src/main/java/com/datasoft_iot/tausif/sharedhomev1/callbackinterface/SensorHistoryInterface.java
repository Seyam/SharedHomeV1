package com.datasoft_iot.tausif.sharedhomev1.callbackinterface;
import com.datasoft_iot.tausif.sharedhomev1.model.SensorHistoryResponse;

import java.util.List;

public interface SensorHistoryInterface {

    void sensorHistoryInterfaceSuccessful(List<SensorHistoryResponse> sensorHistoryInterfaces);
    void sensorHistoryInterfaceUnsuccessful(String message);
}
