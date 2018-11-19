package com.toolshare.toolshare.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.toolshare.toolshare.db.DbHandler;

import java.sql.SQLDataException;
import java.sql.SQLInput;

public class RequestStatus {
    private int Id;
    private String statusName;

    /*****************************************************************************
     * DB Functions
     *
     */

    // REQUEST STATUS TABLE
    public static final String TABLE_REQUEST_STATUSES = "request_statuses";
    public static final String REQUEST_STATUS_COLUMN_ID = "id";
    public static final String REQUEST_STATUS_COLUMN_NAME = "status_name";

    public static String getStatusByPk(DbHandler dbHandler, int id) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select status_name from request_statuses where id = ?", new String[] {Integer.toString(id)});

        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        }

        return "";
    }
}
