package com.toolshare.toolshare.models;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.toolshare.toolshare.db.DbHandler;

public class ToolAddress {
    private int Id;
    private int ToolId;
    private String StreetAddress;
    private String City;
    private String Province;
    private String ZipCode;
    private String Country;

    public ToolAddress() {}

    public ToolAddress(int toolId, String streetAddress, String city, String province, String zipCode, String country) {
        ToolId = toolId;
        StreetAddress = streetAddress;
        City = city;
        Province = province;
        ZipCode = zipCode;
        Country = country;
    }

    public ToolAddress(int id, int toolId, String streetAddress, String city, String province, String zipCode, String country) {
        Id = id;
        ToolId = toolId;
        StreetAddress = streetAddress;
        City = city;
        Province = province;
        ZipCode = zipCode;
        Country = country;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getToolId() {
        return ToolId;
    }

    public void setToolId(int toolId) {
        ToolId = toolId;
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

    // TOOL ADDRESS TABLE
    public static final String TABLE_TOOL_ADDRESS = "tool_addresses";
    public static final String TOOL_ADDRESS_COLUMN_ID = "id";
    public static final String TOOL_ADDRESS_COLUMN_TOOL_ID = "tool_id";
    public static final String TOOL_ADDRESS_COLUMN_STREET_ADDRESS = "street_address";
    public static final String TOOL_ADDRESS_COLUMN_CITY = "city";
    public static final String TOOL_ADDRESS_COLUMN_PROVINCE = "province";
    public static final String TOOL_ADDRESS_COLUMN_ZIP_CODE = "zip_code";
    public static final String TOOL_ADDRESS_COLUMN_COUNTRY = "country";

    public static void addToolAddress(DbHandler dbHander, ToolAddress toolAddress) {
        SQLiteDatabase db = dbHander.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TOOL_ADDRESS_COLUMN_TOOL_ID, toolAddress.getToolId());
        values.put(TOOL_ADDRESS_COLUMN_STREET_ADDRESS, toolAddress.getStreetAddress());
        values.put(TOOL_ADDRESS_COLUMN_CITY, toolAddress.getCity());
        values.put(TOOL_ADDRESS_COLUMN_PROVINCE, toolAddress.getProvince());
        values.put(TOOL_ADDRESS_COLUMN_ZIP_CODE, toolAddress.getZipCode());
        values.put(TOOL_ADDRESS_COLUMN_COUNTRY, toolAddress.getCountry());
        // Inserting Row
        db.insert(TABLE_TOOL_ADDRESS, null, values);
        db.close();
    }
}
