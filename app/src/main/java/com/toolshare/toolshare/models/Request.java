package com.toolshare.toolshare.models;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.toolshare.toolshare.db.DbHandler;

import java.util.Date;

public class Request {
    private int Id;
    private String RequesterId;
    private String OwnerId;
    private User Requester;
    private User Owner;
    private int AdId;
    private Ad Ad;
    private Date RequestedStartDate;
    private Date RequestedEndDate;
    private String DeliveryMethod;
    private int StatusId;

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

    public void setRequestedStartDate(Date date) {
        RequestedStartDate = date;
    }

    public Date getRequestedStartDate() {
        return RequestedStartDate;
    }

    public void setRequestedEndDate(Date date) {
        RequestedEndDate = date;
    }

    public Date getRequestedEndDate() {
        return RequestedEndDate;
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
    public static void addRequest(DbHandler dbHandler, Request request) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(REQUEST_COLUMN_REQUESTER_ID, request.getRequesterId());
        values.put(REQUEST_COLUMN_OWNER_ID, request.getOwnerId());
        values.put(REQUEST_COLUMN_AD_ID, request.getAdId());
        values.put(REQUEST_COLUMN_REQUESTED_START_DATE, request.getRequestedStartDate().toString());
        values.put(REQUEST_COLUMN_REQUESTED_END_DATE, request.getRequestedEndDate().toString());
        values.put(REQUEST_COLUMN_DELIVERY_METHOD, request.getDeliveryMethod());
        values.put(REQUEST_COLUMN_STATUS_ID, request.getStatusId());

        // Inserting Row
        db.insert(TABLE_REQUESTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }
}
