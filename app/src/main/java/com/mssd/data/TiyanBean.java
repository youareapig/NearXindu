package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/27.
 */

public class TiyanBean {

    /**
     * code : 2000
     * message : 获取数据成功
     * data : [{"id":19,"sname":"来自艺术的世界","stitle":"艺术使人精神世界充实","url":"http://192.168.10.130/uploads/shop/20170927/0473a9995b89795802cae7c27730d57b.jpg","uids":null,"sprice":"25","ischeck":0}]
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
         * id : 19
         * sname : 来自艺术的世界
         * stitle : 艺术使人精神世界充实
         * url : http://192.168.10.130/uploads/shop/20170927/0473a9995b89795802cae7c27730d57b.jpg
         * uids : null
         * sprice : 25
         * ischeck : 0
         */

        private int id;
        private String sname;
        private String stitle;
        private String url;
        private Object uids;
        private String sprice;
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

        public String getSprice() {
            return sprice;
        }

        public void setSprice(String sprice) {
            this.sprice = sprice;
        }

        public int getIscheck() {
            return ischeck;
        }

        public void setIscheck(int ischeck) {
            this.ischeck = ischeck;
        }
    }
}
