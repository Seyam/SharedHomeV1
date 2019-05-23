
package com.datasoft_iot.tausif.sharedhomev1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlarmHistoryResponse {

    @SerializedName("alarm_time")
    @Expose
    private String alarmTime;
    @SerializedName("alarm_date")
    @Expose
    private String alarmDate;
    @SerializedName("alarm_name")
    @Expose
    private String alarmName;
    @SerializedName("room_no")
    @Expose
    private String roomNo;

    public AlarmHistoryResponse(String roomName, String alarmName, String alarmDate, String alarmTime)
    {
        this.roomNo=roomName;
        this.alarmName=alarmName;
        this.alarmDate=alarmDate;
        this.alarmTime=alarmTime;
    }


    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(String alarmDate) {
        this.alarmDate = alarmDate;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    @Override
    public String toString() {
        return "AlarmHistoryResponse{" +
                "alarmTime='" + alarmTime + '\'' +
                ", alarmDate='" + alarmDate + '\'' +
                ", alarmName='" + alarmName + '\'' +
                ", roomNo='" + roomNo + '\'' +
                '}';
    }
}
