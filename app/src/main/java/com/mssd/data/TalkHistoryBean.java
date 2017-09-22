package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/21.
 */

public class TalkHistoryBean {

    /**
     * code : 1000
     * message : 获取数据成功
     * data : [{"hname":"你猜12","htag":"一般","hintro":"很好啊","id":4,"url":"http://192.168.10.130/uploads/history/20170921/ac77de86f8f9d41584df64b58ea0b649.jpg","imgs":null}]
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
         * hname : 你猜12
         * htag : 一般
         * hintro : 很好啊
         * id : 4
         * url : http://192.168.10.130/uploads/history/20170921/ac77de86f8f9d41584df64b58ea0b649.jpg
         * imgs : null
         */

        private String hname;
        private String htag;
        private String hintro;
        private int id;
        private String url;
        private Object imgs;

        public String getHname() {
            return hname;
        }

        public void setHname(String hname) {
            this.hname = hname;
        }

        public String getHtag() {
            return htag;
        }

        public void setHtag(String htag) {
            this.htag = htag;
        }

        public String getHintro() {
            return hintro;
        }

        public void setHintro(String hintro) {
            this.hintro = hintro;
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

        public Object getImgs() {
            return imgs;
        }

        public void setImgs(Object imgs) {
            this.imgs = imgs;
        }
    }
}
