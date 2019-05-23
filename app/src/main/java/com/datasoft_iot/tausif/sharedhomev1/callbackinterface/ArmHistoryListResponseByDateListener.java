package com.datasoft_iot.tausif.sharedhomev1.callbackinterface;

import com.datasoft_iot.tausif.sharedhomev1.model.ArmHistoryResponse;

import java.util.List;


public interface ArmHistoryListResponseByDateListener {

    void armHistoryListResponseByDateSuccessful(List<ArmHistoryResponse> armHistoryResponse);

    void armHistoryListResponseByDateUnsuccessful(String message);

}
