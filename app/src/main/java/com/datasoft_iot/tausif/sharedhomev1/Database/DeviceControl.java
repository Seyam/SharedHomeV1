package com.datasoft_iot.tausif.sharedhomev1.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import io.reactivex.annotations.NonNull;


@Entity(tableName = "device_table")
public class DeviceControl {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "row_id")
    @SerializedName("id")
    private Integer id;


    @ColumnInfo(name = "devId_table")
    @SerializedName("devId")
    private String devId;

    @ColumnInfo(name = "status_table")
    @SerializedName("status")
    private Integer status;


    public DeviceControl(Integer id, String devId, Integer status) {
        this.id = id;
        this.devId = devId;
        this.status = status;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
