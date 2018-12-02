package com.toolshare.toolshare.models;


import java.io.Serializable;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.toolshare.toolshare.db.DbHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.toolshare.toolshare.models.Ad.getAdByPk;


public class Notification implements Serializable {
    private int Id;
    private String OwnerId;
    private User Owner;
    private int RequestId;
    private Request Request;
    private int StatusId;
    private int ViewingStatus;
    private Date DateCreated;

    public Notification(){
    }

    public Notification(String ownerId, int requestId, int statusId, int viewingStatus, String dateCreated) {
        OwnerId = ownerId;
        RequestId = requestId;
        StatusId = statusId;
        ViewingStatus = viewingStatus;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            date = df.parse(dateCreated);
            DateCreated = date;
        } catch(Exception e) {}
    }

    public Notification(int id, String ownerId, int requestId, int statusId, int viewingStatus, String dateCreated){
        Id = id;
        OwnerId = ownerId;
        RequestId = requestId;
        StatusId = statusId;
        ViewingStatus = viewingStatus;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            date = df.parse(dateCreated);
            DateCreated = date;
        } catch(Exception e) {}
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        this.OwnerId = ownerId;
    }

    public int getRequestId() {
        return RequestId;
    }

    public void setRequestId(int requestId) {
        RequestId = requestId;
    }

    public com.toolshare.toolshare.models.Request getRequest() {
        return Request;
    }

    public void setRequest(com.toolshare.toolshare.models.Request request) {
        Request = request;
    }

    public void setOwner(User user) {
        Owner = user;
    }

    public User getOwner() {
        return Owner;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        this.StatusId = statusId;
    }

    public int getViewingStatus() { return ViewingStatus; }

    public void setViewingStatus(int viewingStatus){ this.ViewingStatus = viewingStatus; }

    public Date getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        DateCreated = dateCreated;
    }

    /*****************************************************************************
     * DB Functions
     *
     */

    //Notification Table
    public static final String TABLE_NOTIFICATION = "notifications";
    public static final String NOTIFICATION_COLUMN_ID = "id";
    public static final String NOTIFICATION_COLUMN_OWNER_ID = "owner_id";
    public static final String NOTIFICATION_COLUMN_REQUEST_ID = "request_id";
    public static final String NOTIFICATION_COLUMN_STATUS_ID = "status_id";
    public static final String NOTIFICATION_COLUMN_VIEWSTATUS_ID = "read";
    public static final String NOTIFICATION_COLUMN_DATE_CREATED = "date_created";

    public static void addNotification(DbHandler dbHandler, Notification notification) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTIFICATION_COLUMN_OWNER_ID, notification.getOwnerId());
        values.put(NOTIFICATION_COLUMN_REQUEST_ID, notification.getRequestId());
        values.put(NOTIFICATION_COLUMN_STATUS_ID, notification.getStatusId());
        values.put(NOTIFICATION_COLUMN_VIEWSTATUS_ID, notification.getViewingStatus());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        values.put(NOTIFICATION_COLUMN_DATE_CREATED, formatter.format(notification.getDateCreated()));

        db.insert(TABLE_NOTIFICATION, null, values);
        db.close();
    }

    public static List<Notification> getAllNotificationsByOwner(DbHandler dbHandler, String owner){
        List<Notification> notifications = new ArrayList<Notification>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NOTIFICATION + " where " + NOTIFICATION_COLUMN_OWNER_ID + "= ?", new String[]{owner});
        if (cursor.moveToFirst()) {
            do {
                Notification notification = new Notification(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getString(5));
                notification.setOwner(User.getUser(dbHandler, notification.getOwnerId()));
                notification.setRequest(com.toolshare.toolshare.models.Request.getRequestByPk(dbHandler, notification.getRequestId()));
                notifications.add(notification);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notifications;
    }

    public static void updateNotification(DbHandler dbHandler, Notification notification) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTIFICATION_COLUMN_OWNER_ID, notification.getOwnerId());
        values.put(NOTIFICATION_COLUMN_REQUEST_ID, notification.RequestId);
        values.put(NOTIFICATION_COLUMN_STATUS_ID, notification.StatusId);
        values.put(NOTIFICATION_COLUMN_VIEWSTATUS_ID, notification.getViewingStatus());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        values.put(NOTIFICATION_COLUMN_DATE_CREATED, formatter.format(notification.getDateCreated()));

        // updating row
        db.update(TABLE_NOTIFICATION, values, NOTIFICATION_COLUMN_ID + " = ?", new String[] {Integer.toString(notification.getId())});
        db.close();
    }
}


