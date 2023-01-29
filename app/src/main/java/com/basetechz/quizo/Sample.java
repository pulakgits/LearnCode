package com.basetechz.quizo;

public class Sample {

    private  String rank,img,name,coin;

    public Sample(){

    }
    public  Sample(String rank,String img,String name,String coin){
        this.rank = rank;
        this.img = img;
        this.name = name;
        this.coin = coin;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }
}
