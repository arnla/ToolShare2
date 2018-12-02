package com.toolshare.toolshare.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.toolshare.toolshare.models.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper implements Serializable {
    private static final int DATABASE_VERSION = 14;
    private static final String DATABASE_NAME = "ToolshareDB";

    // USER TABLE
    public static final String TABLE_USERS = "users";
    public static final String USERS_COLUMN_EMAIL = "email";
    public static final String USERS_COLUMN_FIRST_NAME = "first_name";
    public static final String USERS_COLUMN_LAST_NAME = "last_name";
    public static final String USERS_COLUMN_PHONE = "phone";
    public static final String USERS_COLUMN_PASSWORD = "password";
    public static final String USERS_COLUMN_STREET_ADDRESS = "street_address";
    public static final String USERS_COLUMN_CITY = "city";
    public static final String USERS_COLUMN_PROVINCE = "province";
    public static final String USERS_COLUMN_ZIP_CODE = "zip_code";
    public static final String USERS_COLUMN_COUNTRY = "country";

    // TOOL TABLE
    public static final String TABLE_TOOLS = "tools";
    public static final String TOOL_COLUMN_ID = "id";
    public static final String TOOL_COLUMN_OWNER = "owner";
    public static final String TOOL_COLUMN_TYPE_ID = "type_id";
    public static final String TOOL_COLUMN_NAME = "name";
    public static final String TOOL_COLUMN_YEAR = "year";
    public static final String TOOL_COLUMN_MODEL = "model";
    public static final String TOOL_COLUMN_BRAND_ID = "brand_id";
    public static final String TOOL_COLUMN_PICTURE = "picture";
    public static final String TOOL_COLUMN_RATING = "rating";

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
    public static final String AD_COLUMN_PRICE = "price";

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

    // CARD TABLE
    public static String TABLE_CARDS = "cards";
    public static final String CARD_COLUMN_ID = "id";
    public static final String CARD_COLUMN_OWNER_ID = "owner_id";
    public static final String CARD_COLUMN_FULL_NAME = "full_name";
    public static final String CARD_COLUMN_CARD_NUMBER = "card_number";
    public static final String CARD_COLUMN_EXPIRY_MONTH = "expiry_month";
    public static final String CARD_COLUMN_EXPIRY_YEAR = "expiry_year";
    public static final String CARD_COLUMN_CVC = "cvc";

    // TRANSACTION TABLE
    public static String TABLE_TRANSACTIONS = "transactions";
    public static final String TRANSACTION_COLUMN_ID = "id";
    public static final String TRANSACTION_COLUMN_REQUEST_ID = "request_id";
    public static final String TRANSACTION_COLUMN_CARD_ID = "card_id";
    public static final String TRANSACTION_COLUMN_BILLING_ADDRESS = "billing_address";
    public static final String TRANSACTION_COLUMN_STATUS_ID = "status_id";

    // TRANSACTION STATUS TABLE
    public static final String TABLE_TRANSACTION_STATUSES = "transaction_statuses";
    public static final String TRANSACTION_STATUS_COLUMN_ID = "id";
    public static final String TRANSACTION_STATUS_COLUMN_NAME = "status_name";

    // TOOL SCHEDULE TABLE
    public static final String TABLE_TOOL_SCHEDULE = "tool_schedule";
    public static final String TOOL_SCHEDULE_COLUMN_ID = "id";
    public static final String TOOL_SCHEDULE_COLUMN_TOOL_ID = "tool_id";
    public static final String TOOL_SCHEDULE_COLUMN_REQUEST_ID = "request_id";
    public static final String TOOL_SCHEDULE_COLUMN_DATE = "date";
    public static final String TOOL_SCHEDULE_COLUMN_STATUS = "status";

    // TOOL REVIEW TABLE
    public static final String TABLE_TOOL_REVIEW = "tool_reviews";
    public static final String TOOL_REVIEW_COLUMN_ID = "id";
    public static final String TOOL_REVIEW_COLUMN_TOOL_ID = "tool_id";
    public static final String TOOL_REVIEW_COLUMN_RATING = "rating";
    public static final String TOOL_REVIEW_COLUMN_REVIEW = "review";

    // TOOL ADDRESS TABLE
    public static final String TABLE_TOOL_ADDRESS = "tool_addresses";
    public static final String TOOL_ADDRESS_COLUMN_ID = "id";
    public static final String TOOL_ADDRESS_COLUMN_TOOL_ID = "tool_id";
    public static final String TOOL_ADDRESS_COLUMN_STREET_ADDRESS = "street_address";
    public static final String TOOL_ADDRESS_COLUMN_CITY = "city";
    public static final String TOOL_ADDRESS_COLUMN_PROVINCE = "province";
    public static final String TOOL_ADDRESS_COLUMN_ZIP_CODE = "zip_code";
    public static final String TOOL_ADDRESS_COLUMN_COUNTRY = "country";

    // NOTIFICATIONS ADDRESS TABLE
    public static final String TABLE_NOTIFICATION = "notifications";
    public static final String NOTIFICATION_COLUMN_ID = "id";
    public static final String NOTIFICATION_COLUMN_OWNER_ID = "owner_id";
    public static final String NOTIFICATION_COLUMN_REQUEST_ID = "request_id";
    public static final String NOTIFICATION_COLUMN_STATUS_ID = "status_id";
    public static final String NOTIFICATION_COLUMN_VIEWSTATUS_ID = "read";
    public static final String NOTIFICATION_COLUMN_DATE_CREATED = "date_created";

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

    public static final String MIGRATION_3_TO_4 = "create table "
            + TABLE_REQUESTS + " ("
            + REQUEST_COLUMN_ID + " integer primary key autoincrement, "
            + REQUEST_COLUMN_REQUESTER_ID + " text not null, "
            + REQUEST_COLUMN_OWNER_ID + " text not null, "
            + REQUEST_COLUMN_AD_ID + " integer not null, "
            + REQUEST_COLUMN_REQUESTED_START_DATE + " text not null, "
            + REQUEST_COLUMN_REQUESTED_END_DATE + " text not null, "
            + REQUEST_COLUMN_DELIVERY_METHOD + " text not null, "
            + REQUEST_COLUMN_STATUS_ID + " integer not null, "
            + "CONSTRAINT fk_users FOREIGN KEY ("
            + REQUEST_COLUMN_REQUESTER_ID + "," + REQUEST_COLUMN_OWNER_ID + ") REFERENCES "
            + TABLE_USERS + "(" + USERS_COLUMN_EMAIL + "," + USERS_COLUMN_EMAIL + "), "
            + "CONSTRAINT fk_ads FOREIGN KEY ("
            + REQUEST_COLUMN_AD_ID + ") REFERENCES "
            + TABLE_ADS + "(" + AD_COLUMN_ID + "), "
            + "CONSTRAINT fk_status FOREIGN KEY ("
            + REQUEST_COLUMN_STATUS_ID + ") REFERENCES "
            + TABLE_REQUEST_STATUSES + "(" + REQUEST_STATUS_COLUMN_ID + "));";

    public static final String MIGRATION_4_TO_5_PART_1 = "create table "
            + TABLE_CARDS + " ("
            + CARD_COLUMN_ID + " integer primary key autoincrement, "
            + CARD_COLUMN_OWNER_ID + " text not null, "
            + CARD_COLUMN_FULL_NAME + " text not null, "
            + CARD_COLUMN_CARD_NUMBER + " text not null, "
            + CARD_COLUMN_EXPIRY_MONTH + " integer not null, "
            + CARD_COLUMN_EXPIRY_YEAR + " integer not null, "
            + CARD_COLUMN_CVC + " integer not null, "
            + "CONSTRAINT fk_users FOREIGN KEY ("
            + CARD_COLUMN_OWNER_ID + ") REFERENCES "
            + TABLE_USERS + "(" + USERS_COLUMN_EMAIL + "));";
    public static final String MIGRATION_4_TO_5_PART_2 = "create table "
            + TABLE_TRANSACTION_STATUSES + " ("
            + TRANSACTION_STATUS_COLUMN_ID + " integer primary key autoincrement, "
            + TRANSACTION_STATUS_COLUMN_NAME + " text not null);";
    public static final String MIGRATION_4_TO_5_PART_3 = "insert into "
            + TABLE_TRANSACTION_STATUSES + " ("
            + TRANSACTION_STATUS_COLUMN_NAME + ") values (\"Pending\"), (\"Completed\"), (\"Cancelled\");";
    public static final String MIGRATION_4_TO_5_PART_4 = "create table "
            + TABLE_TRANSACTIONS + " ("
            + TRANSACTION_COLUMN_ID + " integer primary key autoincrement, "
            + TRANSACTION_COLUMN_REQUEST_ID + " integer not null, "
            + TRANSACTION_COLUMN_CARD_ID + " integer not null, "
            + TRANSACTION_COLUMN_BILLING_ADDRESS + " text not null, "
            + TRANSACTION_COLUMN_STATUS_ID + " integer not null, "
            + "CONSTRAINT fk_requests FOREIGN KEY ("
            + TRANSACTION_COLUMN_REQUEST_ID + ") REFERENCES "
            + TABLE_REQUESTS + "(" + REQUEST_COLUMN_ID + "), "
            + "CONSTRAINT fk_cards FOREIGN KEY ("
            + TRANSACTION_COLUMN_CARD_ID + ") REFERENCES "
            + TABLE_CARDS + "(" + CARD_COLUMN_ID + "), "
            + "CONSTRAINT fk_transaction_status FOREIGN KEY ("
            + TRANSACTION_COLUMN_STATUS_ID + ") REFERENCES "
            + TABLE_TRANSACTION_STATUSES + "(" + TRANSACTION_STATUS_COLUMN_ID + "));";

    public static final String MIGRATION_5_TO_6_PART_1 = "drop table ads_old";
    public static final String MIGRATION_5_TO_6_PART_2 = "alter table "
            + TABLE_ADS + " rename to ads_old;";
    public static final String MIGRATION_5_TO_6_PART_3 = "create table "
            + TABLE_ADS + " ("
            + AD_COLUMN_ID + " integer primary key autoincrement, "
            + AD_COLUMN_OWNER + " text not null, "
            + AD_COLUMN_TOOL_ID + " integer not null, "
            + AD_COLUMN_POST_DATE + " text, "
            + AD_COLUMN_EXPIRATION_DATE + " text, "
            + AD_COLUMN_TITLE + " text not null, "
            + AD_COLUMN_DESCRIPTION + " text, "
            + AD_COLUMN_PRICE + " integer not null default 0, "
            + "CONSTRAINT fk_users FOREIGN KEY ("
            + AD_COLUMN_OWNER + ") REFERENCES "
            + TABLE_USERS + "(" + USERS_COLUMN_EMAIL + "),"
            + "CONSTRAINT fk_tools FOREIGN KEY ("
            + AD_COLUMN_TOOL_ID + ") REFERENCES "
            + TABLE_TOOLS + "(" + TOOL_COLUMN_ID + "));";
    public static final String MIGRATION_5_TO_6_PART_4 = "insert into "
            + TABLE_ADS + "("
            + AD_COLUMN_ID + "," + AD_COLUMN_OWNER + "," + AD_COLUMN_TOOL_ID + ","
            + AD_COLUMN_POST_DATE + "," + AD_COLUMN_EXPIRATION_DATE + "," + AD_COLUMN_TITLE + "," + AD_COLUMN_DESCRIPTION + ") "
            + "select " + AD_COLUMN_ID + "," + AD_COLUMN_OWNER + "," + AD_COLUMN_TOOL_ID + ","
            + AD_COLUMN_POST_DATE + "," + AD_COLUMN_EXPIRATION_DATE + "," + AD_COLUMN_TITLE + "," + AD_COLUMN_DESCRIPTION
            + " from ads_old;";

    public static final String MIGRATION_6_TO_7 = "drop table ads_old;";

    public static final String MIGRATION_7_TO_8_PART_1 = "alter table "
            + TABLE_AVAILABILITY + " rename to availability_old;";
    public static final String MIGRATION_7_TO_8_PART_2 = "create table "
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
            + "CONSTRAINT fk_ads FOREIGN KEY ("
            + AVAILABILITY_COLUMN_AD_ID + ") REFERENCES "
            + TABLE_ADS + "(" + AD_COLUMN_ID + "));";
    public static final String MIGRATION_7_TO_8_PART_3 = "insert into "
            + TABLE_AVAILABILITY + "("
            + AVAILABILITY_COLUMN_ID + "," + AVAILABILITY_COLUMN_AD_ID + "," + AVAILABILITY_COLUMN_SUN + "," + AVAILABILITY_COLUMN_MON
            + "," + AVAILABILITY_COLUMN_TUE + "," + AVAILABILITY_COLUMN_WED + "," + AVAILABILITY_COLUMN_THU + "," + AVAILABILITY_COLUMN_FRI
            + "," + AVAILABILITY_COLUMN_SAT + "," + AVAILABILITY_COLUMN_START_DATE + "," + AVAILABILITY_COLUMN_END_DATE + ") "
            + "select " + AVAILABILITY_COLUMN_ID + "," + AVAILABILITY_COLUMN_AD_ID + "," + AVAILABILITY_COLUMN_SUN + "," + AVAILABILITY_COLUMN_MON
            + "," + AVAILABILITY_COLUMN_TUE + "," + AVAILABILITY_COLUMN_WED + "," + AVAILABILITY_COLUMN_THU + "," + AVAILABILITY_COLUMN_FRI
            + "," + AVAILABILITY_COLUMN_SAT + "," + AVAILABILITY_COLUMN_START_DATE + "," + AVAILABILITY_COLUMN_END_DATE
            + " from availability_old;";

    public static final String MIGRATION_8_TO_9 = "drop table availability_old;";

    public static final String MIGRATION_9_TO_10_PART_1 = "alter table "
            + TABLE_TOOLS + " rename to tools_old;";
    public static final String MIGRATION_9_TO_10_PART_2 = "CREATE TABLE "
            + TABLE_TOOLS + " ("
            + TOOL_COLUMN_ID + " integer primary key autoincrement, "
            + TOOL_COLUMN_OWNER + " text not null, "
            + TOOL_COLUMN_TYPE_ID + " integer not null, "
            + TOOL_COLUMN_BRAND_ID + " integer not null,"
            + TOOL_COLUMN_NAME + " text not null, "
            + TOOL_COLUMN_YEAR + " integer, "
            + TOOL_COLUMN_MODEL + " text, "
            + TOOL_COLUMN_PICTURE + " blob, "
            + "CONSTRAINT fk_users FOREIGN KEY ("
            + TOOL_COLUMN_OWNER + ") REFERENCES "
            + TABLE_USERS + "(" + USERS_COLUMN_EMAIL + "),"
            + "CONSTRAINT fk_tool_types FOREIGN KEY ("
            + TOOL_COLUMN_TYPE_ID + ") REFERENCES "
            + TABLE_TOOL_TYPES + "(" + TOOL_TYPE_COLUMN_ID + "),"
            + "CONSTRAINT fk_brands FOREIGN KEY ("
            + TOOL_COLUMN_BRAND_ID + ") REFERENCES "
            + TABLE_BRANDS + "(" + BRAND_COLUMN_ID + "));";
    public static final String MIGRATION_9_TO_10_PART_3 = "insert into "
            + TABLE_TOOLS + "("
            + TOOL_COLUMN_ID + "," + TOOL_COLUMN_OWNER + "," + TOOL_COLUMN_TYPE_ID + ","
            + TOOL_COLUMN_BRAND_ID + "," + TOOL_COLUMN_NAME + "," + TOOL_COLUMN_YEAR + "," + TOOL_COLUMN_MODEL + ") "
            + "select " + TOOL_COLUMN_ID + "," + TOOL_COLUMN_OWNER + "," + TOOL_COLUMN_TYPE_ID + ","
            + TOOL_COLUMN_BRAND_ID + "," + TOOL_COLUMN_NAME + "," + TOOL_COLUMN_YEAR + "," + TOOL_COLUMN_MODEL
            + " from tools_old;";
    public static final String MIGRATION_9_TO_10_PART_4 = "drop table tools_old;";

    public static final String MIGRATION_10_TO_11_PART_1 = "alter table "
            + TABLE_REQUESTS + " rename to requests_old;";
    public static final String MIGRATION_10_TO_11_PART_2 = "create table "
            + TABLE_REQUESTS + " ("
            + REQUEST_COLUMN_ID + " integer primary key autoincrement, "
            + REQUEST_COLUMN_REQUESTER_ID + " text not null, "
            + REQUEST_COLUMN_OWNER_ID + " text not null, "
            + REQUEST_COLUMN_AD_ID + " integer not null, "
            + REQUEST_COLUMN_DELIVERY_METHOD + " text not null, "
            + REQUEST_COLUMN_STATUS_ID + " integer not null, "
            + "CONSTRAINT fk_users FOREIGN KEY ("
            + REQUEST_COLUMN_REQUESTER_ID + "," + REQUEST_COLUMN_OWNER_ID + ") REFERENCES "
            + TABLE_USERS + "(" + USERS_COLUMN_EMAIL + "," + USERS_COLUMN_EMAIL + "), "
            + "CONSTRAINT fk_ads FOREIGN KEY ("
            + REQUEST_COLUMN_AD_ID + ") REFERENCES "
            + TABLE_ADS + "(" + AD_COLUMN_ID + "), "
            + "CONSTRAINT fk_status FOREIGN KEY ("
            + REQUEST_COLUMN_STATUS_ID + ") REFERENCES "
            + TABLE_REQUEST_STATUSES + "(" + REQUEST_STATUS_COLUMN_ID + "));";
    public static final String MIGRATION_10_TO_11_PART_3 = "insert into "
            + TABLE_REQUESTS + "("
            + REQUEST_COLUMN_ID + "," + REQUEST_COLUMN_REQUESTER_ID + "," + REQUEST_COLUMN_OWNER_ID + ","
            + REQUEST_COLUMN_AD_ID + "," + REQUEST_COLUMN_DELIVERY_METHOD + "," + REQUEST_COLUMN_STATUS_ID + ") "
            + "select " + REQUEST_COLUMN_ID + "," + REQUEST_COLUMN_REQUESTER_ID + "," + REQUEST_COLUMN_OWNER_ID + ","
            + REQUEST_COLUMN_AD_ID + "," + REQUEST_COLUMN_DELIVERY_METHOD + "," + REQUEST_COLUMN_STATUS_ID
            + " from requests_old;";
    public static final String MIGRATION_10_TO_11_PART_4 = "drop table requests_old;";
    public static final String MIGRATION_10_TO_11_PART_5 = "create table "
            + TABLE_TOOL_SCHEDULE + " ("
            + TOOL_SCHEDULE_COLUMN_ID + " integer primary key autoincrement, "
            + TOOL_SCHEDULE_COLUMN_TOOL_ID + " integer not null, "
            + TOOL_SCHEDULE_COLUMN_REQUEST_ID + " int not null, "
            + TOOL_SCHEDULE_COLUMN_DATE + " text not null, "
            + TOOL_SCHEDULE_COLUMN_STATUS + " text not null, "
            + "CONSTRAINT fk_tools FOREIGN KEY ("
            + TOOL_SCHEDULE_COLUMN_TOOL_ID + ") REFERENCES "
            + TABLE_TOOLS + "(" + TOOL_COLUMN_ID + "), "
            + "CONSTRAINT fk_requests FOREIGN KEY ("
            + TOOL_SCHEDULE_COLUMN_REQUEST_ID + ") REFERENCES "
            + TABLE_REQUESTS + "(" + REQUEST_COLUMN_ID + "));";

    public static final String MIGRATION_11_TO_12_PART_1 = "alter table "
            + TABLE_TOOLS + " rename to tools_old;";
    public static final String MIGRATION_11_TO_12_PART_2 = "CREATE TABLE "
            + TABLE_TOOLS + " ("
            + TOOL_COLUMN_ID + " integer primary key autoincrement, "
            + TOOL_COLUMN_OWNER + " text not null, "
            + TOOL_COLUMN_TYPE_ID + " integer not null, "
            + TOOL_COLUMN_BRAND_ID + " integer not null,"
            + TOOL_COLUMN_NAME + " text not null, "
            + TOOL_COLUMN_YEAR + " integer, "
            + TOOL_COLUMN_MODEL + " text, "
            + TOOL_COLUMN_PICTURE + " blob, "
            + TOOL_COLUMN_RATING + " real, "
            + "CONSTRAINT fk_users FOREIGN KEY ("
            + TOOL_COLUMN_OWNER + ") REFERENCES "
            + TABLE_USERS + "(" + USERS_COLUMN_EMAIL + "),"
            + "CONSTRAINT fk_tool_types FOREIGN KEY ("
            + TOOL_COLUMN_TYPE_ID + ") REFERENCES "
            + TABLE_TOOL_TYPES + "(" + TOOL_TYPE_COLUMN_ID + "),"
            + "CONSTRAINT fk_brands FOREIGN KEY ("
            + TOOL_COLUMN_BRAND_ID + ") REFERENCES "
            + TABLE_BRANDS + "(" + BRAND_COLUMN_ID + "));";
    public static final String MIGRATION_11_TO_12_PART_3 = "insert into "
            + TABLE_TOOLS + "("
            + TOOL_COLUMN_ID + "," + TOOL_COLUMN_OWNER + "," + TOOL_COLUMN_TYPE_ID + "," + TOOL_COLUMN_BRAND_ID
            + "," + TOOL_COLUMN_NAME + "," + TOOL_COLUMN_YEAR + "," + TOOL_COLUMN_MODEL + "," + TOOL_COLUMN_PICTURE + ") "
            + "select " + TOOL_COLUMN_ID + "," + TOOL_COLUMN_OWNER + "," + TOOL_COLUMN_TYPE_ID + "," + TOOL_COLUMN_BRAND_ID
            + "," + TOOL_COLUMN_NAME + "," + TOOL_COLUMN_YEAR + "," + TOOL_COLUMN_MODEL + "," + TOOL_COLUMN_PICTURE
            + " from tools_old;";
    public static final String MIGRATION_11_TO_12_PART_4 = "drop table tools_old;";
    public static final String MIGRATION_11_TO_12_PART_5 = "CREATE TABLE "
            + TABLE_TOOL_REVIEW + " ("
            + TOOL_REVIEW_COLUMN_ID + " integer primary key autoincrement, "
            + TOOL_REVIEW_COLUMN_TOOL_ID + " int not null, "
            + TOOL_REVIEW_COLUMN_RATING + " integer not null, "
            + TOOL_REVIEW_COLUMN_REVIEW + " text,"
            + "CONSTRAINT fk_tools FOREIGN KEY ("
            + TOOL_REVIEW_COLUMN_TOOL_ID + ") REFERENCES "
            + TABLE_TOOLS + "(" + TOOL_COLUMN_ID + "));";

    public static final String MIGRATION_12_TO_13 = "CREATE TABLE "
            + TABLE_TOOL_ADDRESS + " ("
            + TOOL_ADDRESS_COLUMN_ID + " integer primary key autoincrement, "
            + TOOL_ADDRESS_COLUMN_TOOL_ID + " integer not null, "
            + TOOL_ADDRESS_COLUMN_STREET_ADDRESS + " text not null, "
            + TOOL_ADDRESS_COLUMN_CITY + " text not null, "
            + TOOL_ADDRESS_COLUMN_PROVINCE + " text not null, "
            + TOOL_ADDRESS_COLUMN_ZIP_CODE + " text not null, "
            + TOOL_ADDRESS_COLUMN_COUNTRY + " text not null, "
            + "CONSTRAINT fk_tools FOREIGN KEY ("
            + TOOL_ADDRESS_COLUMN_TOOL_ID + ") REFERENCES "
            + TABLE_TOOLS + "(" + TOOL_COLUMN_ID + "));";

    public static final String MIGRATION_13_TO_14 = "CREATE TABLE "
            + TABLE_NOTIFICATION + "("
            + NOTIFICATION_COLUMN_ID + " integer primary key autoincrement, "
            + NOTIFICATION_COLUMN_OWNER_ID + " text not null, "
            + NOTIFICATION_COLUMN_REQUEST_ID + " integer not null, "
            + NOTIFICATION_COLUMN_STATUS_ID + " integer not null, "
            + NOTIFICATION_COLUMN_VIEWSTATUS_ID + " integer not null, "
            + NOTIFICATION_COLUMN_DATE_CREATED + " text not null, "
            + "CONSTRAINT fk_users FOREIGN KEY ("
            + NOTIFICATION_COLUMN_OWNER_ID + ") REFERENCES "
            + TABLE_USERS + "(" + USERS_COLUMN_EMAIL + "), "
            + "CONSTRAINT fk_requests FOREIGN KEY ("
            + NOTIFICATION_COLUMN_REQUEST_ID + ") REFERENCES "
            + TABLE_REQUESTS + "(" + REQUEST_COLUMN_ID + "), "
            + "CONSTRAINT fk_status FOREIGN KEY ("
            + REQUEST_COLUMN_STATUS_ID + ") REFERENCES "
            + TABLE_REQUEST_STATUSES + "(" + REQUEST_STATUS_COLUMN_ID + "));";


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
                + USERS_COLUMN_STREET_ADDRESS + " text not null, "
                + USERS_COLUMN_CITY + " text not null, "
                + USERS_COLUMN_PROVINCE + " text not null, "
                + USERS_COLUMN_ZIP_CODE + " text not null, "
                + USERS_COLUMN_COUNTRY + " text not null);";
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

        // execute migrations
        db.execSQL(MIGRATION_1_TO_2_PART_1);
        db.execSQL(MIGRATION_1_TO_2_PART_2);
        db.execSQL(MIGRATION_1_TO_2_PART_3);
        db.execSQL(MIGRATION_2_TO_3_PART_1);
        db.execSQL(MIGRATION_2_TO_3_PART_2);
        db.execSQL(MIGRATION_3_TO_4);
        db.execSQL(MIGRATION_4_TO_5_PART_1);
        db.execSQL(MIGRATION_4_TO_5_PART_2);
        db.execSQL(MIGRATION_4_TO_5_PART_3);
        db.execSQL(MIGRATION_4_TO_5_PART_4);
        db.execSQL(MIGRATION_5_TO_6_PART_1);
        db.execSQL(MIGRATION_5_TO_6_PART_2);
        db.execSQL(MIGRATION_5_TO_6_PART_3);
        db.execSQL(MIGRATION_5_TO_6_PART_4);
        db.execSQL(MIGRATION_6_TO_7);
        db.execSQL(MIGRATION_7_TO_8_PART_1);
        db.execSQL(MIGRATION_7_TO_8_PART_2);
        db.execSQL(MIGRATION_7_TO_8_PART_3);
        db.execSQL(MIGRATION_8_TO_9);
        db.execSQL(MIGRATION_9_TO_10_PART_1);
        db.execSQL(MIGRATION_9_TO_10_PART_2);
        db.execSQL(MIGRATION_9_TO_10_PART_3);
        db.execSQL(MIGRATION_9_TO_10_PART_4);
        db.execSQL(MIGRATION_10_TO_11_PART_1);
        db.execSQL(MIGRATION_10_TO_11_PART_2);
        db.execSQL(MIGRATION_10_TO_11_PART_3);
        db.execSQL(MIGRATION_10_TO_11_PART_4);
        db.execSQL(MIGRATION_10_TO_11_PART_5);
        db.execSQL(MIGRATION_11_TO_12_PART_1);
        db.execSQL(MIGRATION_11_TO_12_PART_2);
        db.execSQL(MIGRATION_11_TO_12_PART_3);
        db.execSQL(MIGRATION_11_TO_12_PART_4);
        db.execSQL(MIGRATION_11_TO_12_PART_5);
        db.execSQL(MIGRATION_12_TO_13);
        db.execSQL(MIGRATION_13_TO_14);
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

        if (oldVersion < 4) {
            db.execSQL(MIGRATION_3_TO_4);
        }

        if (oldVersion < 5) {
            db.execSQL(MIGRATION_4_TO_5_PART_1);
            db.execSQL(MIGRATION_4_TO_5_PART_2);
            db.execSQL(MIGRATION_4_TO_5_PART_3);
            db.execSQL(MIGRATION_4_TO_5_PART_4);
        }

        if (oldVersion < 6) {
            db.execSQL(MIGRATION_5_TO_6_PART_1);
            db.execSQL(MIGRATION_5_TO_6_PART_2);
            db.execSQL(MIGRATION_5_TO_6_PART_3);
            db.execSQL(MIGRATION_5_TO_6_PART_4);
        }

        if (oldVersion < 7) {
            db.execSQL(MIGRATION_6_TO_7);
        }

        if (oldVersion < 8) {
            db.execSQL(MIGRATION_7_TO_8_PART_1);
            db.execSQL(MIGRATION_7_TO_8_PART_2);
            db.execSQL(MIGRATION_7_TO_8_PART_3);
        }

        if (oldVersion < 9) {
            db.execSQL(MIGRATION_8_TO_9);
        }

        if (oldVersion < 10) {
            db.execSQL(MIGRATION_9_TO_10_PART_1);
            db.execSQL(MIGRATION_9_TO_10_PART_2);
            db.execSQL(MIGRATION_9_TO_10_PART_3);
            db.execSQL(MIGRATION_9_TO_10_PART_4);
        }

        if (oldVersion < 11) {
            db.execSQL(MIGRATION_10_TO_11_PART_1);
            db.execSQL(MIGRATION_10_TO_11_PART_2);
            db.execSQL(MIGRATION_10_TO_11_PART_3);
            db.execSQL(MIGRATION_10_TO_11_PART_4);
            db.execSQL(MIGRATION_10_TO_11_PART_5);
        }

        if (oldVersion < 12) {
            db.execSQL(MIGRATION_11_TO_12_PART_1);
            db.execSQL(MIGRATION_11_TO_12_PART_2);
            db.execSQL(MIGRATION_11_TO_12_PART_3);
            db.execSQL(MIGRATION_11_TO_12_PART_4);
            db.execSQL(MIGRATION_11_TO_12_PART_5);
        }

        if (oldVersion < 13) {
            db.execSQL(MIGRATION_12_TO_13);
        }

        if (oldVersion < 14){
            db.execSQL(MIGRATION_13_TO_14);
        }
    }
}