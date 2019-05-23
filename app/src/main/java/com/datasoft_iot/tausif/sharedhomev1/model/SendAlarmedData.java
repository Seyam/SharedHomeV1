package com.datasoft_iot.tausif.sharedhomev1.model;

public class SendAlarmedData {

    String mobile_number;
    String user_id;
    String alarm_type;
    String alarm_id;
    String time;
    String date;




    public SendAlarmedData(String mobile_number, String user_id, String alarm_type, String alarm_id, String time, String date) {
        this.mobile_number = mobile_number;
        this.user_id = user_id;
        this.alarm_type = alarm_type;
        this.alarm_id = alarm_id;
        this.time = time;
        this.date = date;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAlarm_type() {
        return alarm_type;
    }

    public void setAlarm_type(String alarm_type) {
        this.alarm_type = alarm_type;
    }

    public String getAlarm_id() {
        return alarm_id;
    }

    public void setAlarm_id(String alarm_id) {
        this.alarm_id = alarm_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
