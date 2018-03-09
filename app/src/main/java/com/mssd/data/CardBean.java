package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2018/2/7.
 */

public class CardBean {

    /**
     * code : 3000
     * message : 获取数据成功
     * data : [{"state":1,"coupon_code":12345,"shop_id":27,"url":"http://192.168.10.130/uploads/shop/20180207/9729a3f47f1ddde553d03a7649d6ac4c.jpg","type":1,"iswei":0,"weiurl":""}]
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
         * state : 1
         * coupon_code : 12345
         * shop_id : 27
         * url : http://192.168.10.130/uploads/shop/20180207/9729a3f47f1ddde553d03a7649d6ac4c.jpg
         * type : 1
         * iswei : 0
         * weiurl :
         */

        private int state;
        private int coupon_code;
        private int shop_id;
        private String url;
        private int type;
        private int iswei;
        private String weiurl;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getCoupon_code() {
            return coupon_code;
        }

        public void setCoupon_code(int coupon_code) {
            this.coupon_code = coupon_code;
        }

        public int getShop_id() {
            return shop_id;
        }

        public void setShop_id(int shop_id) {
            this.shop_id = shop_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getIswei() {
            return iswei;
        }

        public void setIswei(int iswei) {
            this.iswei = iswei;
        }

        public String getWeiurl() {
            return weiurl;
        }

        public void setWeiurl(String weiurl) {
            this.weiurl = weiurl;
        }
    }
}
