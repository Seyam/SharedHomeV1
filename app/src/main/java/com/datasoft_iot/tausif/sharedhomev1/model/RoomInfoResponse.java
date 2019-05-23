package com.datasoft_iot.tausif.sharedhomev1.model;

import com.google.gson.annotations.SerializedName;

public class RoomInfoResponse {


    @SerializedName("room_image")
    private String image_url;

    @SerializedName("facility")
    private String facility;

    @SerializedName("no_of_bed_available")
    private Integer no_of_bed_available;

    @SerializedName("no_of_bed_booked")
    private Integer no_of_bed_booked;

    @SerializedName("room")
    private String room;

    @SerializedName("room_kitchen_no")
    private String room_kitchen_no;

    @SerializedName("room_washroom_no")
    private Integer room_washroom_no;

    public RoomInfoResponse( String image_url, String facility, Integer no_of_bed_available, Integer no_of_bed_booked, String room, String room_kitchen_no, Integer room_washroom_no) {

        this.image_url = image_url;
        this.facility = facility;
        this.no_of_bed_available = no_of_bed_available;
        this.no_of_bed_booked = no_of_bed_booked;
        this.room = room;
        this.room_kitchen_no = room_kitchen_no;
        this.room_washroom_no = room_washroom_no;
    }


    public String getTotalBed(){
        return Integer.toString(no_of_bed_booked + no_of_bed_available);
    }

    public String getImage_url() {
        return image_url;
    }

    public String getFacility() {
        return facility;
    }

    public Integer getNo_of_bed_available() {
        return no_of_bed_available;
    }

    public Integer getNo_of_bed_booked() {
        return no_of_bed_booked;
    }

    public String getRoom() {
        return room;
    }

    public String getRoom_kitchen_no() {
        return room_kitchen_no;
    }

    public Integer getRoom_washroom_no() {
        return room_washroom_no;
    }




    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public void setNo_of_bed_available(Integer no_of_bed_available) {
        this.no_of_bed_available = no_of_bed_available;
    }

    public void setNo_of_bed_booked(Integer no_of_bed_booked) {
        this.no_of_bed_booked = no_of_bed_booked;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setRoom_kitchen_no(String room_kitchen_no) {
        this.room_kitchen_no = room_kitchen_no;
    }

    public void setRoom_washroom_no(Integer room_washroom_no) {
        this.room_washroom_no = room_washroom_no;
    }
}
