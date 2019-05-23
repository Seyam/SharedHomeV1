package com.datasoft_iot.tausif.sharedhomev1.model;

public class SpeceficAlarmDataResponse {

    String alarm_name
            ;
    String room_no;
    String alarm_date;
    String alarm_time;

    public String getAlarm_name() {
        return alarm_name;
    }

    public void setAlarm_name(String alarm_name) {
        this.alarm_name = alarm_name;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    public String getAlarm_date() {
        return alarm_date;
    }

    public void setAlarm_date(String alarm_date) {
        this.alarm_date = alarm_date;
    }

    public String getAlarm_time() {
        return alarm_time;
    }

    public void setAlarm_time(String alarm_time) {
        this.alarm_time = alarm_time;
    }

    @Override
    public String toString() {
        return "SpeceficAlarmDataResponse{" +
                "alarm_name='" + alarm_name + '\'' +
                ", room_no='" + room_no + '\'' +
                ", alarm_date='" + alarm_date + '\'' +
                ", alarm_time='" + alarm_time + '\'' +
                '}';
    }
}
