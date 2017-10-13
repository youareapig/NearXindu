package com.mssd.data;

/**
 * Created by DELL on 2017/10/12.
 */

public class HtmlBean {

    /**
     * code : 4000
     * message : 成功
     * data : {"ischeck":0,"link":"http://192.168.10.130/api.php/Show/roomlist/id/5"}
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
         * ischeck : 0
         * link : http://192.168.10.130/api.php/Show/roomlist/id/5
         */

        private int ischeck;
        private String link;

        public int getIscheck() {
            return ischeck;
        }

        public void setIscheck(int ischeck) {
            this.ischeck = ischeck;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }
}
