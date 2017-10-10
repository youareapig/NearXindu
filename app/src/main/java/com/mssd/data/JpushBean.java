package com.mssd.data;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by DELL on 2017/9/13.
 */
@Table(name = "Jbean", onCreated = "")
public class JpushBean {
    @Column(name = "id", isId = true, autoGen = true, property = "NOT NULL")
    private int id;
    @Column(name = "content")
    private String content;
    @Column(name = "dates")
    private String dates;

    public JpushBean(String content, String dates) {
        this.content = content;
        this.dates = dates;
    }

    public JpushBean() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
