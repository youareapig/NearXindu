package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/26.
 */

public class TansuoBean {

    /**
     * code : 1000
     * message : 获取数据成功
     * data : {"banner":[{"content":"第一张","url":"http://192.168.10.130/uploads/banner/20170926/9ceaaa12311494fb78aac1caa68b8304.jpg"},{"content":"你猜","url":"http://192.168.10.130/uploads/banner/20170926/7242f24fe1000c939c658ddf968ca5d0.jpg"},{"content":"asdasdasdasd","url":"http://192.168.10.130/uploads/banner/20170926/f4e71d44083b361e3089b3236f1b56db.jpg"}],"meal":[{"fname":"首页家宴才22","id":19,"url":"http://192.168.10.130/uploads/eat/20170926/30e2a6c2ad1a756e7f613c9593909e82.jpg"}],"stay":[{"id":17,"sname":"推荐店1","url":"http://192.168.10.130/uploads/shop/20170926/64a2ff714bc111ccafeab6e73a0a2f64.jpg"}],"line":[{"id":18,"stitle":"景色异人很好","sname":"推荐小景","url":"http://192.168.10.130/uploads/shop/20170926/cc1a45b55110e537a33edf6f4ee96977.jpg"}]}
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
        private List<BannerBean> banner;
        private List<MealBean> meal;
        private List<StayBean> stay;
        private List<LineBean> line;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<MealBean> getMeal() {
            return meal;
        }

        public void setMeal(List<MealBean> meal) {
            this.meal = meal;
        }

        public List<StayBean> getStay() {
            return stay;
        }

        public void setStay(List<StayBean> stay) {
            this.stay = stay;
        }

        public List<LineBean> getLine() {
            return line;
        }

        public void setLine(List<LineBean> line) {
            this.line = line;
        }

        public static class BannerBean {
            /**
             * content : 第一张
             * url : http://192.168.10.130/uploads/banner/20170926/9ceaaa12311494fb78aac1caa68b8304.jpg
             */

            private String content;
            private String url;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class MealBean {
            /**
             * fname : 首页家宴才22
             * id : 19
             * url : http://192.168.10.130/uploads/eat/20170926/30e2a6c2ad1a756e7f613c9593909e82.jpg
             */

            private String fname;
            private int id;
            private String url;

            public String getFname() {
                return fname;
            }

            public void setFname(String fname) {
                this.fname = fname;
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

        public static class StayBean {
            /**
             * id : 17
             * sname : 推荐店1
             * url : http://192.168.10.130/uploads/shop/20170926/64a2ff714bc111ccafeab6e73a0a2f64.jpg
             */

            private int id;
            private String sname;
            private String url;

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
        }

        public static class LineBean {
            /**
             * id : 18
             * stitle : 景色异人很好
             * sname : 推荐小景
             * url : http://192.168.10.130/uploads/shop/20170926/cc1a45b55110e537a33edf6f4ee96977.jpg
             */

            private int id;
            private String stitle;
            private String sname;
            private String url;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getStitle() {
                return stitle;
            }

            public void setStitle(String stitle) {
                this.stitle = stitle;
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
        }
    }
}
