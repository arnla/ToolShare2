package com.toolshare.toolshare.models;

import android.widget.AdapterView;

import java.util.Date;

public class Ad {
    private int Id;
    private String Owner;
    private int ToolId;
    private Date PostDate;
    private Date ExpirationDate;
    private String Description;
    private Availability Availability;
    private String Title;

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


    /*****************************************************************************
     * DB Functions
     *
     */

    // AD TABLE
    public static final String TABLE_ADS = "ads";
    public static final String AD_COLUMN_ID = "id";
    public static final String AD_COLUMN_OWNER = "owner";
    public static final String AD_COLUMN_TOOL_ID = "tool_id";
    public static final String AD_COLUMN_POST_DATE = "post_date";
    public static final String AD_COLUMN_EXPIRATION_DATE = "expiration_date";
    public static final String AD_COLUMN_DESCRIPTION = "description";
    public static final String AD_COLUMN_TOOL_AVAILABILITY_ID = "tool_availability_id";
    public static final String AD_COLUMN_TITLE = "title";
}
