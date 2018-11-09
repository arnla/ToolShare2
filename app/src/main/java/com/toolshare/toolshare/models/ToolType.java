package com.toolshare.toolshare.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;

import com.toolshare.toolshare.db.DbHandler;

import java.util.ArrayList;
import java.util.List;

public class ToolType {
    private int Id;
    private String Type;
    private String Description;

    public ToolType(DbHandler dbHandler, String type, String description) {
        Id = getNextId(dbHandler);
        this.Type = type;
        this.Description = description;
    }

    public ToolType(DbHandler dbHandler, int id, String type, String description) {
        this.Id = id;
        this.Type = type;
        this.Description = description;
    }


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

    @Override
    public String toString() {
        return Type;
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
        values.put("type", this.getType()); // Tool type
        values.put("description", this.getDescription()); // Description

        // Inserting Row
        db.insert("tool_types", null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    public void deleteToolType(DbHandler dbHandler) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.rawQuery("delete from tool_types where id = ?", new String[] {Integer.toString(this.getId())}).moveToFirst();
        db.close();
    }

    private int getNextId(DbHandler dbHandler) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Cursor cursor = db.rawQuery("select max(id) from tool_types", null);
        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(0) + 1;
    }

    public static List<ToolType> getAllToolTypes(DbHandler dbHandler) {
        List<ToolType> toolTypes = new ArrayList<ToolType>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tool_types", null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ToolType toolType = new ToolType(dbHandler, cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                toolTypes.add(toolType);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return toolTypes;
    }
}
