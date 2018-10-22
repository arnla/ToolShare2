package com.toolshare.toolshare.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.toolshare.toolshare.db.DbHandler;

public class ToolType {
    private int Id;
    private String Type;
    private String Description;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }


    /*****************************************************************************
     * DB Functions
     *
     */

    // TOOL TYPE TABLE
    public static final String TABLE_TOOL_TYPES = "tool_types";
    public static final String TOOL_TYPE_COLUMN_ID = "id";
    public static final String TOOL_TYPE_COLUMN_TYPE = "type";
    public static final String TOOL_TYPE_COLUMN_DESCRIPTION = "description";

    public void addToolType(DbHandler dbHandler) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id", getNextId(dbHandler)); // Id
        values.put("type", "Saws"); // Tool type
        values.put("description", "Cutting tools"); // Description

        // Inserting Row
        db.insert("tool_types", null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public void deleteToolType(DbHandler dbHandler) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.rawQuery("delete from tool_types where id = ?", new String[] {"0"}).moveToFirst();
        db.close();
    }

    private int getNextId(DbHandler dbHandler) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Cursor cursor = db.rawQuery("select max(id) from tool_types", null);
        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(0) + 1;
    }
}
