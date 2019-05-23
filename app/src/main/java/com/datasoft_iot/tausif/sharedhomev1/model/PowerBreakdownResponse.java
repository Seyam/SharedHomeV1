package com.datasoft_iot.tausif.sharedhomev1.model;

import com.google.gson.annotations.SerializedName;

public class PowerBreakdownResponse {

    @SerializedName("ac")
    private String ac;

    @SerializedName("light")
    private String light;

    @SerializedName("washing_machine")
    private String washing_machine;

    @SerializedName("kettle")
    private String kettle;

    @SerializedName("oven")
    private String oven;

    @SerializedName("total")
    private String total;

    @SerializedName("update_date")
    private String update_date;

    @SerializedName("update_time")
    private String update_time;

    public PowerBreakdownResponse(String ac, String light, String washing_machine, String kettle, String oven, String total, String update_date, String update_time) {
        this.ac = ac;
        this.light = light;
        this.washing_machine = washing_machine;
        this.kettle = kettle;
        this.oven = oven;
        this.total = total;
        this.update_date = update_date;
        this.update_time = update_time;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public String getAc() {
        return ac;
    }

    public String getLight() {
        return light;
    }

    public String getWashing_machine() {
        return washing_machine;
    }

    public String getKettle() {
        return kettle;
    }

    public String getOven() {
        return oven;
    }

    public String getTotal() {
        return total;
    }
}
