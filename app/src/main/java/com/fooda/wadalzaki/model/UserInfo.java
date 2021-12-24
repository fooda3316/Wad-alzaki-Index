package com.fooda.wadalzaki.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private String address;
    private String email;
    private String imagePath;
    private String name;
    private String numberThree;
    private String numberTwo;
    private String phoneNumber;
    private String uid;


    public UserInfo() {
    }

    public UserInfo(String address, String email, String imagePath, String name, String numberThree, String numberTwo, String phoneNumber, String uid) {
        this.address = address;
        this.email = email;
        this.imagePath = imagePath;
        this.name = name;
        this.numberThree = numberThree;
        this.numberTwo = numberTwo;
        this.phoneNumber = phoneNumber;
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getName() {
        return name;
    }

    public String getNumberThree() {
        return numberThree;
    }

    public String getNumberTwo() {
        return numberTwo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUid() {
        return uid;
    }
}
