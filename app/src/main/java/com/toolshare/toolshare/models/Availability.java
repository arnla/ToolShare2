package com.toolshare.toolshare.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.toolshare.toolshare.db.DbHandler;

import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class Availability implements Serializable {
    private int Id;
    private int AdId;
    private boolean Monday;
    private boolean Tuesday;
    private boolean Wednesday;
    private boolean Thursday;
    private boolean Friday;
    private boolean Saturday;
    private boolean Sunday;
    private Date StartDate;
    private Date EndDate;
/*    private Time StartTime;
    private Time EndTime;*/

    public Availability() {

    }

    public Availability(int id, int adId,int sun, int mon, int tue, int wed, int thu, int fri, int sat,
                        String startDate, String endDate) {
        this.Id = id;
        this.AdId = adId;
        this.Monday = mon == 1;
        this.Tuesday = tue == 1;
        this.Wednesday = wed == 1;
        this.Thursday = thu == 1;
        this.Friday = fri == 1;
        this.Saturday = sat == 1;
        this.Sunday = sun == 1;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            date = df.parse(startDate);
            this.StartDate = date;
            date = df.parse(endDate);
            this.EndDate = date;
        } catch (Exception e) {

        }
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public int getAdId() {
        return AdId;
    }

    public void setAdId(int adId) {
        this.AdId = adId;
    }

    public boolean isAvailableMonday() {
        return Monday;
    }

    public void setAvailableMonday(boolean available) {
        Monday = available;
    }

    public boolean isAvailableTuesday() {
        return Tuesday;
    }

    public void setAvailableTuesday(boolean available) {
        Tuesday = available;
    }

    public boolean isAvailableWednesday() {
        return Wednesday;
    }

    public void setAvailableWednesday(boolean available) {
        Wednesday = available;
    }

    public boolean isAvailableThursday() {
        return Thursday;
    }

    public void setAvailableThursday(boolean available) {
        Thursday = available;
    }

    public boolean isAvailableFriday() {
        return Friday;
    }

    public void setAvailableFriday(boolean available) {
        Friday = available;
    }

    public boolean isAvailableSaturday() {
        return Saturday;
    }

    public void setAvailableSaturday(boolean available) {
        Saturday = available;
    }

    public boolean isAvailableSunday() {
        return Sunday;
    }

    public void setAvailableSunday(boolean available) {
        Sunday = available;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        this.StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        this.EndDate = endDate;
    }

/*    public Time getStartTime() {
        return StartTime;
    }

    public void setStartTime(Time time) {
        StartTime = time;
    }

    public Time getEndTime() {
        return EndTime;
    }

    public void setEndTime(Time time) {
        EndTime = time;
    }*/


    /*****************************************************************************
     * DB Functions
     *
     */

    // AD TABLE
    public static final String TABLE_AVAILABILITY = "availability";
    public static final String AVAILABILITY_COLUMN_ID = "id";
    public static final String AVAILABILITY_COLUMN_AD_ID = "ad_id";
    public static final String AVAILABILITY_COLUMN_SUN = "sunday";
    public static final String AVAILABILITY_COLUMN_MON = "monday";
    public static final String AVAILABILITY_COLUMN_TUE = "tuesday";
    public static final String AVAILABILITY_COLUMN_WED = "wednesday";
    public static final String AVAILABILITY_COLUMN_THU = "thursday";
    public static final String AVAILABILITY_COLUMN_FRI = "friday";
    public static final String AVAILABILITY_COLUMN_SAT = "saturday";
    public static final String AVAILABILITY_COLUMN_START_DATE = "start_date";
    public static final String AVAILABILITY_COLUMN_END_DATE = "end_date";
/*    public static final String AVAILABILITY_COLUMN_START_TIME = "start_time";
    public static final String AVAILABILITY_COLUMN_END_TIME = "end_time";*/

    public void addAvailability(DbHandler dbHandler) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AVAILABILITY_COLUMN_AD_ID, this.getAdId());
        values.put(AVAILABILITY_COLUMN_SUN, this.isAvailableSunday());
        values.put(AVAILABILITY_COLUMN_MON, this.isAvailableMonday());
        values.put(AVAILABILITY_COLUMN_TUE, this.isAvailableTuesday());
        values.put(AVAILABILITY_COLUMN_WED, this.isAvailableWednesday());
        values.put(AVAILABILITY_COLUMN_THU, this.isAvailableThursday());
        values.put(AVAILABILITY_COLUMN_FRI, this.isAvailableFriday());
        values.put(AVAILABILITY_COLUMN_SAT, this.isAvailableSaturday());
        values.put(AVAILABILITY_COLUMN_START_DATE, this.getStartDate().toString());
        values.put(AVAILABILITY_COLUMN_END_DATE, this.getEndDate().toString());
/*        values.put(AVAILABILITY_COLUMN_START_TIME, this.getStartTime().toString());
        values.put(AVAILABILITY_COLUMN_END_TIME, this.getEndTime().toString());*/

        // Inserting Row
        db.insert("availability", null, values);
        db.close();
    }

    public static Availability getAvailabilityByPk(DbHandler dbHandler, int id) {
        Availability availability = null;
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from availability where id = ?", new String[] {Integer.toString(id)});

        if (cursor.moveToFirst()) {
            availability = new Availability(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getInt(8),
                    cursor.getString(9),
                    cursor.getString(10));
        }

        cursor.close();
        db.close();
        return availability;
    }

    public static Availability getAvailabilityByAdId(DbHandler dbHandler, int id) {
        Availability availability = null;
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from availability where ad_id = ?", new String[] {Integer.toString(id)});

        if (cursor.moveToFirst()) {
            availability = new Availability(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getInt(8),
                    cursor.getString(9),
                    cursor.getString(10));
        }

        cursor.close();
        db.close();
        return availability;
    }

    public static void deleteAvailabilityByAdId(DbHandler dbHandler, int id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete("availability",
                "ad_id = ?",
                new String[] {Integer.toString(id)});
        db.close();
    }
}
