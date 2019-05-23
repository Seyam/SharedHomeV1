package com.datasoft_iot.tausif.sharedhomev1.model;

import com.google.gson.annotations.SerializedName;

public class RoomAvailabilityResponse {
    @SerializedName("Per_night_cost")
    private String cost;

    @SerializedName("Total cost")
    private String total_cost;

    @SerializedName("Total room rent")
    private String total_rent;

    @SerializedName("no_of_days_of_stay")
    private Integer days_of_stay;

    @SerializedName("service_charge")
    private String service_charge;

    public RoomAvailabilityResponse(String cost, String total_cost, String total_rent, Integer days_of_stay, String service_charge) {
        this.cost = cost;
        this.total_cost = total_cost;
        this.total_rent = total_rent;
        this.days_of_stay = days_of_stay;
        this.service_charge = service_charge;
    }


    public String getCost() {
        return cost;
    }

    public String getTotal_cost() {
        return total_cost;
    }

    public String getTotal_rent() {
        return total_rent;
    }

    public Integer getDays_of_stay() {
        return days_of_stay;
    }

    public String getService_charge() {
        return service_charge;
    }
}
