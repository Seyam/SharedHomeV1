package com.datasoft_iot.tausif.sharedhomev1.model;

public class SensorType {

    String sensor_Id;
    String sensorName;

    public SensorType(String sensor_Id, String sensorName) {
        this.sensor_Id = sensor_Id;
        this.sensorName = sensorName;
    }

    public String getSensor_Id() {
        return sensor_Id;
    }

    public void setSensor_Id(String sensor_Id) {
        this.sensor_Id = sensor_Id;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }
}
