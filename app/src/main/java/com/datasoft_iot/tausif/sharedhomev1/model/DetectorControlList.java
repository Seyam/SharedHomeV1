
package com.datasoft_iot.tausif.sharedhomev1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetectorControlList {

    @SerializedName("alarm_name")
    @Expose
    private String alarmname;
    @SerializedName("alarm_id")
    @Expose
    private String alarmId;

    @SerializedName("warm_status")
    @Expose
    private String warm_status;

    @SerializedName("arm_date")
    @Expose
    private String arm_date;

    @SerializedName("arm_time")
    @Expose
    private String arm_time;

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

    public String getWarm_status() {
        return warm_status;
    }

    public void setWarm_status(String warm_status) {
        this.warm_status = warm_status;
    }

    public String getArm_date() {
        return arm_date;
    }

    public void setArm_date(String arm_date) {
        this.arm_date = arm_date;
    }

    public String getArm_time() {
        return arm_time;
    }

    public void setArm_time(String arm_time) {
        this.arm_time = arm_time;
    }

    @Override
    public String toString() {
        return "DetectorControlList{" +
                "alarmname='" + alarmname + '\'' +
                ", alarmId='" + alarmId + '\'' +
                ", warm_status='" + warm_status + '\'' +
                ", arm_date='" + arm_date + '\'' +
                ", arm_time='" + arm_time + '\'' +
                '}';
    }
}
