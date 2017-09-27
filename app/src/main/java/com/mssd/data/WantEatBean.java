package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/27.
 */

public class WantEatBean {

    /**
     * code : 3000
     * message : 获取数据成功
     * data : [{"tid":3,"type":1,"url":"http://192.168.10.130/uploads/shop/20170922/76a9b61afa9381868cadd9abba8fa477.jpg","sname":"星部屋","stitle":"连住第五天免费","sprice":"25"}]
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
         * tid : 3
         * type : 1
         * url : http://192.168.10.130/uploads/shop/20170922/76a9b61afa9381868cadd9abba8fa477.jpg
         * sname : 星部屋
         * stitle : 连住第五天免费
         * sprice : 25
         */

        private int tid;
        private int type;
        private String url;
        private String sname;
        private String stitle;
        private String sprice;

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
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

        public String getSprice() {
            return sprice;
        }

        public void setSprice(String sprice) {
            this.sprice = sprice;
        }
    }
}
