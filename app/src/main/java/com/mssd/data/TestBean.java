package com.mssd.data;

/**
 * Created by DELL on 2017/8/31.
 */

public class TestBean {
    private Integer img;
    private String title1,title2;

    public TestBean(Integer img, String title1, String title2) {
        this.img = img;
        this.title1 = title1;
        this.title2 = title2;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }
}
