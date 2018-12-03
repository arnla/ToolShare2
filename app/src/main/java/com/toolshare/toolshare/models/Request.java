package com.toolshare.toolshare.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.toolshare.toolshare.db.DbHandler;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.toolshare.toolshare.models.Ad.getAdByPk;

public class Request implements Serializable {
    private int Id;
    private String RequesterId;
    private String OwnerId;
    private User Requester;
    private User Owner;
    private int AdId;
    private Ad Ad;
    private String DeliveryMethod;
    private int StatusId;

    public Request() {

    }

    public Request(String requesterId, String ownerId, int adId, String deliveryMethod, int statusId) {
        RequesterId = requesterId;
        OwnerId = ownerId;
        AdId = adId;
        DeliveryMethod = deliveryMethod;
        StatusId = statusId;
    }

    public Request(int id, String requesterId, String ownerId, int adId, String deliveryMethod, int statusId) {
        Id = id;
        RequesterId = requesterId;
        OwnerId = ownerId;
        AdId = adId;
        DeliveryMethod = deliveryMethod;
        StatusId = statusId;
    }

    public int getId() {
        return Id;
    }

    public void setRequesterId(String id) {
        this.RequesterId = id;
    }

    public String getRequesterId() {
        return RequesterId;
    }

    public void setOwnerId(String id) {
        this.OwnerId = id;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setRequester(User user) {
        Requester = user;
    }

    public User getRequester() {
        return Requester;
    }

    public void setOwner(User user) {
        Owner = user;
    }

    public User getOwner() {
        return Owner;
    }

    public void setAdId(int id) {
        AdId = id;
    }

    public int getAdId() {
        return AdId;
    }

    public void setAd(Ad ad) {
        Ad = ad;
    }

    public Ad getAd() {
        return Ad;
    }

    public void setDeliveryMethod(String method) {
        DeliveryMethod = method;
    }

    public String getDeliveryMethod() {
        return DeliveryMethod;
    }

    public void setStatusId(int id) {
        StatusId = id;
    }

    public int getStatusId() {
        return StatusId;
    }



    /*****************************************************************************
     * DB Functions
     *
     */

    // REQUEST TABLE
    public static final String TABLE_REQUESTS = "requests";
    public static final String REQUEST_COLUMN_ID = "id";
    public static final String REQUEST_COLUMN_REQUESTER_ID = "requester_id";
    public static final String REQUEST_COLUMN_OWNER_ID = "owner_id";
    public static final String REQUEST_COLUMN_AD_ID = "ad_id";
    public static final String REQUEST_COLUMN_REQUESTED_START_DATE = "requested_start_date";
    public static final String REQUEST_COLUMN_REQUESTED_END_DATE = "requested_end_date";
    public static final String REQUEST_COLUMN_DELIVERY_METHOD = "delivery_method";
    public static final String REQUEST_COLUMN_STATUS_ID = "status_id";

    // code to add the new user
    public static int addRequest(DbHandler dbHandler, Request request) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(REQUEST_COLUMN_REQUESTER_ID, request.getRequesterId());
        values.put(REQUEST_COLUMN_OWNER_ID, request.getOwnerId());
        values.put(REQUEST_COLUMN_AD_ID, request.getAdId());
        values.put(REQUEST_COLUMN_DELIVERY_METHOD, request.getDeliveryMethod());
        values.put(REQUEST_COLUMN_STATUS_ID, request.getStatusId());

        // Inserting Row
        db.insert(TABLE_REQUESTS, null, values);
        int id = -1;

        Cursor cursor = db.rawQuery("select max(id) from " + TABLE_REQUESTS, null);
        if (cursor != null)
            cursor.moveToFirst();

        id =  cursor.getInt(0);

        cursor.close();
        db.close(); // Closing database connection
        return id;
    }

    public static List<Request> getAllRequestsByOwner(DbHandler dbHandler, String owner) {
        List<Request> requests = new ArrayList<Request>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_REQUESTS + " where " + REQUEST_COLUMN_OWNER_ID + " = ?", new String[] {owner});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Request request = new Request(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getInt(5));
                request.setAd(getAdByPk(dbHandler, request.getAdId()));
                request.setOwner(User.getUser(dbHandler, request.getOwnerId()));
                request.setRequester(User.getUser(dbHandler, request.getRequesterId()));
                requests.add(request);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return requests;
    }

    public static List<Request> getAllRequestsByRequester(DbHandler dbHandler, String requester) {
        List<Request> requests = new ArrayList<Request>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_REQUESTS + " where " + REQUEST_COLUMN_REQUESTER_ID + " = ?", new String[] {requester});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Request request = new Request(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getInt(5));
                request.setAd(getAdByPk(dbHandler, request.getAdId()));
                request.setOwner(User.getUser(dbHandler, request.getOwnerId()));
                request.setRequester(User.getUser(dbHandler, request.getRequesterId()));
                requests.add(request);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return requests;
    }

    public static void updateRequest(DbHandler dbHandler, Request request) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(REQUEST_COLUMN_REQUESTER_ID, request.getRequesterId());
        values.put(REQUEST_COLUMN_OWNER_ID, request.getOwnerId());
        values.put(REQUEST_COLUMN_AD_ID, request.getAdId());
        values.put(REQUEST_COLUMN_DELIVERY_METHOD, request.getDeliveryMethod());
        values.put(REQUEST_COLUMN_STATUS_ID, request.StatusId);

        // updating row
        db.update(TABLE_REQUESTS, values, REQUEST_COLUMN_ID + " = ?", new String[] {Integer.toString(request.getId())});
        db.close();
    }

    public static Request getRequestByPk(DbHandler dbHandler, int id) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " + TABLE_REQUESTS + " where " + REQUEST_COLUMN_ID + " = ?", new String[] {Integer.toString(id)});
        if (cursor != null)
            cursor.moveToFirst();

        Request request = new Request(cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3),
                cursor.getString(4),
                cursor.getInt(5));
        request.setAd(getAdByPk(dbHandler, request.getAdId()));
        request.setOwner(User.getUser(dbHandler, request.getOwnerId()));
        request.setRequester(User.getUser(dbHandler, request.getRequesterId()));

        cursor.close();
        db.close();

        return request;
    }
}
