package com.mssd.data;

/**
 * Created by DELL on 2017/8/30.
 */

public class FoodBean {
    private int img;
    private String name;

    public FoodBean(int img, String name) {
        this.img = img;
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
