package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/25.
 */

public class ShitangBean {

    /**
     * code : 2000
     * message : 成功
     * data : {"catInfo":[{"catname":"酒馆","id":1,"url":""},{"catname":"饭馆","id":2,"url":""},{"catname":"面馆","id":3,"url":""},{"catname":"茶馆","id":4,"url":""},{"catname":"素","id":5,"url":""}]}
     */

    private int code;
    private String message;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<CatInfoBean> catInfo;

        public List<CatInfoBean> getCatInfo() {
            return catInfo;
        }

        public void setCatInfo(List<CatInfoBean> catInfo) {
            this.catInfo = catInfo;
        }

        public static class CatInfoBean {
            /**
             * catname : 酒馆
             * id : 1
             * url :
             */

            private String catname;
            private int id;
            private String url;

            public String getCatname() {
                return catname;
            }

            public void setCatname(String catname) {
                this.catname = catname;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
