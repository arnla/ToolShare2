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

    // code to add the new user
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("email", user.getEmail()); // User email
        values.put("first_name", user.getFirstName()); // User first name
        values.put("last_name", user.getLastName()); // User last name
        values.put("phone", user.getPhone()); // User phone
        values.put("password", user.getPassword()); // User password

        // Inserting Row
        db.insert("users", null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single user
    public User getUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("users",
                new String[] {"email", "first_name", "last_name", "phone", "password"},
                "email =?",
                new String[] {email},
                null,
                null,
                null,
                null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));
        // return user
        return user;
    }

    // code to get all users in a list view
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM users";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setEmail(cursor.getString(0));
                user.setFirstName(cursor.getString(1));
                user.setLastName(cursor.getString(2));
                user.setPhone(cursor.getString(3));
                user.setPassword(cursor.getString(4));
                // Adding user to list
                userList.add(user);
            } while (cursor.moveToNext());
        }

        // return user list
        return userList;
    }

    // code to update the single user
    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("first_name", user.getFirstName());
        values.put("last_name", user.getLastName());
        values.put("phone", user.getPhone());
        values.put("password", user.getPassword());

        // updating row
        return db.update("users",
                values,
                "email = ?",
                new String[] {user.getEmail()});
    }

    // Deleting single user
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("users",
                "email = ?",
                new String[] {user.getEmail()});
        db.close();
    }

    // Getting users Count
    public int getUsersCount() {
        String countQuery = "SELECT  * FROM users";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}