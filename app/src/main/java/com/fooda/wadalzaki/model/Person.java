package com.fooda.wadalzaki.model;


/**
 * Contact Instance Class
 * Created by leosunzh on 2015/12/12.
 */
public class Person {
    String id;
    String name;
    String number;
    String pinyin;//名字拼音，用于快速索引
    String color;//random color when there was no picture
    Boolean isFavorite;//Whether it's favorite
    int photo;

    public Person(String name, String number, int photo) {
        this.name = name;
        this.number = number;
        this.photo = photo;
    }
 public Person(String id, String name, String number){
        this.id = id;
        this.number = number;
        this.name = name;
    }

    public Person(String id, String number, String name, String pinyin){
        this.id = id;
        this.number = number;
        this.name = name;
        this.pinyin = pinyin;
    }


    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public int getPhoto() {
        return photo;
    }
    /* public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }*/

//    public String getPinYin() {
//        return pinyin;
//    }

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }
}
