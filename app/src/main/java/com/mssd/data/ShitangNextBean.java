package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/25.
 */

public class ShitangNextBean {

    /**
     * code : 2000
     * message : 获取数据成功
     * data : [{"id":1,"sname":"啤酒馆","stitle":"你一口，我一口","url":"http://192.168.10.130/uploads/shop/20170925/4a75290f9172c167903c477b1bb0b6c8.jpg","uids":null,"ischeck":0},{"id":2,"sname":"清一色喝红酒","stitle":"哈哈","url":"http://192.168.10.130/uploads/shop/20170908\\a582e9c798d548128a05a21bb1bfb9e6.jpg","uids":null,"ischeck":0},{"id":11,"sname":"叶婆婆饭馆","stitle":"好吃不上火","url":"http://192.168.10.130/uploads/shop/20170925/e7b8dfeb049dfce7485124befe9f0259.jpg","uids":null,"ischeck":0}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * sname : 啤酒馆
         * stitle : 你一口，我一口
         * url : http://192.168.10.130/uploads/shop/20170925/4a75290f9172c167903c477b1bb0b6c8.jpg
         * uids : null
         * ischeck : 0
         */

        private int id;
        private String sname;
        private String stitle;
        private String url;
        private Object uids;
        private int ischeck;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getStitle() {
            return stitle;
        }

        public void setStitle(String stitle) {
            this.stitle = stitle;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Object getUids() {
            return uids;
        }

        public void setUids(Object uids) {
            this.uids = uids;
        }

        public int getIscheck() {
            return ischeck;
        }

        public void setIscheck(int ischeck) {
            this.ischeck = ischeck;
        }
    }
}
