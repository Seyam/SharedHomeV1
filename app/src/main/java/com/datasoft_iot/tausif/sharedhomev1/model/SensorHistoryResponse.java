
package com.datasoft_iot.tausif.sharedhomev1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SensorHistoryResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("alarmname")
    @Expose
    private String alarmname;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;

    public SensorHistoryResponse(String status, String alarmname, String date, String time)
    {
        this.status=status;
        this.alarmname=alarmname;
        this.date=date;
        this.time=time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAlarmname() {
        return alarmname;
    }

    public void setAlarmname(String alarmname) {
        this.alarmname = alarmname;
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
        return "SensorHistoryResponse{" +
                "status='" + status + '\'' +
                ", alarmname='" + alarmname + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
