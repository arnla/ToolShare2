package com.toolshare.toolshare.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.toolshare.toolshare.db.DbHandler;

import java.util.ArrayList;
import java.util.List;

public class ToolReview {
    private int Id;
    private int ToolId;
    private Tool Tool;
    private int Rating;
    private String Review;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setToolId(int toolId) {
        ToolId = toolId;
    }

    public int getToolId() {
        return ToolId;
    }

    public com.toolshare.toolshare.models.Tool getTool() {
        return Tool;
    }

    public void setTool(com.toolshare.toolshare.models.Tool tool) {
        Tool = tool;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }

    /*****************************************************************************
     * DB Functions
     *
     */

    // TOOL REVIEW TABLE
    public static final String TABLE_TOOL_REVIEW = "tool_reviews";
    public static final String TOOL_REVIEW_COLUMN_ID = "id";
    public static final String TOOL_REVIEW_COLUMN_TOOL_ID = "tool_id";
    public static final String TOOL_REVIEW_COLUMN_RATING = "rating";
    public static final String TOOL_REVIEW_COLUMN_REVIEW = "review";

    public static void addToolReview(DbHandler dbHander, ToolReview toolReview) {
        SQLiteDatabase db = dbHander.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOOL_REVIEW_COLUMN_TOOL_ID, toolReview.getToolId());
        values.put(TOOL_REVIEW_COLUMN_RATING, toolReview.getRating());
        values.put(TOOL_REVIEW_COLUMN_REVIEW, toolReview.getReview());
        // Inserting Row
        db.insert(TABLE_TOOL_REVIEW, null, values);
        db.close();
    }

    public static List<Integer> getAllRatingsByToolId(DbHandler dbHandler, int toolId) {
        List<Integer> ratings = new ArrayList<Integer>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Cursor cursor = db.rawQuery("select " + TOOL_REVIEW_COLUMN_RATING + " from " + TABLE_TOOL_REVIEW + " where " + TOOL_REVIEW_COLUMN_TOOL_ID + " = ?", new String[] {Integer.toString(toolId)});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ratings.add(cursor.getInt(0));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return ratings;
    }
}
