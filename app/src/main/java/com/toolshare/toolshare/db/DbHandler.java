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
    private static final int DATABASE_VERSION = 3;
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
    public static final String TOOL_COLUMN_BRAND_ID = "brand_id";

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
    public static final String AD_COLUMN_AVAILABILITY_ID = "tool_availability_id";
    public static final String AD_COLUMN_POST_DATE = "post_date";
    public static final String AD_COLUMN_EXPIRATION_DATE = "expiration_date";
    public static final String AD_COLUMN_DESCRIPTION = "description";
    public static final String AD_COLUMN_TITLE = "title";

    // BRAND TABLE
    public static final String TABLE_BRANDS = "brands";
    public static final String BRAND_COLUMN_ID = "id";
    public static final String BRAND_COLUMN_NAME = "name";

    // AVAILABILITY
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

    // REQUEST STATUS TABLE
    public static final String TABLE_REQUEST_STATUSES = "request_statuses";
    public static final String REQUEST_STATUS_COLUMN_ID = "id";
    public static final String REQUEST_STATUS_COLUMN_NAME = "status_name";

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // MIGRATIONS
    public static final String MIGRATION_1_TO_2_PART_1 = "alter table "
            + TABLE_ADS + " rename to ads_old;";
    public static final String MIGRATION_1_TO_2_PART_2 = "CREATE TABLE "
            + TABLE_ADS + " ("
            + AD_COLUMN_ID + " integer primary key autoincrement, "
            + AD_COLUMN_OWNER + " text not null, "
            + AD_COLUMN_TOOL_ID + " integer not null, "
            + AD_COLUMN_POST_DATE + " text, "
            + AD_COLUMN_EXPIRATION_DATE + " text, "
            + AD_COLUMN_TITLE + " text not null, "
            + AD_COLUMN_DESCRIPTION + " text, "
            + "CONSTRAINT fk_users FOREIGN KEY ("
            + AD_COLUMN_OWNER + ") REFERENCES "
            + TABLE_USERS + "(" + USERS_COLUMN_EMAIL + "),"
            + "CONSTRAINT fk_tools FOREIGN KEY ("
            + AD_COLUMN_TOOL_ID + ") REFERENCES "
            + TABLE_TOOLS + "(" + TOOL_COLUMN_ID + "));";
    public static final String MIGRATION_1_TO_2_PART_3 = "insert into "
            + TABLE_ADS + "("
            + AD_COLUMN_ID + "," + AD_COLUMN_OWNER + "," + AD_COLUMN_TOOL_ID + ","
            + AD_COLUMN_POST_DATE + "," + AD_COLUMN_EXPIRATION_DATE + "," + AD_COLUMN_TITLE + "," + AD_COLUMN_DESCRIPTION + ") "
            + "select " + AD_COLUMN_ID + "," + AD_COLUMN_OWNER + "," + AD_COLUMN_TOOL_ID + ","
            + AD_COLUMN_POST_DATE + "," + AD_COLUMN_EXPIRATION_DATE + "," + AD_COLUMN_TITLE + "," + AD_COLUMN_DESCRIPTION
            + " from ads_old;";

    public static final String MIGRATION_2_TO_3_PART_1 = "create table "
            + TABLE_REQUEST_STATUSES + " ("
            + REQUEST_STATUS_COLUMN_ID + " integer primary key autoincrement, "
            + REQUEST_STATUS_COLUMN_NAME + " text not null);";
    public static final String MIGRATION_2_TO_3_PART_2 = "insert into "
            + TABLE_REQUEST_STATUSES + " ("
            + REQUEST_STATUS_COLUMN_NAME + ") values (\"Pending\"), (\"Accepted\"), (\"Rejected\"), (\"Cancelled\");";

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE "
                + TABLE_USERS + " ("
                + USERS_COLUMN_EMAIL + " text primary key, "
                + USERS_COLUMN_FIRST_NAME + " text not null, "
                + USERS_COLUMN_LAST_NAME + " text not null,"
                + USERS_COLUMN_PHONE + " text, "
                + USERS_COLUMN_PASSWORD + " text not null, "
                + USERS_COLUMN_LOCATION + " text);";
        String CREATE_TOOLS_TABLE = "CREATE TABLE "
                + TABLE_TOOLS + " ("
                + TOOL_COLUMN_ID + " integer primary key autoincrement, "
                + TOOL_COLUMN_OWNER + " text not null, "
                + TOOL_COLUMN_TYPE_ID + " integer not null, "
                + TOOL_COLUMN_BRAND_ID + " integer not null,"
                + TOOL_COLUMN_NAME + " text not null, "
                + TOOL_COLUMN_YEAR + " integer, "
                + TOOL_COLUMN_MODEL + " text, "
                + "CONSTRAINT fk_users FOREIGN KEY ("
                + TOOL_COLUMN_OWNER + ") REFERENCES "
                + TABLE_USERS + "(" + USERS_COLUMN_EMAIL + "),"
                + "CONSTRAINT fk_tool_types FOREIGN KEY ("
                + TOOL_COLUMN_TYPE_ID + ") REFERENCES "
                + TABLE_TOOL_TYPES + "(" + TOOL_TYPE_COLUMN_ID + "),"
                + "CONSTRAINT fk_brands FOREIGN KEY ("
                + TOOL_COLUMN_BRAND_ID + ") REFERENCES "
                + TABLE_BRANDS + "(" + BRAND_COLUMN_ID + "));";
        String CREATE_TOOL_TYPES_TABLE = "CREATE TABLE "
                + TABLE_TOOL_TYPES + " ("
                + TOOL_TYPE_COLUMN_ID + " integer primary key autoincrement, "
                + TOOL_TYPE_COLUMN_TYPE + " text not null, "
                + TOOL_TYPE_COLUMN_DESCRIPTION + " integer);";
        String CREATE_ADS_TABLE = "CREATE TABLE "
                + TABLE_ADS + " ("
                + AD_COLUMN_ID + " integer primary key autoincrement, "
                + AD_COLUMN_OWNER + " text not null, "
                + AD_COLUMN_TOOL_ID + " integer not null, "
                + AD_COLUMN_AVAILABILITY_ID + " integer not null,"
                + AD_COLUMN_POST_DATE + " text, "
                + AD_COLUMN_EXPIRATION_DATE + " text, "
                + AD_COLUMN_TITLE + " text not null, "
                + AD_COLUMN_DESCRIPTION + " text, "
                + "CONSTRAINT fk_users FOREIGN KEY ("
                + AD_COLUMN_OWNER + ") REFERENCES "
                + TABLE_USERS + "(" + USERS_COLUMN_EMAIL + "),"
                + "CONSTRAINT fk_tools FOREIGN KEY ("
                + AD_COLUMN_TOOL_ID + ") REFERENCES "
                + TABLE_TOOLS + "(" + TOOL_COLUMN_ID + "),"
                + "CONSTRAINT fk_availability FOREIGN KEY ("
                + AD_COLUMN_AVAILABILITY_ID + ") REFERENCES "
                + TABLE_AVAILABILITY + "(" + AVAILABILITY_COLUMN_ID + "));";
        String CREATE_BRANDS_TABLE = "CREATE TABLE "
                + TABLE_BRANDS + " ("
                + BRAND_COLUMN_ID + " integer primary key autoincrement, "
                + BRAND_COLUMN_NAME + " text);";
        String CREATE_AVAILABILITY_TABLE = "CREATE TABLE "
                + TABLE_AVAILABILITY + " ("
                + AVAILABILITY_COLUMN_ID + " integer primary key autoincrement, "
                + AVAILABILITY_COLUMN_AD_ID + " integer not null, "
                + AVAILABILITY_COLUMN_SUN + " integer not null, "
                + AVAILABILITY_COLUMN_MON + " integer not null, "
                + AVAILABILITY_COLUMN_TUE + " integer not null, "
                + AVAILABILITY_COLUMN_WED + " integer not null, "
                + AVAILABILITY_COLUMN_THU + " integer not null, "
                + AVAILABILITY_COLUMN_FRI + " integer not null, "
                + AVAILABILITY_COLUMN_SAT + " integer not null, "
                + AVAILABILITY_COLUMN_START_DATE + " text not null, "
                + AVAILABILITY_COLUMN_END_DATE + " text not null, "
                + AVAILABILITY_COLUMN_START_TIME + " text not null, "
                + AVAILABILITY_COLUMN_END_TIME + " text not null, "
                + "CONSTRAINT fk_ads FOREIGN KEY ("
                + AVAILABILITY_COLUMN_AD_ID + ") REFERENCES "
                + TABLE_ADS + "(" + AD_COLUMN_ID + "));";

        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_TOOLS_TABLE);
        db.execSQL(CREATE_TOOL_TYPES_TABLE);
        db.execSQL(CREATE_ADS_TABLE);
        db.execSQL(CREATE_BRANDS_TABLE);
        db.execSQL(CREATE_AVAILABILITY_TABLE);
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
            db.execSQL(MIGRATION_2_TO_3_PART_1);
            db.execSQL(MIGRATION_2_TO_3_PART_2);
        }
    }
}