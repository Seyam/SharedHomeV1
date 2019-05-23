package com.datasoft_iot.tausif.sharedhomev1.model;

public class AlarmListGridView {

    int sensorImage;
    String sensorAlarm;
    String sensorType;
    String noOfSensor;

//    public AlarmListGridView(int sensorImage, String sensorAlarm, String sensorType, String noOfSensor) {
//        this.sensorImage = sensorImage;
//        this.sensorAlarm = sensorAlarm;
//        this.sensorType = sensorType;
//        this.noOfSensor = noOfSensor;
//    }

    public AlarmListGridView(int sensorImage, String sensorAlarm, String sensorType) {
        this.sensorImage = sensorImage;
        this.sensorAlarm = sensorAlarm;
        this.sensorType = sensorType;
       // this.noOfSensor = noOfSensor;
    }


    public int getSensorImage() {
        return sensorImage;
    }

    public void setSensorImage(int sensorImage) {
        this.sensorImage = sensorImage;
    }

    public String getSensorAlarm() {
        return sensorAlarm;
    }

    public void setSensorAlarm(String sensorAlarm) {
        this.sensorAlarm = sensorAlarm;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getNoOfSensor() {
        return noOfSensor;
    }

    public void setNoOfSensor(String noOfSensor) {
        this.noOfSensor = noOfSensor;
    }
}
