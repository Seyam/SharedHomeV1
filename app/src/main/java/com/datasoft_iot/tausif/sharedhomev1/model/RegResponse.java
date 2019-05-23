package com.datasoft_iot.tausif.sharedhomev1.model;

public class RegResponse extends  BaseResponse {


    String host_number ;

    String mobile_number;

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getHost_number() {
        return host_number;
    }

    public void setHost_number(String host_number) {
        this.host_number = host_number;
    }

    @Override
    public String toString() {
        return "RegResponse{" +
                "host_number='" + host_number + '\'' +
                ", mobile_number='" + mobile_number + '\'' +
                '}';
    }
}
