package com.datasoft_iot.tausif.sharedhomev1.model;

public class ArmHistoryResponse {

    String alarm_name;
    String warm_status;
    String arm_date;
    String arm_time;

    @Override
    public String toString() {
        return "ArmHistoryResponse{" +
                "alarm_name='" + alarm_name + '\'' +
                ", warm_status='" + warm_status + '\'' +
                ", arm_date='" + arm_date + '\'' +
                ", arm_time='" + arm_time + '\'' +
                '}';
    }


    public ArmHistoryResponse(String alarm_name, String warm_status, String arm_date, String arm_time) {
        this.alarm_name = alarm_name;
        this.warm_status = warm_status;
        this.arm_date = arm_date;
        this.arm_time = arm_time;
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

    public String getAlarm_name() {
        return alarm_name;
    }

    public void setAlarm_name(String alarm_name) {
        this.alarm_name = alarm_name;
    }
}
