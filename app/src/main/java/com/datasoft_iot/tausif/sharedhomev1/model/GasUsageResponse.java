package com.datasoft_iot.tausif.sharedhomev1.model;

import com.google.gson.annotations.SerializedName;

public class GasUsageResponse {

    @SerializedName("total_gas")
    private String total_gas;

    @SerializedName("update_date")
    private String update_date;

    @SerializedName("update_time")
    private String update_time;

    public GasUsageResponse(String total_gas, String update_date, String update_time) {
        this.total_gas = total_gas;
        this.update_date = update_date;
        this.update_time = update_time;
    }

    public String getTotal_gas() {
        return total_gas;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public String getUpdate_time() {
        return update_time;
    }
}
