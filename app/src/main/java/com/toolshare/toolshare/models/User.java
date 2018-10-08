package com.toolshare.toolshare.models;

public class User {
    private String Email;
    private String FirstName;
    private String LastName;
    private String Phone;
    private String Password;

    public User() {
        this.Email = null;
        this.FirstName = null;
        this.LastName = null;
        this.Phone = null;
        this.Password = null;
    }

    public User( String email,String firstName, String lastName, String phone, String password) {
        this.Email = email;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Phone = phone;
        this.Password = password;
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
}
