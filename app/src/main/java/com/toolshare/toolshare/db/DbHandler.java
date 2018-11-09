package com.toolshare.toolshare.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.toolshare.toolshare.models.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper implements Serializable {
    private static final int DATABASE_VERSION = 7;
    private static final String DATABASE_NAME = "ToolshareDB";

    // USER TABLE
    public static final String TABLE_USERS = "users";
    public static final String USERS_COLUMN_EMAIL = "email";
    public static final String USERS_COLUMN_FIRST_NAME = "first_name";
    public static final String USERS_COLUMN_LAST_NAME = "last_name";
    public static final String USERS_COLUMN_PHONE = "phone";
    public static final String USERS_COLUMN_PASSWORD = "password";
    public static final String USERS_COLUMN_LOCATION = "location";

    // TOOL TABLE
    public static final String TABLE_TOOLS = "tools";
    public static final String TOOL_COLUMN_ID = "id";
    public static final String TOOL_COLUMN_OWNER = "owner";
    public static final String TOOL_COLUMN_TYPE_ID = "type_id";
    public static final String TOOL_COLUMN_NAME = "name";
    public static final String TOOL_COLUMN_YEAR = "year";
    public static final String TOOL_COLUMN_MODEL = "model";
    public static final String TOOL_COLUMN_BRAND = "brand";

    // TOOL TYPE TABLE
    public static final String TABLE_TOOL_TYPES = "tool_types";
    public static final String TOOL_TYPE_COLUMN_ID = "id";
    public static final String TOOL_TYPE_COLUMN_TYPE = "type";
    public static final String TOOL_TYPE_COLUMN_DESCRIPTION = "description";

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

    // BRAND TABLE
    public static final String TABLE_BRANDS = "brands";
    public static final String BRAND_COLUMN_ID = "id";
    public static final String BRAND_COLUMN_NAME = "name";

    // TOOL BUSY DATES TABLE
/*    public static final String TABLE_TOOL_BUSY_DATES = "tool_busy_dates";
    public static final String TOOL_BUSY_DATES_COLUMN_ID = "id";
    public static final String TOOL_BUSY_DATES_COLUMN_TOOL_ID = "tool_id";
    public static final String TOOL_BUSY_DATES_COLUMN_AD_ID = "ad_id";
    public static final String TOOL_BUSY_DATES_COLUMN_MON = "mon";
    public static final String TOOL_BUSY_DATES_COLUMN_TUE = "tue";
    public static final String TOOL_BUSY_DATES_COLUMN_WED = "wed";
    public static final String TOOL_BUSY_DATES_COLUMN_THU = "thu";
    public static final String TOOL_BUSY_DATES_COLUMN_FRI = "fri";
    public static final String TOOL_BUSY_DATES_COLUMN_SAT = "sat";
    public static final String TOOL_BUSY_DATES_COLUMN_SUN = "sun";
    public static final String TOOL_BUSY_DATES_COLUMN_START_TIME = "start_time";
    public static final String TOOL_BUSY_DATES_COLUMN_END_TIME = "end_time";*/

    // TOOL-AD AVAILABILITY TABLE

    // AD-RENTER TABLE

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // MIGRATIONS

    // 1 to 2
    private static final String MIGRATION_1_TO_2_PART_1 = "CREATE TABLE "
            + TABLE_TOOLS + " ("
            + TOOL_COLUMN_ID + " integer primary key, "
            + TOOL_COLUMN_OWNER + " text, "
            + TOOL_COLUMN_TYPE_ID + " integer, "
            + TOOL_COLUMN_NAME + " text, "
            + TOOL_COLUMN_YEAR + " integer, "
            + TOOL_COLUMN_MODEL + " text);";
    private static final String MIGRATION_1_TO_2_PART_2 = "CREATE TABLE "
            + TABLE_TOOL_TYPES + " ("
            + TOOL_TYPE_COLUMN_ID + " integer primary key, "
            + TOOL_TYPE_COLUMN_TYPE + " text, "
            + TOOL_TYPE_COLUMN_DESCRIPTION + " integer);";
    private static final String MIGRATION_1_TO_2_PART_3 = "CREATE TABLE "
            + TABLE_ADS + " ("
            + AD_COLUMN_ID + " integer primary key, "
            + AD_COLUMN_OWNER + " text, "
            + AD_COLUMN_TOOL_ID + " integer, "
            + AD_COLUMN_POST_DATE + " text, "
            + AD_COLUMN_EXPIRATION_DATE + " integer);";

    // 2 to 3
    private static final String MIGRATION_2_TO_3 = "ALTER TABLE "
            + TABLE_TOOLS
            + " ADD " + TOOL_COLUMN_BRAND + " text;";

    // 3 to 4
    private static final String MIGRATION_3_TO_4_PART_1 = "CREATE TABLE "
            + TABLE_BRANDS + " ("
            + BRAND_COLUMN_ID + " integer primary key, "
            + BRAND_COLUMN_NAME + " text);";
    private static final String MIGRATION_3_TO_4_PART_2 = "INSERT INTO "
            + TABLE_BRANDS + " ("
            + BRAND_COLUMN_ID + ", "
            + BRAND_COLUMN_NAME + ") VALUES "
            + "(1, \"Other\"),(2, \"Ryobi\"),(3, \"DeWalt\"),(4, \"Bosch\");";

    // 4 to 5
    private static final String MIGRATION_4_TO_5_PART_1 = "DROP TABLE "
            + TABLE_TOOLS + ";";
    private static final String MIGRATION_4_TO_5_PART_2 = "CREATE TABLE "
            + TABLE_TOOLS + " ("
            + TOOL_COLUMN_ID + " integer primary key, "
            + TOOL_COLUMN_OWNER + " integer, "
            + TOOL_COLUMN_TYPE_ID + " integer, "
            + TOOL_COLUMN_NAME + " text, "
            + TOOL_COLUMN_YEAR + " integer, "
            + TOOL_COLUMN_MODEL + " text, "
            + TOOL_COLUMN_BRAND + " integer);";

    // 5 to 6
    private static final String MIGRATION_5_TO_6_PART_1 = "DROP TABLE "
            + TABLE_TOOLS + ";";
    private static final String MIGRATION_5_TO_6_PART_2 = "CREATE TABLE "
            + TABLE_TOOLS + " ("
            + TOOL_COLUMN_ID + " integer primary key, "
            + TOOL_COLUMN_OWNER + " text, "
            + TOOL_COLUMN_TYPE_ID + " integer, "
            + TOOL_COLUMN_NAME + " text, "
            + TOOL_COLUMN_YEAR + " integer, "
            + TOOL_COLUMN_MODEL + " text, "
            + TOOL_COLUMN_BRAND + " integer);";

    // 6 to 7
    private static final String MIGRATION_6_TO_7 = "ALTER TABLE "
            + TABLE_ADS
            + " ADD " + AD_COLUMN_DESCRIPTION + " text;";

    // 7 to 8
/*  private static final String MIGRATION_7_TO_8_PART_1 = "ALTER TABLE "
            + TABLE_ADS
            + " ADD " + AD_COLUMN_TITLE  + " text;";
    private static final String MIGRATION_7_TO_8_PART_2 = "CREATE TABLE "
            + TABLE_TOOL_BUSY_DATES + " ("
            + TOOL_BUSY_DATES_COLUMN_ID + " integer primary key, "
            + TOOL_BUSY_DATES_COLUMN_TOOL_ID + " integer, "
            + TOOL_BUSY_DATES_COLUMN_AD_ID + " integer, "
            + TOOL_BUSY_DATES_COLUMN_MON + " integer, "
            + TOOL_BUSY_DATES_COLUMN_TUE + " integer, "
            + TOOL_BUSY_DATES_COLUMN_WED + " integer, "
            + TOOL_BUSY_DATES_COLUMN_THU + " integer, "
            + TOOL_BUSY_DATES_COLUMN_FRI + " integer, "
            + TOOL_BUSY_DATES_COLUMN_SAT + " integer, "
            + TOOL_BUSY_DATES_COLUMN_SUN + " integer, "
            + TOOL_BUSY_DATES_COLUMN_START_TIME + " real, "
            + TOOL_BUSY_DATES_COLUMN_END_TIME + " real);";*/


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE users (" +
                "email text primary key," +
                "first_name text NOT NULL," +
                "last_name text NOT NULL," +
                "phone text," +
                "password text NOT NULL)";
        db.execSQL(CREATE_USERS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL(MIGRATION_1_TO_2_PART_1);
            db.execSQL(MIGRATION_1_TO_2_PART_2);
            db.execSQL(MIGRATION_1_TO_2_PART_3);
        }

        if (oldVersion < 3) {
            db.execSQL(MIGRATION_2_TO_3);
        }

        if (oldVersion < 4) {
            db.execSQL(MIGRATION_3_TO_4_PART_1);
            db.execSQL(MIGRATION_3_TO_4_PART_2);
        }

        if (oldVersion < 5) {
            db.execSQL(MIGRATION_4_TO_5_PART_1);
            db.execSQL(MIGRATION_4_TO_5_PART_2);
        }

        if (oldVersion < 6) {
            db.execSQL(MIGRATION_5_TO_6_PART_1);
            db.execSQL(MIGRATION_5_TO_6_PART_2);
        }

        if (oldVersion < 7) {
            db.execSQL(MIGRATION_6_TO_7);
        }
    }
}