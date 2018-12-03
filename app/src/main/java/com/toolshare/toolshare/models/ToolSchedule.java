package com.toolshare.toolshare.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.toolshare.toolshare.db.DbHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.toolshare.toolshare.models.Availability.getAvailabilityByAdId;

public class ToolSchedule {
    private int Id;
    private int ToolId;
    private Tool Tool;
    private int RequestId;
    private Date Date;
    private String Status;

    public ToolSchedule() {}

    public ToolSchedule(int toolId, int requestId, String date, String status) {
        ToolId = toolId;
        RequestId = requestId;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new Date();
            d = df.parse(date);
            Date = d;
        } catch (Exception e) {

        }
        Status = status;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }

    public int getToolId() {
        return ToolId;
    }

    public void setToolId(int toolId) {
        ToolId = toolId;
    }

    public com.toolshare.toolshare.models.Tool getTool() {
        return Tool;
    }

    public void setTool(com.toolshare.toolshare.models.Tool tool) {
        Tool = tool;
    }

    public void setRequestId(int requestId) {
        RequestId = requestId;
    }

    public int getRequestId() {
        return RequestId;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


    /*****************************************************************************
     * DB Functions
     *
     */

    // TOOL SCHEDULE TABLE
    public static final String TABLE_TOOL_SCHEDULE = "tool_schedule";
    public static final String TOOL_SCHEDULE_COLUMN_ID = "id";
    public static final String TOOL_SCHEDULE_COLUMN_TOOL_ID = "tool_id";
    public static final String TOOL_SCHEDULE_COLUMN_REQUEST_ID = "request_id";
    public static final String TOOL_SCHEDULE_COLUMN_DATE = "date";
    public static final String TOOL_SCHEDULE_COLUMN_STATUS = "status";


    public static void insertToolSchedule(DbHandler dbHandler, ToolSchedule toolSchedule) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOOL_SCHEDULE_COLUMN_TOOL_ID, toolSchedule.getToolId());
        values.put(TOOL_SCHEDULE_COLUMN_REQUEST_ID, toolSchedule.getRequestId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        values.put(TOOL_SCHEDULE_COLUMN_DATE, formatter.format(toolSchedule.getDate()));
        values.put(TOOL_SCHEDULE_COLUMN_STATUS, toolSchedule.getStatus());

        // Inserting Row
        db.insert(TABLE_TOOL_SCHEDULE, null, values);
        db.close();
    }

    public static List<Date> getBusyDaysByToolId(DbHandler dbHandler, int id) {
        List<Date> dates = new ArrayList<Date>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Cursor cursor = db.rawQuery("select " + TOOL_SCHEDULE_COLUMN_DATE + " from " + TABLE_TOOL_SCHEDULE + " where "
                + TOOL_SCHEDULE_COLUMN_TOOL_ID + " = ? and " + TOOL_SCHEDULE_COLUMN_STATUS + " = ?" ,
                new String[] {Integer.toString(id), "Busy"});

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                try {
                    Date date = new Date();
                    date = df.parse(cursor.getString(0));
                    dates.add(date);
                } catch (Exception e) {

                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return dates;
    }

    public static List<Date> getDaysByRequestId(DbHandler dbHandler, int id) {
        List<Date> dates = new ArrayList<Date>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Cursor cursor = db.rawQuery("select " + TOOL_SCHEDULE_COLUMN_DATE + " from " + TABLE_TOOL_SCHEDULE + " where "
                        + TOOL_SCHEDULE_COLUMN_REQUEST_ID + " = ?" ,
                new String[] {Integer.toString(id)});

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                try {
                    Date date = new Date();
                    date = df.parse(cursor.getString(0));
                    dates.add(date);
                } catch (Exception e) {

                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return dates;
    }

    public static void updateToolScheduleStatus(DbHandler dbHandler, int requestId, String status) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOOL_SCHEDULE_COLUMN_STATUS, status);

        // updating row
        db.update(TABLE_TOOL_SCHEDULE, values, TOOL_SCHEDULE_COLUMN_REQUEST_ID + " = ?", new String[] {Integer.toString(requestId)});

        db.close();
    }
}
