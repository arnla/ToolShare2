package com.toolshare.toolshare.models;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.toolshare.toolshare.db.DbHandler;

import java.security.cert.CertificateExpiredException;

public class Card {
    private int Id;
    private String OwnerId;
    private User Owner;
    private String FullName;
    private String CardNumber;
    private int ExpiryMonth;
    private int ExpiryYear;
    private int Cvc;

    public Card() {}

    public Card(String ownerId, String fullName, String cardNumber, int expiryMonth, int expiryYear, int cvc) {
        OwnerId = ownerId;
        FullName = fullName;
        CardNumber = cardNumber;
        ExpiryMonth = expiryMonth;
        ExpiryYear = expiryYear;
        Cvc = cvc;
    }

    public Card(int id, String ownerId, String fullName, String cardNumber, int expiryMonth, int expiryYear, int cvc) {
        Id = id;
        OwnerId = ownerId;
        FullName = fullName;
        CardNumber = cardNumber;
        ExpiryMonth = expiryMonth;
        ExpiryYear = expiryYear;
        Cvc = cvc;
    }

    public int getId() {
        return Id;
    }

    public void setOwnerId(String id) {
        OwnerId = id;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwner(User owner) {
        Owner = owner;
    }

    public User getOwner() {
        return Owner;
    }

    public void setFullName(String fullName) {
        this.FullName = fullName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setCardNumber(String number) {
        CardNumber = number;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setExpiryMonth(int month) {
        ExpiryMonth = month;
    }

    public int getExpiryMonth() {
        return ExpiryMonth;
    }

    public void setExpiryYear(int expiryYear) {
        ExpiryYear = expiryYear;
    }

    public int getExpiryYear() {
        return ExpiryYear;
    }

    public void setCvc(int cvc) {
        Cvc = cvc;
    }

    public int getCvc() {
        return Cvc;
    }

    /*****************************************************************************
     * DB Functions
     *
     */

    public static String TABLE_CARDS = "cards";
    public static final String CARD_COLUMN_ID = "id";
    public static final String CARD_COLUMN_OWNER_ID = "owner_id";
    public static final String CARD_COLUMN_FULL_NAME = "full_name";
    public static final String CARD_COLUMN_CARD_NUMBER = "card_number";
    public static final String CARD_COLUMN_EXPIRY_MONTH = "expiry_month";
    public static final String CARD_COLUMN_EXPIRY_YEAR = "expiry_year";
    public static final String CARD_COLUMN_CVC = "cvc";

    public static void addCard(DbHandler dbHandler, Card card) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CARD_COLUMN_OWNER_ID, card.getOwnerId());
        values.put(CARD_COLUMN_FULL_NAME, card.getFullName());
        values.put(CARD_COLUMN_CARD_NUMBER, card.getCardNumber());
        values.put(CARD_COLUMN_EXPIRY_MONTH, card.getExpiryMonth());
        values.put(CARD_COLUMN_EXPIRY_YEAR, card.getExpiryYear());
        values.put(CARD_COLUMN_CVC, card.getCvc());

        db.insert(TABLE_CARDS, null, values);
        db.close();
    }

    public static void updateRequest(DbHandler dbHandler, Card card) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CARD_COLUMN_OWNER_ID, card.getOwnerId());
        values.put(CARD_COLUMN_FULL_NAME, card.getFullName());
        values.put(CARD_COLUMN_CARD_NUMBER, card.getCardNumber());
        values.put(CARD_COLUMN_EXPIRY_MONTH, card.getExpiryMonth());
        values.put(CARD_COLUMN_EXPIRY_YEAR, card.getExpiryYear());

        // updating row
        db.update(TABLE_CARDS, values, CARD_COLUMN_ID + " = ?", new String[] {Integer.toString(card.getId())});

        db.close();
    }

}
