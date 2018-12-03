package com.toolshare.toolshare.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.toolshare.toolshare.db.DbHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private String Email;
    private String FirstName;
    private String LastName;
    private String Phone;
    private String Password;
    private String StreetAddress;
    private String City;
    private String Province;
    private String ZipCode;
    private String Country;

    public User() {

    }

    public User(String email, String firstName, String lastName, String phone, String password, String streetAddress,
                String city, String province, String zipCode, String country) {
        this.Email = email;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Phone = phone;
        this.Password = password;
        this.StreetAddress = streetAddress;
        this.City = city;
        this.Province = province;
        this.ZipCode = zipCode;
        this.Country = country;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getStreetAddress() {
        return StreetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        StreetAddress = streetAddress;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getZipCode() {
        return ZipCode;
    }

    public void setZipCode(String zipCode) {
        ZipCode = zipCode;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    /*****************************************************************************
     * DB Functions
     *
     */

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

    // code to add the new user
    public void addUser(DbHandler dbHandler) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("email", this.getEmail());
        values.put("first_name", this.getFirstName());
        values.put("last_name", this.getLastName());
        values.put("phone", this.getPhone());
        values.put("password", this.getPassword());
        values.put(USERS_COLUMN_STREET_ADDRESS, this.getStreetAddress());
        values.put(USERS_COLUMN_CITY, this.getCity());
        values.put(USERS_COLUMN_PROVINCE, this.getProvince());
        values.put(USERS_COLUMN_ZIP_CODE, this.getZipCode());
        values.put(USERS_COLUMN_COUNTRY, this.getCountry());

        // Inserting Row
        db.insert("users", null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single user
    public static User getUser(DbHandler dbHandler, String email) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from users where email = ?", new String[] {email});
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getString(9));

        cursor.close();
        db.close();
        // return user
        return user;
    }

    // code to get all users in a list view
    public List<User> getAllUsers(DbHandler dbHandler) {
        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM users";

        SQLiteDatabase db = dbHandler.getWritableDatabase();
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

        cursor.close();
        db.close();
        // return user list
        return userList;
    }

    // code to update the single user
    public int updateUser(DbHandler dbHandler, User user) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("first_name", user.getFirstName());
        values.put("last_name", user.getLastName());
        values.put("phone", user.getPhone());
        values.put("password", user.getPassword());

        db.close();
        // updating row
        return db.update("users",
                values,
                "email = ?",
                new String[] {user.getEmail()});
    }

    // Deleting single user
    public void deleteUser(DbHandler dbHandler, User user) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete("users",
                "email = ?",
                new String[] {user.getEmail()});
        db.close();
    }

    // Getting users Count
    public int getUsersCount(DbHandler dbHandler) {
        String countQuery = "SELECT  * FROM users";
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        db.close();
        // return count
        return cursor.getCount();
    }

    public static String getUserNameByPk(DbHandler dbHandler, String owner) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("select first_name, last_name from users where email = ?", new String[] {owner});

        if (cursor.moveToFirst()) {
            String username = cursor.getString(0) + " " + cursor.getString(1);
            cursor.close();
            db.close();
            return username;
        }

        cursor.close();
        db.close();
        return "";
    }
}
