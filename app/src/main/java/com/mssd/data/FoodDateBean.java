package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/26.
 */

public class FoodDateBean {

    /**
     * code : 2000
     * message : 获取数据成功
     * data : {"gastronome":[{"id":1,"gname":"我很好","url":"http://192.168.10.130/uploads/eat/20170912\\02eb8c3008cc18244f777ccfcf8671b5.jpg"}],"feast":[{"id":15,"fname":"第一个家宴","url":"http://192.168.10.130/uploads/eat/20170911\\33cc2e971485ac6ef3ac931e44a7d0d9.jpg"}],"canteen":[{"id":1,"sname":"啤酒馆","url":"http://192.168.10.130/uploads/shop/20170925/4a75290f9172c167903c477b1bb0b6c8.jpg","stitle":"你一口，我一口"},{"id":2,"sname":"清一色喝红酒","url":"http://192.168.10.130/uploads/shop/20170926/d49668763895ea738b2880d8b9151979.jpg","stitle":"哈哈"}]}
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
             * url : http://192.168.10.130/uploads/eat/20170912\02eb8c3008cc18244f777ccfcf8671b5.jpg
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

        public static class CanteenBean {
            /**
             * id : 1
             * sname : 啤酒馆
             * url : http://192.168.10.130/uploads/shop/20170925/4a75290f9172c167903c477b1bb0b6c8.jpg
             * stitle : 你一口，我一口
             */

            private int id;
            private String sname;
            private String url;
            private String stitle;

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
        }
    }
}
