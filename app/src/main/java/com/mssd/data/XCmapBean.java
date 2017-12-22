package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/12/18.
 */

public class XCmapBean {

    /**
     * code : 1000
     * message : 获取数据成功
     * data : {"title":"香城地图","imgs":["http://www.qiecd.com/uploads/history/20171114/5c9ae18f7ed8ab5d70f5bb9f2642051f.jpg"]}
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
         * title : 香城地图
         * imgs : ["http://www.qiecd.com/uploads/history/20171114/5c9ae18f7ed8ab5d70f5bb9f2642051f.jpg"]
         */

        private String title;
        private List<String> imgs;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }
    }
}
