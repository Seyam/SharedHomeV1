package com.datasoft_iot.tausif.sharedhomev1.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface DeviceControlDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<DeviceControl> deviceList);

    @Query("SELECT * FROM device_table")
    Single<List<DeviceControl>> fetchAll();  //could be a list of  NotificationResponse if many rows exist in the database


    @Query("SELECT * FROM device_table WHERE row_id =:taskId")
    Single<DeviceControl> getById(int taskId);


    @Update
    void updateTask(List<DeviceControl> allListData);


    @Delete
    void deleteTask(List<DeviceControl> allListData);


    @Query("DELETE FROM device_table")
    void destroyTable();
}
