package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/26.
 */

public class GoodsBean {

    /**
     * code : 2000
     * message : 获取数据成功
     * data : [{"id":7,"pname":"难得 .运动","url":"http://192.168.10.130/uploads/play/20170925/4b3b9559a023470ce933440f2ef18676.jpg"},{"id":8,"pname":"你猜 . 他是","url":"http://192.168.10.130/uploads/play/20170925/774910ce44140323c32909f1975c1adf.jpg"}]
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
         * id : 7
         * pname : 难得 .运动
         * url : http://192.168.10.130/uploads/play/20170925/4b3b9559a023470ce933440f2ef18676.jpg
         */

        private int id;
        private String pname;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPname() {
            return pname;
        }

        public void setPname(String pname) {
            this.pname = pname;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
