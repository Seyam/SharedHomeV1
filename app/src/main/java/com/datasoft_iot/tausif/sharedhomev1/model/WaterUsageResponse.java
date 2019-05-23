package com.datasoft_iot.tausif.sharedhomev1.model;

import com.google.gson.annotations.SerializedName;

public class WaterUsageResponse {


    @SerializedName("bath_tub")
    private String bath_tub;

    @SerializedName("shower")
    private String shower;

    @SerializedName("sink")
    private String sink;

    @SerializedName("total")
    private String total;

    @SerializedName("update_date")
    private String update_date;

    @SerializedName("update_time")
    private String update_time;

    public WaterUsageResponse(String bath_tub, String shower, String sink, String total, String update_date, String update_time) {
        this.bath_tub = bath_tub;
        this.shower = shower;
        this.sink = sink;
        this.total = total;
        this.update_date = update_date;
        this.update_time = update_time;
    }

    public String getBath_tub() {
        return bath_tub;
    }

    public String getShower() {
        return shower;
    }

    public String getSink() {
        return sink;
    }

    public String getTotal() {
        return total;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public String getUpdate_time() {
        return update_time;
    }
}
