package com.mssd.data;

/**
 * Created by DELL on 2017/9/27.
 */

public class TripClassfiyBean {
    private String name;
    private String cid;

    public TripClassfiyBean(String name, String cid) {
        this.name = name;
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public String getCid() {
        return cid;
    }
}
