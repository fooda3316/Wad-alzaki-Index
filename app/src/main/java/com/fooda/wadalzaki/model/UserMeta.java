package com.fooda.wadalzaki.model;

import android.os.Parcel;
import android.os.Parcelable;

public class UserMeta implements Parcelable {

    public String userName;
    public String userEmail;
    public String profilePic;
    public String gender;

    public static final Creator<UserMeta> CREATOR = new Creator<UserMeta>() {

        @Override
        public UserMeta createFromParcel(Parcel parcel) {
            return new UserMeta(parcel);
        }

        @Override
        public UserMeta[] newArray(int size) {
            return new UserMeta[size];
        }
    };

    public UserMeta() {

    }

    public UserMeta(String userName, String userEmail, String profilePic) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.profilePic = profilePic;
    }

    private UserMeta(Parcel parcel) {
        userName = parcel.readString();
        userEmail = parcel.readString();
        profilePic = parcel.readString();
        gender = parcel.readString();
    }


    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeString(userName);
        parcel.writeString(userEmail);
        parcel.writeString(profilePic);
        parcel.writeString(gender);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
