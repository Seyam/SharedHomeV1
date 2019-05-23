package com.datasoft_iot.tausif.sharedhomev1.callbackinterface;

import com.datasoft_iot.tausif.sharedhomev1.model.ArmHistoryResponse;

import java.util.List;


public interface ArmHistoryListResponseListener {

    void armHistoryListResponseSuccessful(List<ArmHistoryResponse> armHistoryResponse);

    void armHistoryResponseUnsuccessful(String message);

}
