package com.toolshare.toolshare.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.toolshare.toolshare.db.DbHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tool implements Serializable {
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
    
    public Tool(String owner, int typeId, String name, int year, String model, int brand) {
        this.Owner = owner;
        this.TypeId = typeId;
        this.Name = name;
        this.Year = year;
        this.Model = model;
        this.Brand = brand;
    }

    public Tool(int id, String owner, int typeId, String name, int year, String model, int brand) {
        this.Id = id;
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
    public static final String TOOL_COLUMN_BRAND_ID = "brand_id";

    public void addTool(DbHandler dbHandler) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOOL_COLUMN_OWNER, this.getOwner());
        values.put(TOOL_COLUMN_TYPE_ID, this.getTypeId());
        values.put(TOOL_COLUMN_NAME, this.getName());
        values.put(TOOL_COLUMN_YEAR, this.getYear());
        values.put(TOOL_COLUMN_MODEL, this.getModel());
        values.put(TOOL_COLUMN_BRAND_ID, this.getBrand());

        // Inserting Row
        db.insert("tools", null, values);
        db.close();
    }

    public List<Tool> getAllTools(DbHandler dbHandler) {
        List<Tool> tools = new ArrayList<Tool>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tools", null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Tool tool = new Tool(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getInt(6));
                tools.add(tool);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tools;
    }

    public List<Tool> getAllToolsByOwner(DbHandler dbHandler, String owner) {
        List<Tool> tools = new ArrayList<Tool>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tools where owner = ?", new String[] {owner});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Tool tool = new Tool(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getInt(6));
                tools.add(tool);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tools;
    }

    public Tool getToolByPk(DbHandler dbHandler, int id) {
        Tool tool = null;
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tools where id = ?", new String[] {Integer.toString(id)});

        if (cursor.moveToFirst()) {
            tool = new Tool(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getString(5),
                    cursor.getInt(6));
        }

        cursor.close();
        db.close();
        return tool;
    }

    public void deleteTool(DbHandler dbHandler, int id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete("tools",
                "id = ?",
                new String[] {Integer.toString(id)});
        db.close();
    }
}
