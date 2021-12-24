package com.fooda.wadalzaki.model;

public class User {
    private String fullName;
    private String email;
    private String password;
    private String imageUri;
    private String loginType;

    public User(String fullName, String email, String password, String imageUri, String loginType) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.imageUri = imageUri;
        this.loginType = loginType;
    }

    public User() {
    }

//    public User(String fullName, String email, String imageUri) {
//        this.fullName = fullName;
//        this.email = email;
//        this.imageUri = imageUri;
//    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getImageUri() {
        return imageUri;
    }

    public String getLoginType() {
        return loginType;
    }
}
