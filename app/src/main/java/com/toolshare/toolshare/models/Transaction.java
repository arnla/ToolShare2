package com.toolshare.toolshare.models;

public class Transaction {
    private int Id;
    private int RequestId;
    private Request Request;
    private int CardId;
    private Card Card;
    private String BillingAddress;
    private int StatusId;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getRequestId() {
        return RequestId;
    }

    public void setRequestId(int requestId) {
        RequestId = requestId;
    }

    public Request getRequest() {
        return Request;
    }

    public void setRequest(Request request) {
        Request = request;
    }

    public int getCardId() {
        return CardId;
    }

    public void setCardId(int cardId) {
        CardId = cardId;
    }

    public Card getCard() {
        return Card;
    }

    public void setCard(com.toolshare.toolshare.models.Card card) {
        Card = card;
    }

    public String getBillingAddress() {
        return BillingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        BillingAddress = billingAddress;
    }

    public int getStatusId() {
        return StatusId;
    }

    public void setStatusId(int statusId) {
        StatusId = statusId;
    }

    /*****************************************************************************
     * DB Functions
     *
     */

    public static String TABLE_TRANSACTIONS = "transactions";
    public static final String TRANSACTION_COLUMN_ID = "id";
    public static final String TRANSACTION_COLUMN_REQUEST_ID = "request_id";
    public static final String TRANSACTION_COLUMN_CARD_ID = "card_id";
    public static final String TRANSACTION_COLUMN_BILLING_ADDRESS = "billing_address";
    public static final String TRANSACTION_COLUMN_STATUS_ID = "status_id";
}
