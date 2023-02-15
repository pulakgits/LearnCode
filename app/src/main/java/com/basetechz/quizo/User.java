package com.basetechz.quizo;

import com.google.firebase.firestore.SetOptions;

public class User {
    private String name,email,pass,image;
    private long coins;
    private String phoneNumber,state;
    SetOptions merge;
    private String  UserId;


    public User(){

    }
    public User(String name,String email,String pass){
        this.name = name;
        this.email = email;
        this.pass = pass;

    }

    public User(String name, String email, String pass, String image, long coins ){
        this.name = name;
        this.email = email;
        this.pass=pass;
        this.image=image;
        this.coins=coins;

    }

    public User(String name, String email, String pass , String phoneNumber , String state, SetOptions merge) {
        this.name= name;
        this.email = email;
        this.pass = pass;
        this.phoneNumber = phoneNumber;
        this.state = state;
        this.merge = merge;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public long getCoins() {
        return coins;
    }

    public void setCoins(long coins) {
        this.coins = coins;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
