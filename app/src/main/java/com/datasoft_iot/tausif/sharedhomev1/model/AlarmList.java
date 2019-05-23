
package com.datasoft_iot.tausif.sharedhomev1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlarmList {

    @SerializedName("alarmname")
    @Expose
    private String alarmname;
    @SerializedName("alarm_id")
    @Expose
    private String alarmId;

    public String getAlarmname() {
        return alarmname;
    }

    public void setAlarmname(String alarmname) {
        this.alarmname = alarmname;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

}
