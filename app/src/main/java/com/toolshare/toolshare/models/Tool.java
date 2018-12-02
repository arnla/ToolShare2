package com.toolshare.toolshare.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.toolshare.toolshare.R;
import com.toolshare.toolshare.db.DbHandler;

import java.io.ByteArrayOutputStream;
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
    private Bitmap Picture;
    private float Rating;

    public Tool() {
        this.Id = -1;
        this.Owner = null;
        this.TypeId = -1;
        this.Name = null;
        this.Year = -1;
        this.Model = null;
        this.Brand = -1;
    }
    
    public Tool(String owner, int typeId, int brandId, String name, int year, String model, Bitmap  image) {
        this.Owner = owner;
        this.TypeId = typeId;
        this.Name = name;
        this.Year = year;
        this.Model = model;
        this.Brand = brandId;
        this.Picture = image;
    }

    public Tool(int id, String owner, int typeId, int brandId, String name, int year, String model, byte[] imageInBytes, float rating) {
        this.Id = id;
        this.Owner = owner;
        this.TypeId = typeId;
        this.Name = name;
        this.Year = year;
        this.Model = model;
        this.Brand = brandId;
        this.Picture = BitmapFactory.decodeByteArray(imageInBytes, 0, imageInBytes.length);
        this.Rating = rating;
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

    public Bitmap getPicture() {  return Picture; }

    public void setPicture(Bitmap picture) { Picture = picture; }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
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
    public static final String TOOL_COLUMN_PICTURE = "picture";
    public static final String TOOL_COLUMN_RATING = "rating";

    public int addTool(DbHandler dbHandler) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        this.getPicture().compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInBytes = stream.toByteArray();

        ContentValues values = new ContentValues();
        values.put(TOOL_COLUMN_OWNER, this.getOwner());
        values.put(TOOL_COLUMN_TYPE_ID, this.getTypeId());
        values.put(TOOL_COLUMN_NAME, this.getName());
        values.put(TOOL_COLUMN_YEAR, this.getYear());
        values.put(TOOL_COLUMN_MODEL, this.getModel());
        values.put(TOOL_COLUMN_BRAND_ID, this.getBrand());
        values.put(TOOL_COLUMN_PICTURE, imageInBytes);
        values.put(TOOL_COLUMN_RATING, this.getRating());

        // Inserting Row
        db.insert("tools", null, values);
        int id = -1;

        Cursor cursor = db.rawQuery("select max(id) from " + TABLE_TOOLS, null);
        if (cursor != null)
            cursor.moveToFirst();

        id = cursor.getInt(0);

        db.close();
        return id;
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
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getBlob(7),
                        cursor.getFloat(8));
                tools.add(tool);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tools;
    }

    public static List<Tool> getAllToolsByOwner(DbHandler dbHandler, String owner) {
        List<Tool> tools = new ArrayList<Tool>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tools where owner = ?", new String[] {owner});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Tool tool = new Tool(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getInt(5),
                        cursor.getString(6),
                        cursor.getBlob(7),
                        cursor.getFloat(8));
                tools.add(tool);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tools;
    }

    public static Tool getToolByPk(DbHandler dbHandler, int id) {
        Tool tool = null;
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tools where id = ?", new String[] {Integer.toString(id)});

        if (cursor.moveToFirst()) {
            tool = new Tool(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getInt(5),
                    cursor.getString(6),
                    cursor.getBlob(7),
                    cursor.getFloat(8));
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

    public static void updateTool(DbHandler dbHandler, Tool tool) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        tool.getPicture().compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInBytes = stream.toByteArray();

        ContentValues values = new ContentValues();
        values.put(TOOL_COLUMN_OWNER, tool.getOwner());
        values.put(TOOL_COLUMN_TYPE_ID, tool.getTypeId());
        values.put(TOOL_COLUMN_NAME, tool.getName());
        values.put(TOOL_COLUMN_YEAR, tool.getYear());
        values.put(TOOL_COLUMN_MODEL, tool.getModel());
        values.put(TOOL_COLUMN_BRAND_ID, tool.getBrand());
        values.put(TOOL_COLUMN_PICTURE, imageInBytes);
        values.put(TOOL_COLUMN_RATING, tool.getRating());

        // updating row
        db.update(TABLE_TOOLS, values, TOOL_COLUMN_ID + " = ?", new String[] {Integer.toString(tool.getId())});
    }

    @Override
    public String toString() {
        return getName() + ": " + getModel();
    }
}
