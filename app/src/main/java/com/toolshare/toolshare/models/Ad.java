package com.toolshare.toolshare.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.AdapterView;

import com.toolshare.toolshare.db.DbHandler;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.toolshare.toolshare.models.Availability.deleteAvailabilityByAdId;
import static com.toolshare.toolshare.models.Availability.getAvailabilityByAdId;

public class Ad implements Serializable {
    private int Id;
    private String Owner;
    private int ToolId;
    private Date PostDate;
    private Date ExpirationDate;
    private String Description;
    private Availability Availability;
    private int AvailabilityId;
    private String Title;
    private int Price;

    public Ad() {

    }

    public Ad(int id, String owner, int toolId, String postDate, String expirationDate, String title, String description, int price) {
        this.Id = id;
        this.Owner = owner;
        this.ToolId = toolId;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            date = df.parse(postDate);
            this.PostDate = df.parse(postDate);
            date = df.parse(expirationDate);
            this.ExpirationDate = date;
        } catch (Exception e) {

        }
        this.Description = description;
        this.Title = title;
        this.Price = price;
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

    public int getToolId() {
        return ToolId;
    }

    public void setToolId(int toolId) {
        this.ToolId = toolId;
    }

    public Date getPostDate() {
        return PostDate;
    }

    public void setPostDate(Date postDate) {
        this.PostDate = postDate;
    }

    public Date getExpirationDate() {
        return ExpirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.ExpirationDate = expirationDate;
    }

    public String getDescription() { return Description; }

    public void setDescription(String description) { this.Description = description; }

    public Availability getAvailability() {
        return Availability;
    }

    public void setAvailability(Availability availability) {
        this.Availability = availability;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public int getAvailabilityId() {
        return AvailabilityId;
    }

    public void setAvailabilityId(int id) {
        AvailabilityId = id;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    /*****************************************************************************
     * DB Functions
     *
     */

    // AD TABLE
    // AD TABLE
    public static final String TABLE_ADS = "ads";
    public static final String AD_COLUMN_ID = "id";
    public static final String AD_COLUMN_OWNER = "owner";
    public static final String AD_COLUMN_TOOL_ID = "tool_id";
    public static final String AD_COLUMN_AVAILABILITY_ID = "tool_availability_id";
    public static final String AD_COLUMN_POST_DATE = "post_date";
    public static final String AD_COLUMN_EXPIRATION_DATE = "expiration_date";
    public static final String AD_COLUMN_DESCRIPTION = "description";
    public static final String AD_COLUMN_TITLE = "title";
    public static final String AD_COLUMN_PRICE = "price";

    public static List<Ad> getAllAdsByOwner(DbHandler dbHandler, String owner) {
        List<Ad> ads = new ArrayList<Ad>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ads where owner = ?", new String[] {owner});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Ad ad = new Ad(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7));
                ad.setAvailability(getAvailabilityByAdId(dbHandler, ad.getId()));
                ads.add(ad);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ads;
    }

    public int addAd(DbHandler dbHandler) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AD_COLUMN_OWNER, this.getOwner());
        values.put(AD_COLUMN_TOOL_ID, this.getToolId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        values.put(AD_COLUMN_POST_DATE, formatter.format(this.getPostDate()));
        values.put(AD_COLUMN_EXPIRATION_DATE, formatter.format(this.getExpirationDate()));
        values.put(AD_COLUMN_TITLE, this.getTitle());
        values.put(AD_COLUMN_DESCRIPTION, this.getDescription());
        values.put(AD_COLUMN_PRICE, this.getPrice());

        // Inserting Row
        db.insert("ads", null, values);
        int id = -1;

        Cursor cursor = db.rawQuery("select max(id) from ads", null);
        if (cursor != null)
            cursor.moveToFirst();

        id =  cursor.getInt(0);

        db.close();

        return id;
    }

    public static void deleteAd(DbHandler dbHandler, int id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        // Inserting Row
        db.delete("ads",
                "id = ?",
                new String[] {Integer.toString(id)});
        deleteAvailabilityByAdId(dbHandler, id);
        db.close();
    }

    public static List<Ad> getAllAdsThatAreNotOwners(DbHandler dbHandler, String owner) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        List<Ad> ads = new ArrayList<Ad>();

        Cursor cursor = db.rawQuery("select * from ads where not owner = ?", new String[] {owner});

        if (cursor.moveToFirst()) {
            do {
                Ad ad = new Ad(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7));
                ad.setAvailability(getAvailabilityByAdId(dbHandler, ad.getId()));
                ads.add(ad);
            } while (cursor.moveToNext());
        }

        return ads;
    }

    public static Ad getAdByPk(DbHandler dbHandler, int id) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_ADS + " where " + AD_COLUMN_ID + " = ?", new String[] {Integer.toString(id)});

        if (cursor.moveToFirst()) {
            Ad ad = new Ad(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getInt(7));
            ad.setAvailability(getAvailabilityByAdId(dbHandler, ad.getId()));

            return ad;
        }

        return null;
    }
}
