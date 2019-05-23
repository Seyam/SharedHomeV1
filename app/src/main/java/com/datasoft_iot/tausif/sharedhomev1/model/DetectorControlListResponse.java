package com.datasoft_iot.tausif.sharedhomev1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetectorControlListResponse {



    @SerializedName("alarm_list")
    @Expose
    private List<DetectorControlList> alarmList = null;

    public List<DetectorControlList> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(List<DetectorControlList> alarmList) {
        this.alarmList = alarmList;
    }


    @Override
    public String toString() {
        return "DetectorControlListResponse{" +
                "alarmList=" + alarmList +
                '}';
    }
}
