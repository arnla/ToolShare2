package com.toolshare.toolshare.models;

import java.util.Date;

public class Request {
    private int Id;
    private String RequesterId;
    private String OwnerId;
    private User Requester;
    private User Owner;
    private int AdId;
    private Ad Ad;
    private Date RequestedStartDate;
    private Date RequestedEndDate;
    private String DeliveryMethod;
    private int StatusId;

    public int getId() {
        return Id;
    }

    public void setRequesterId(String id) {
        this.RequesterId = id;
    }

    public String getRequesterId() {
        return RequesterId;
    }

    public void setOwnerId(String id) {
        this.OwnerId = id;
    }

    public String getOwnerId() {
        return OwnerId;
    }

    public void setRequester(User user) {
        Requester = user;
    }

    public User getRequester() {
        return Requester;
    }

    public void setOwner(User user) {
        Owner = user;
    }

    public User getOwner() {
        return Owner;
    }

    public void setAdId(int id) {
        AdId = id;
    }

    public int getAdId() {
        return AdId;
    }

    public void setAd(Ad ad) {
        Ad = ad;
    }

    public Ad getAd() {
        return Ad;
    }

    public void setRequestedStartDate(Date date) {
        RequestedStartDate = date;
    }

    public Date getRequestedStartDate() {
        return RequestedStartDate;
    }

    public void setRequestedEndDate(Date date) {
        RequestedEndDate = date;
    }

    public Date getRequestedEndDate() {
        return RequestedEndDate;
    }

    public void setDeliveryMethod(String method) {
        DeliveryMethod = method;
    }

    public String getDeliveryMethod() {
        return DeliveryMethod;
    }

    public void setStatusId(int id) {
        StatusId = id;
    }

    public int getStatusId() {
        return StatusId;
    }
}
