package com.toolshare.toolshare.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.toolshare.toolshare.db.DbHandler;

public class Tool {
    private int Id;
    private String Owner;
    private int TypeId;
    private String Name;
    private int Year;
    private String Model;
    private int Brand;

    public Tool() {
        this.Id = -1;
        this.Owner = null;
        this.TypeId = -1;
        this.Name = null;
        this.Year = -1;
        this.Model = null;
        this.Brand = -1;
    }
    
    public Tool(DbHandler dbHandler, String owner, int typeId, String name, int year, String model, int brand) {
        Id = getNextId(dbHandler);
        this.Owner = owner;
        this.TypeId = typeId;
        this.Name = name;
        this.Year = year;
        this.Model = model;
        this.Brand = brand;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        this.Owner = owner;
    }

    public int getTypeId() {
        return TypeId;
    }

    public void setTypeId(int typeId) {
        this.TypeId = typeId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        this.Year = year;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        this.Model = model;
    }

    public int getBrand() {
        return Brand;
    }

    public void setBrand(int brand) {
        this.Brand = brand;
    }


    /*****************************************************************************
     * DB Functions
     *
     */

    // TOOL TABLE
    public static final String TABLE_TOOLS = "tools";
    public static final String TOOL_COLUMN_ID = "id";
    public static final String TOOL_COLUMN_OWNER = "owner";
    public static final String TOOL_COLUMN_TYPE_ID = "type_id";
    public static final String TOOL_COLUMN_NAME = "name";
    public static final String TOOL_COLUMN_YEAR = "year";
    public static final String TOOL_COLUMN_MODEL = "model";
    public static final String TOOL_COLUMN_BRAND = "brand";

    private int getNextId(DbHandler dbHandler) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Cursor cursor = db.rawQuery("select max(id) from tools", null);
        if (cursor != null)
            cursor.moveToFirst();

        return cursor.getInt(0) + 1;
    }
}
