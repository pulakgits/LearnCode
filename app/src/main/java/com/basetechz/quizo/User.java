package com.basetechz.quizo;

import android.net.Uri;

public class User {
    private String name,email,pass,image;
    private long coins;

    public User(){

    }

    public User(String name,String email,String pass,String image,long coins){
        this.name = name;
        this.email = email;
        this.pass=pass;
        this.image=image;
        this.coins=coins;

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
}
