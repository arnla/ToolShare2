package com.toolshare.toolshare.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.toolshare.toolshare.models.User;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ToolshareDB";

    // USER TABLE
    public static final String TABLE_USERS = "users";
    public static final String USERS_COLUMN_EMAIL = "email";
    public static final String USERS_COLUMN_FIRST_NAME = "first_name";
    public static final String USERS_COLUMN_LAST_NAME = "last_name";
    public static final String USERS_COLUMN_PHONE = "phone";
    public static final String USERS_COLUMN_PASSWORD = "password";
    public static final String USERS_COLUMN_LOCATION = "location";

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // MIGRATIONS
/*    private static final String MIGRATION_1_TO_2 = "ALTER TABLE "
            + TABLE_USERS + " ADD " + USERS_COLUMN_LOCATION + " TEXT DEFAULT null;";

    private static final String MIGRATION_2_TO_3_PART_1 = "ALTER TABLE "
            + TABLE_USERS + " RENAME TO users_old;";
    private static final String MIGRATION_2_TO_3_PART_2 = "CREATE TABLE users ("
            + USERS_COLUMN_EMAIL + " text primary key, "
            + USERS_COLUMN_FIRST_NAME + " text NOT NULL,"
            + USERS_COLUMN_LAST_NAME + " text NOT NULL,"
            + USERS_COLUMN_PHONE + " text,"
            + USERS_COLUMN_PASSWORD + " text NOT NULL)";
    private static final String MIGRATION_2_TO_3_PART_3 = "INSERT INTO users ("
            + USERS_COLUMN_EMAIL + ", "
            + USERS_COLUMN_FIRST_NAME + ", "
            + USERS_COLUMN_LAST_NAME + ", "
            + USERS_COLUMN_PHONE + ", "
            + USERS_COLUMN_PASSWORD + ") "
            + "SELECT "
            + USERS_COLUMN_EMAIL + ", "
            + USERS_COLUMN_FIRST_NAME + ", "
            + USERS_COLUMN_LAST_NAME + ", "
            + USERS_COLUMN_PHONE + ", "
            + USERS_COLUMN_PASSWORD
            + " FROM users_old";*/

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
/*        if (oldVersion < 2) {
            db.execSQL(MIGRATION_1_TO_2);
        }

        if (oldVersion < 3) {
            db.execSQL(MIGRATION_2_TO_3_PART_1);
            db.execSQL(MIGRATION_2_TO_3_PART_2);
            db.execSQL(MIGRATION_2_TO_3_PART_3);
        }*/
    }
}