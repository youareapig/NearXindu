package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/26.
 */

public class PedestrianBean {

    /**
     * code : 2000
     * message : 获取数据成功
     * data : [{"id":9,"pname":"北京遇上西雅图","url":"http://192.168.10.130/uploads/play/20170926/f30f8d1b2cc5ba3d82677b2ae9502b4d.jpg"}]
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
         * id : 9
         * pname : 北京遇上西雅图
         * url : http://192.168.10.130/uploads/play/20170926/f30f8d1b2cc5ba3d82677b2ae9502b4d.jpg
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
