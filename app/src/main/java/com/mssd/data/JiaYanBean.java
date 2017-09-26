package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/26.
 */

public class JiaYanBean {


    /**
     * code : 2000
     * message : 获取数据成功
     * data : [{"id":15,"fname":"第一个家宴","url":"http://192.168.10.130/uploads/eat/20170911\\33cc2e971485ac6ef3ac931e44a7d0d9.jpg"},{"id":17,"fname":"首页家宴","url":"http://192.168.10.130/uploads/eat/20170926/50591aca68c0eb464204816fbcdf2ba8.jpg"}]
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
         * id : 15
         * fname : 第一个家宴
         * url : http://192.168.10.130/uploads/eat/20170911\33cc2e971485ac6ef3ac931e44a7d0d9.jpg
         */

        private int id;
        private String fname;
        private String url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFname() {
            return fname;
        }

        public void setFname(String fname) {
            this.fname = fname;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
