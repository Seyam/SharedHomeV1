package com.datasoft_iot.tausif.sharedhomev1.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.datasoft_iot.tausif.sharedhomev1.model.UserInfo;


public class DatabaseHelper extends SQLiteOpenHelper {

        public static final String DATABASE_NAME = "Smart_Alarm_Cloud.db";
        public static final String TABLE_NAME = "user_info";

        public static final String COL_1 = "ID";
        public static final String COL_2 = "FIRST_NAME";
        public static final String COL_3 = "LAST_NAME";
        public static final String COL_4 = "EMAIL";
        public static final String COL_5 = "MOBILE_NUMBER";
        public static final String COL_6 = "PASSWORD";




    public static final String TABLE_UNSEEN_ALARM_LIST = "UNSEEN_ALARM_LIST";

    public static final String COL_7 = "ID";
    public static final String COL_8 = "ALARM_TYPE";
    public static final String COL_9 = "TIME";
    public static final String COL_10 = "DATE";


    public static final String TABLE_ALARMED_DATA = "ALARMED_DATA";
    public static final String COL_11 = "ID";
    public static final String COL_12 = "MOBILE_NUMBER";
    public static final String COL_13 = "USER_ID";
    public static final String COL_14 = "ALARM_TYPE";
    public static final String COL_15 = "ALARM_ID";
    public static final String COL_16 = "TIME";
    public static final String COL_17 = "DATE";
    public static final String COL_18 = "STATUS";


    //public static final String TABLE_NAME1 = "pass_table";
        private SQLiteDatabase myDatabase;
    private final Context myContext;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.myContext = context;
    }


    /*public DatabaseHelper(Context context) {

            super(context, DATABASE_NAME, null, 1);
            SQLiteDatabase db = this.getWritableDatabase();
        }
*/
        @Override
        public void onCreate(SQLiteDatabase db) {

          //  db.execSQL("create table " + TABLE_NAME1 + " (Password TEXT)");


            db.execSQL("create table " + TABLE_UNSEEN_ALARM_LIST + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,ALARM_TYPE TEXT,TIME TEXT,DATE TEXT)");
            db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,FIRST_NAME TEXT,LAST_NAME TEXT,EMAIL TEXT,MOBILE_NUMBER TEXT,PASSWORD TEXT)");

            db.execSQL("create table " + TABLE_ALARMED_DATA + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,MOBILE_NUMBER TEXT,USER_ID TEXT,ALARM_TYPE TEXT,ALARM_ID TEXT,TIME TEXT,DATE TEXT,STATUS TEXT)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            onCreate(db);
        }
        public boolean insertData(UserInfo userInfo) {

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(COL_2, userInfo.getFirstname());
            values.put(COL_3, userInfo.getLastname());
            values.put(COL_4, userInfo.getEmail());
            values.put(COL_5, userInfo.getMobileNumber());
            values.put(COL_6, userInfo.getPassowrd());

            // Inserting Row
          //  db.insert(TABLE_NAME, null, values);

            long result = db.insert(TABLE_NAME,null ,values);

            if(result == -1)
                return false;
            else
                return true;


        }


    public boolean insertUnseenAlarmData( String alarmType, String time, String date ) {


        Log.e("AlarmType", alarmType);

        Log.e("Date", date);

        Log.e("Time", time);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_8,alarmType);
        values.put(COL_9,time);
        values.put(COL_10,date);

        // Inserting Row
        //  db.insert(TABLE_NAME, null, values);

        long result = db.insert(TABLE_UNSEEN_ALARM_LIST,null ,values);

        if(result == -1)
            return false;
        else
            return true;


    }

    public boolean insertAlarmedData( String mobile_number,
                                      String user_id,
                                      String alarmType,
                                      String alarmId,
                                      String time,
                                      String date,
                                      String status) {


        Log.e("mobile", mobile_number);

        Log.e("AlarmType", alarmType);

        Log.e("Date", date);

        Log.e("Time", time);

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_12,mobile_number);
        values.put(COL_13,user_id);
        values.put(COL_14,alarmType);
        values.put(COL_15,alarmId);
        values.put(COL_16,time);
        values.put(COL_17,date);
        values.put(COL_18,status);


        // Inserting Row
        //  db.insert(TABLE_NAME, null, values);

        long result = db.insert(TABLE_ALARMED_DATA,null ,values);

        if(result == -1)
            return false;
        else
            return true;


    }

    public Cursor getAlarmedList() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_ALARMED_DATA,null);
        return res;
    }


    public Cursor getUnseenAlarmData() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_UNSEEN_ALARM_LIST,null);
        return res;
    }


    public boolean updateData(int uid, int user_id, String smsType, String date, String time, String floorName, String status) {

            String id  = String.valueOf(uid);

            SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,user_id);
        contentValues.put(COL_3, smsType);
        contentValues.put(COL_4, date);
        contentValues.put(COL_5, time);
        contentValues.put(COL_6, floorName);
    //    contentValues.put(COL_7, status);

        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }





    public Cursor getAllData() {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
             return res;
        }


    public Cursor getAllUnseenAlarmList() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_UNSEEN_ALARM_LIST,null);
        return res;
    }
    public Cursor getInformation(DatabaseHelper dop) {

        SQLiteDatabase SQD = dop.getReadableDatabase();
        Cursor res = SQD.rawQuery("select * from "+TABLE_NAME,null);
        return res;


    }

   /* public List<Contact> getImages() {
        List<Contact> MyImages = new ArrayList<>();
        Cursor cursor =
                myDatabase.query(DatabaseHelper.TABLE_NAME, null, null, null, null,
                        null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Contact MyImage = cursorToMyImage(cursor);
            MyImages.add(MyImage);
            cursor.moveToNext();
        }
        cursor.close();
        return MyImages;
    }


    private Contact cursorToMyImage(Cursor cursor) {
        Contact image = new Contact();
        image.set_path(
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_6)));
        *//*image.setTitle(
                cursor.getString(cursor.getColumnIndex(DBhelper.COLUMN_TITLE)));
        image.setDatetime(cursor.getLong(
                cursor.getColumnIndex(DBhelper.COLUMN_DATETIME)));
        image.setDescription(cursor.getString(
                cursor.getColumnIndex(DBhelper.COLUMN_DESCRIPTION)));
        *//*
        return image;
    }
*/
    public Cursor getSingleInformation(DatabaseHelper dop, int id) {

        SQLiteDatabase SQD = dop.getReadableDatabase();

        String[] columns = { COL_1, COL_2, COL_3,
                COL_4, COL_5};

        Cursor CR = SQD.query(TABLE_NAME, columns, COL_1+" =?",new String[] {String.valueOf(id)}, null,null,null,null);
        return CR;

    }








        /*public Integer deleteData(String id){
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete(TABLE_NAME,"ID= ?",new String[] {id});
        }*/

    public boolean delete(DatabaseHelper dop, int id) {
        SQLiteDatabase SQD = dop.getWritableDatabase();
        try {
            int result = SQD.delete(TABLE_NAME, COL_1 + " =?",	new String[] { String.valueOf(id) });
            if (result > 0)
                return true;
        } catch (android.database.SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUnseenAll(DatabaseHelper dop) {
        SQLiteDatabase SQD = dop.getWritableDatabase();
        try {
            SQD.execSQL("delete from "+ TABLE_UNSEEN_ALARM_LIST);
            return true;
        } catch (android.database.SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteAlarmedData(DatabaseHelper dop) {
        SQLiteDatabase SQD = dop.getWritableDatabase();
        try {
            SQD.execSQL("delete from "+ TABLE_ALARMED_DATA);
            return true;
        } catch (android.database.SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean deleteAll(DatabaseHelper dop) {
        SQLiteDatabase SQD = dop.getWritableDatabase();
        try {
            SQD.execSQL("delete from "+ TABLE_NAME);
            return true;
        } catch (android.database.SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from list_table where id="+id+"", null );
        return res;
    }




}
