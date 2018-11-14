package com.toolshare.toolshare.models;

import java.sql.Date;
import java.sql.Time;

public class Availability {
    private int Id;
    private int AdId;
    private boolean Monday;
    private boolean Tuesday;
    private boolean Wednesday;
    private boolean Thursday;
    private boolean Friday;
    private boolean Saturday;
    private boolean Sunday;
    private Date StartDate;
    private Date EndDate;
    private Time StartTime;
    private Time EndTime;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public int getAdId() {
        return AdId;
    }

    public void setAdId(int adId) {
        this.AdId = adId;
    }

    public boolean isAvailableMonday() {
        return Monday;
    }

    public void setAvailableMonday(boolean available) {
        Monday = available;
    }

    public boolean isAvailableTuesday() {
        return Tuesday;
    }

    public void setAvailableTuesday(boolean available) {
        Tuesday = available;
    }

    public boolean isAvailableWednesday() {
        return Wednesday;
    }

    public void setAvailableWednesday(boolean available) {
        Wednesday = available;
    }

    public boolean isAvailableThursday() {
        return Thursday;
    }

    public void setAvailableThursday(boolean available) {
        Thursday = available;
    }

    public boolean isAvailableFriday() {
        return Friday;
    }

    public void setAvailableFriday(boolean available) {
        Friday = available;
    }

    public boolean isAvailableSaturday() {
        return Saturday;
    }

    public void setAvailableSaturday(boolean available) {
        Saturday = available;
    }

    public boolean isAvailableSunday() {
        return Sunday;
    }

    public void setAvailableSunday(boolean available) {
        Sunday = available;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public void setStartDate(Date startDate) {
        this.StartDate = startDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public void setEndDate(Date endDate) {
        this.EndDate = endDate;
    }

    public Time getStartTime() {
        return StartTime;
    }

    public void setStartTime(Time time) {
        StartTime = time;
    }

    public Time getEndTime() {
        return EndTime;
    }

    public void setEndTime(Time time) {
        EndTime = time;
    }


    /*****************************************************************************
     * DB Functions
     *
     */

    // AD TABLE
    public static final String TABLE_AVAILABILITY = "availability";
    public static final String AVAILABILITY_COLUMN_ID = "id";
    public static final String AVAILABILITY_COLUMN_AD_ID = "ad_id";
    public static final String AVAILABILITY_COLUMN_SUN = "sunday";
    public static final String AVAILABILITY_COLUMN_MON = "monday";
    public static final String AVAILABILITY_COLUMN_TUE = "tuesday";
    public static final String AVAILABILITY_COLUMN_WED = "wednesday";
    public static final String AVAILABILITY_COLUMN_THU = "thursday";
    public static final String AVAILABILITY_COLUMN_FRI = "friday";
    public static final String AVAILABILITY_COLUMN_SAT = "saturday";
    public static final String AVAILABILITY_COLUMN_START_DATE = "start_date";
    public static final String AVAILABILITY_COLUMN_END_DATE = "end_date";
    public static final String AVAILABILITY_COLUMN_START_TIME = "start_time";
    public static final String AVAILABILITY_COLUMN_END_TIME = "end_time";
}
