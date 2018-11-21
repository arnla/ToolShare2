package com.toolshare.toolshare.models;

public class TransactionStatus {
    private int Id;
    private String statusName;

    /*****************************************************************************
     * DB Functions
     *
     */

    // TRANSACTION STATUS TABLE
    public static final String TABLE_TRANSACTION_STATUSES = "transaction_statuses";
    public static final String TRANSACTION_STATUS_COLUMN_ID = "id";
    public static final String TRANSACTION_STATUS_COLUMN_NAME = "status_name";
}
