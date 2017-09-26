package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/22.
 */

public class StayBean {

    /**
     * code : 2000
     * message : 成功
     * data : {"catInfo":[{"catname":" 民宿","id":6,"url":""},{"catname":"客栈","id":7,"url":""},{"catname":"酒店","id":8,"url":""},{"catname":"青旅","id":9,"url":""},{"catname":"露营","id":10,"url":""}],"banner":"http://192.168.10.130/uploads/banner/20170922/65147d5df20660c6a02f9af3fd7a658e.jpg"}
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
        /**
         * catInfo : [{"catname":" 民宿","id":6,"url":""},{"catname":"客栈","id":7,"url":""},{"catname":"酒店","id":8,"url":""},{"catname":"青旅","id":9,"url":""},{"catname":"露营","id":10,"url":""}]
         * banner : http://192.168.10.130/uploads/banner/20170922/65147d5df20660c6a02f9af3fd7a658e.jpg
         */

        private String banner;
        private List<CatInfoBean> catInfo;

        public String getBanner() {
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public List<CatInfoBean> getCatInfo() {
            return catInfo;
        }

        public void setCatInfo(List<CatInfoBean> catInfo) {
            this.catInfo = catInfo;
        }

        public static class CatInfoBean {
            /**
             * catname :  民宿
             * id : 6
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
