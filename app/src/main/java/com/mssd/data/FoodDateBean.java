package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/26.
 */

public class FoodDateBean {

    /**
     * code : 2000
     * message : 获取数据成功
     * data : {"gastronome":[{"id":1,"gname":"我很好","url":"http://192.168.10.130/uploads/eat/20170926/a558d87bfbd289d0ed6350d78be32201.jpg"}],"feast":[{"id":15,"fname":"第一个家宴","url":"http://192.168.10.130/uploads/eat/20170926/bea9ff2db21c497bc708cc9026ac87c0.jpg"},{"id":17,"fname":"首页家宴","url":"http://192.168.10.130/uploads/eat/20170926/38e97aff247c61b7c7bb13d7c28f4efe.jpg"},{"id":18,"fname":"红烧肉","url":"http://192.168.10.130/uploads/eat/20170926/6e336b87ad31d53df7efbfea12d9d8d7.jpg"},{"id":19,"fname":"首页家宴才22","url":"http://192.168.10.130/uploads/eat/20170926/30e2a6c2ad1a756e7f613c9593909e82.jpg"}],"canteen":[{"id":1,"sname":"啤酒馆","url":"http://192.168.10.130/uploads/shop/20170925/4a75290f9172c167903c477b1bb0b6c8.jpg","stitle":"你一口，我一口","uids":"1,2,3","ischeck":0},{"id":2,"sname":"清一色喝红酒","url":"http://192.168.10.130/uploads/shop/20170926/d49668763895ea738b2880d8b9151979.jpg","stitle":"哈哈","uids":"1,2,3","ischeck":0}]}
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
        private List<GastronomeBean> gastronome;
        private List<FeastBean> feast;
        private List<CanteenBean> canteen;

        public List<GastronomeBean> getGastronome() {
            return gastronome;
        }

        public void setGastronome(List<GastronomeBean> gastronome) {
            this.gastronome = gastronome;
        }

        public List<FeastBean> getFeast() {
            return feast;
        }

        public void setFeast(List<FeastBean> feast) {
            this.feast = feast;
        }

        public List<CanteenBean> getCanteen() {
            return canteen;
        }

        public void setCanteen(List<CanteenBean> canteen) {
            this.canteen = canteen;
        }

        public static class GastronomeBean {
            /**
             * id : 1
             * gname : 我很好
             * url : http://192.168.10.130/uploads/eat/20170926/a558d87bfbd289d0ed6350d78be32201.jpg
             */

            private int id;
            private String gname;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getGname() {
                return gname;
            }

            public void setGname(String gname) {
                this.gname = gname;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class FeastBean {
            /**
             * id : 15
             * fname : 第一个家宴
             * url : http://192.168.10.130/uploads/eat/20170926/bea9ff2db21c497bc708cc9026ac87c0.jpg
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

        public static class CanteenBean {
            /**
             * id : 1
             * sname : 啤酒馆
             * url : http://192.168.10.130/uploads/shop/20170925/4a75290f9172c167903c477b1bb0b6c8.jpg
             * stitle : 你一口，我一口
             * uids : 1,2,3
             * ischeck : 0
             */

            private int id;
            private String sname;
            private String url;
            private String stitle;
            private String uids;
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

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getStitle() {
                return stitle;
            }

            public void setStitle(String stitle) {
                this.stitle = stitle;
            }

            public String getUids() {
                return uids;
            }

            public void setUids(String uids) {
                this.uids = uids;
            }

            public int getIscheck() {
                return ischeck;
            }

            public void setIscheck(int ischeck) {
                this.ischeck = ischeck;
            }
        }
    }
}
