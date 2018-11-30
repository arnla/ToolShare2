package com.toolshare.toolshare.models;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.toolshare.toolshare.db.DbHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ToolSchedule {
    private int Id;
    private int RequestId;
    private Date Date;
    private String Status;

    public void setId(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
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
    public static final String TOOL_SCHEDULE_COLUMN_REQUEST_ID = "request_id";
    public static final String TOOL_SCHEDULE_COLUMN_DATE = "date";
    public static final String TOOL_SCHEDULE_COLUMN_STATUS = "status";


    public static void insertToolSchedule(DbHandler dbHandler, ToolSchedule toolSchedule) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOOL_SCHEDULE_COLUMN_REQUEST_ID, toolSchedule.getRequestId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        values.put(TOOL_SCHEDULE_COLUMN_DATE, formatter.format(toolSchedule.getDate()));
        values.put(TOOL_SCHEDULE_COLUMN_STATUS, toolSchedule.getStatus());

        // Inserting Row
        db.insert(TABLE_TOOL_SCHEDULE, null, values);
        db.close();
    }
}
