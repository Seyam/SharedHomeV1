package com.datasoft_iot.tausif.sharedhomev1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class AlarmListResponse {



    @SerializedName("alarm_list")
    @Expose
    private List<AlarmList> alarmList = null;

    public List<AlarmList> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(List<AlarmList> alarmList) {
        this.alarmList = alarmList;
    }

}
