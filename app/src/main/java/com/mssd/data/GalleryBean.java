package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/26.
 */

public class GalleryBean {

    /**
     * code : 2000
     * message : 获取数据成功
     * data : [{"id":1,"url":"http://192.168.10.130/uploads/play/20170925/9e435fa72f7ca5cdb78d31c9cffc4722.jpg"},{"id":5,"url":"http://192.168.10.130/uploads/play/20170925/e00d2f81933e07319a42ac17eadc723a.jpg"},{"id":6,"url":"http://192.168.10.130/uploads/play/20170925/9cbd716289912d6dbb5fbc23aa9fc27d.jpg"}]
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
         * url : http://192.168.10.130/uploads/play/20170925/9e435fa72f7ca5cdb78d31c9cffc4722.jpg
         */

        private int id;
        private String url;

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
