package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/26.
 */

public class ShijiaBean {

    /**
     * code : 2000
     * message : 获取数据成功
     * data : [{"id":1,"gname":"我很好","url":"http://192.168.10.130/uploads/eat/20170926/a558d87bfbd289d0ed6350d78be32201.jpg"}]
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
         * gname : 我很好
         * url : http://192.168.10.130/uploads/eat/20170926/a558d87bfbd289d0ed6350d78be32201.jpg
         */

        private int id;
        private String gname;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
