package com.datasoft_iot.tausif.sharedhomev1.model;

public class LoginResponse extends  BaseResponse {

    String user_id;
    String user_type;

    String host_number;

    String name ;

    String mobile_number;



    public String getHost_number() {
        return host_number;
    }

    public void setHost_number(String host_number) {
        this.host_number = host_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "user_id='" + user_id + '\'' +
                ", user_type='" + user_type + '\'' +
                ", host_number='" + host_number + '\'' +
                ", name='" + name + '\'' +
                ", mobile_number='" + mobile_number + '\'' +
                '}';
    }
}
