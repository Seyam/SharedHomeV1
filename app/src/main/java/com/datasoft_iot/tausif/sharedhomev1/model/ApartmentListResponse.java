package com.datasoft_iot.tausif.sharedhomev1.model;

import com.google.gson.annotations.SerializedName;

public class ApartmentListResponse {

    @SerializedName("apartment_name")
    private String apartment_name;

    @SerializedName("address")
    private String address;

    @SerializedName("description")
    private String description;

    @SerializedName("contact")
    private String contact;

    @SerializedName("image_url")
    private String image_url;

    @SerializedName("no_of_bed")
    private Integer no_of_room;

    @SerializedName("avg_fare")
    private String avg_fare;

    @SerializedName("rating")
    private Float rating;

    @SerializedName("apartment_id")
    private String apartment_id;


        public ApartmentListResponse(String apartment_name, String address, String description, String contact, String image_url, Integer no_of_room, String avg_fare, Float rating, String apartment_id) {
        this.apartment_name = apartment_name;
        this.address = address;
        this.description = description;
        this.contact = contact;
        this.image_url = image_url;
        this.no_of_room = no_of_room;
        this.avg_fare = avg_fare;
        this.rating = rating;
        this.apartment_id = apartment_id;
    }

    public String getApartment_name() {
        return apartment_name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getContact() {
        return contact;
    }

    public String getImage_url() {
        return image_url;
    }

    public Integer getNo_of_room() {
        return no_of_room;
    }

    public String getAvg_fare() {
        return avg_fare;
    }

    public Float getRating() {
        return rating;
    }

    public String getApartment_id() {
        return apartment_id;
    }
}
