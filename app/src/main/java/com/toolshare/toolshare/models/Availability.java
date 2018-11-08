package com.toolshare.toolshare.models;

import java.sql.Time;

public class Availability {
    private boolean Monday;
    private boolean Tuesday;
    private boolean Wednesday;
    private boolean Thursday;
    private boolean Friday;
    private boolean Saturday;
    private boolean Sunday;
    private Time Start;
    private Time End;

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

    public Time getStart() {
        return Start;
    }

    public void setStart(Time time) {
        Start = time;
    }

    public Time getEnd() {
        return End;
    }

    public void setEnd(Time time) {
        End = time;
    }
}
