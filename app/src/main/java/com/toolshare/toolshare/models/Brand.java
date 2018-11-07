package com.toolshare.toolshare.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.toolshare.toolshare.db.DbHandler;

import java.util.ArrayList;
import java.util.List;

public class Brand {
    private int Id;
    private String Name;

    public Brand(int id, String name) {
        this.Id = id;
        this.Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    @Override
    public String toString() {
        return Name;
    }

    /*****************************************************************************
     * DB Functions
     *
     */

    public static List<Brand> getAllBrands(DbHandler dbHandler) {
        List<Brand> brands = new ArrayList<Brand>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from brands", null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Brand brand = new Brand(cursor.getInt(0), cursor.getString(1));
                brands.add(brand);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return brands;
    }

    public static Brand getBrandByPk(DbHandler dbHandler, int id) {
        Brand brand = null;
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from brands where id = ?", new String[] {Integer.toString(id)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            brand = new Brand(cursor.getInt(0), cursor.getString(1));
        }

        cursor.close();
        db.close();
        return brand;
    }
}
