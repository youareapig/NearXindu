package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/26.
 */

public class LocationBean {
    private String name;
    private Integer img;

    public LocationBean(String name, Integer img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }
}
