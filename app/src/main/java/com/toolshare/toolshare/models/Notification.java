package com.toolshare.toolshare.models;


import java.io.Serializable;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.toolshare.toolshare.db.DbHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.toolshare.toolshare.models.Ad.getAdByPk;


public class Notification implements Serializable {
    private int Id;
    private String OwnerId;
    private String RequesterId;
    private User Owner;
    private User Requester;
    private int StatusId;
    private int ViewingStatus;

    public Notification(){
    }

    public Notification(int id, String ownerId, String requesterId, int statusId, int viewingStatus){
        Id = id;
        OwnerId = ownerId;
        RequesterId = requesterId;
        StatusId = statusId;
        ViewingStatus = viewingStatus;
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

    public String getRequesterId() {
        return RequesterId;
    }

    public void setRequesterId(String requesterId) {
        this.RequesterId = requesterId;
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

    public void setRequester(User user){ Requester = user; }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        this.StatusId = statusId;
    }

    public int getViewingStatus() { return ViewingStatus; }

    public void setViewingStatus(int viewingStatus){ this.ViewingStatus = viewingStatus; }


    /*****************************************************************************
     * DB Functions
     *
     */

    //Notification Table
    public static final String TABLE_NOTIFICATION = "notifications";
    public static final String NOTIFICATION_COLUMN_ID = "id";
    public static final String NOTIFICATION_COLUMN_REQUESTER_ID = "request_id";
    public static final String NOTIFICATION_COLUMN_OWNER_ID = "own_id";
    public static final String NOTIFICATION_COLUMN_STATUS_ID = "status_id";
    public static final String NOTIFICATION_COLUMN_VIEWSTATUS_ID = "viewstatus_id";

    public static void addNotification(DbHandler dbHandler, Notification notification) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTIFICATION_COLUMN_REQUESTER_ID, notification.getRequesterId());
        values.put(NOTIFICATION_COLUMN_OWNER_ID, notification.getOwnerId());
        values.put(NOTIFICATION_COLUMN_STATUS_ID, notification.getStatusId());
        values.put(NOTIFICATION_COLUMN_VIEWSTATUS_ID, notification.getViewingStatus());

        db.insert(TABLE_NOTIFICATION, null, values);
        db.close();
    }

    public static List<Notification> getAllNotificationsByOwner(DbHandler dbHandler, String owner){
        List<Notification> notifications = new ArrayList<Notification>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NOTIFICATION + "where" + NOTIFICATION_COLUMN_OWNER_ID + "=?", new String[]{owner});
        if (cursor.moveToFirst()) {
            do {
                Notification notification = new Notification(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4));
                notification.setOwner(User.getUser(dbHandler, notification.getOwnerId()));
                notification.setRequester(User.getUser(dbHandler, notification.getRequesterId()));
                notifications.add(notification);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notifications;
    }


    public static List<Notification> getAllNotificationsByRequester(DbHandler dbHandler, String requester){
        List<Notification> notifications = new ArrayList<Notification>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NOTIFICATION + "where" + NOTIFICATION_COLUMN_REQUESTER_ID + "=?", new String[]{requester});
        if (cursor.moveToFirst()) {
            do {
                Notification notification = new Notification(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4));
                notification.setOwner(User.getUser(dbHandler, notification.getOwnerId()));
                notification.setRequester(User.getUser(dbHandler, notification.getRequesterId()));
                notifications.add(notification);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notifications;
    }

    public static void updateRequest(DbHandler dbHandler, Notification notification) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOTIFICATION_COLUMN_REQUESTER_ID, notification.getRequesterId());
        values.put(NOTIFICATION_COLUMN_OWNER_ID, notification.getOwnerId());
        values.put(NOTIFICATION_COLUMN_STATUS_ID, notification.StatusId);
        values.put(NOTIFICATION_COLUMN_VIEWSTATUS_ID, notification.ViewingStatus);

        // updating row
        db.update(TABLE_NOTIFICATION, values, NOTIFICATION_COLUMN_ID + " = ?", new String[] {Integer.toString(notification.getId())});
    }


}


