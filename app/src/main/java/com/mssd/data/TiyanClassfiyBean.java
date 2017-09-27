package com.mssd.data;

/**
 * Created by DELL on 2017/9/27.
 */

public class TiyanClassfiyBean {
    private String name;
    private Integer icon;
    private String id;

    public TiyanClassfiyBean(String name, Integer icon, String id) {
        this.name = name;
        this.icon = icon;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getIcon() {
        return icon;
    }

    public String getId() {
        return id;
    }
}
