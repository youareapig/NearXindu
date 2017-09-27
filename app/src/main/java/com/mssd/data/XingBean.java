package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/27.
 */

public class XingBean {

    /**
     * code : 2000
     * message : 获取数据成功
     * data : [{"id":18,"sname":"推荐小景","stitle":"景色异人很好","url":"http://192.168.10.130/uploads/shop/20170926/cc1a45b55110e537a33edf6f4ee96977.jpg","uids":null,"sprice":"56","ischeck":0},{"id":14,"sname":"一号城市小景色","stitle":"很好啊不错的选择","url":"http://192.168.10.130/uploads/shop/20170926/995efb5e87ff6822a735315411eb8b70.jpg","uids":null,"sprice":"15","ischeck":0}]
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
         * id : 18
         * sname : 推荐小景
         * stitle : 景色异人很好
         * url : http://192.168.10.130/uploads/shop/20170926/cc1a45b55110e537a33edf6f4ee96977.jpg
         * uids : null
         * sprice : 56
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
