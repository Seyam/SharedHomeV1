package com.datasoft_iot.tausif.sharedhomev1.model;

public class BatteryStatusListResponse {


    String bettery_status;

    String alarm_name ;

    String  date ;

    String time ;


    public String getBettery_status() {
        return bettery_status;
    }

    public void setBettery_status(String bettery_status) {
        this.bettery_status = bettery_status;
    }

    public String getAlarm_name() {
        return alarm_name;
    }

    public void setAlarm_name(String alarm_name) {
        this.alarm_name = alarm_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "BatteryStatusListResponse{" +
                "bettery_status='" + bettery_status + '\'' +
                ", alarm_name='" + alarm_name + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
