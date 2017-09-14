package com.mssd.data;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by DELL on 2017/9/13.
 */
@Table(name = "bean", onCreated = "")
public class HistoryBean {
    @Column(name = "id", isId = true, autoGen = true, property = "NOT NULL")
    private int id;
    @Column(name = "text")
    private String text;

    public HistoryBean() {
    }

    public HistoryBean( String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
