package com.datasoft_iot.tausif.sharedhomev1.model;

public class UnseenAlarm {

    String alarmType;

    String date;

    String time;


    public UnseenAlarm(String alarmType, String time, String date) {
        this.alarmType = alarmType;
        this.date = date;
        this.time = time;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
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
        return "UnseenAlarm{" +
                "alarmType='" + alarmType + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
