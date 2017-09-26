package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/26.
 */

public class ExperienceBean {

    /**
     * code : 2000
     * message : 成功
     * data : {"catInfo":[{"catname":"户外活动","id":11,"url":"http://192.168.10.130/uploads/shop/20170922/30843478c82560cbee98a33a686ef779.jpg"}]}
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
             * catname : 户外活动
             * id : 11
             * url : http://192.168.10.130/uploads/shop/20170922/30843478c82560cbee98a33a686ef779.jpg
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
