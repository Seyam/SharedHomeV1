package com.datasoft_iot.tausif.sharedhomev1.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = DeviceControl.class, version = 1, exportSchema = false)
public abstract class DeviceControlDatabase extends RoomDatabase {

    public static volatile DeviceControlDatabase INSTANCE;

    public abstract DeviceControlDAO getDeviceControlDAO();

    public static DeviceControlDatabase getInstance(Context context){
        if(INSTANCE == null){
            synchronized (DeviceControlDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DeviceControlDatabase.class, "my_db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
