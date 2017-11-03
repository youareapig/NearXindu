package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/26.
 */

public class TansuoBean {

    /**
     * code : 1000
     * message : 获取数据成功
     * data : {"banner":[{"content":"第一张","url":"http://www.qiecd.com/uploads/banner/20171026/b221224367de717641793bd9d2942f36.jpg","link":"http://www.baidu.com"},{"content":"你猜","url":"http://www.qiecd.com/uploads/banner/20171026/9562ab19a4efa0c05b63b0be0bf81629.jpg","link":"http://www.baidu.com"},{"content":"asdasdasdasd","url":"http://www.qiecd.com/uploads/banner/20171026/1e1052a1b12fa6fdfe846734f03b4baf.jpg","link":"www.baidu.com"}],"meal":[{"fname":"桂花煮雨","id":"15","url":"http://www.qiecd.com/uploads/eat/20171027/6c73cefc50cb981e2c323bb82e212c25.jpg"},{"fname":"桑葚果泥甜品","id":"17","url":"http://www.qiecd.com/uploads/eat/20171027/2ac1223e1e0c11cedfa5cc1101974053.jpg"},{"fname":"郝记牛肉米线","id":"18","url":"http://www.qiecd.com/uploads/eat/20171027/71a6a7de57414b859b395ba6ea20fd77.jpg"},{"fname":"首页家宴才22","id":"19","url":"http://www.qiecd.com/uploads/eat/20171027/aa0895e2a1d314225c6745381c650c91.jpg"}],"stay":[{"id":"3","stitle":"邀您与万物共眠","sname":"半山的院子","url":"http://www.qiecd.com/uploads/shop/20171027/2993bf83ada7a432ae7071422c4c8781.jpg"},{"id":"4","stitle":"心有远山安于当下","sname":"安隅小院","url":"http://www.qiecd.com/uploads/shop/20171027/df2ef437943b83948e2b37831966913b.jpg"},{"id":"5","stitle":"艺术园区里不一样的旅行","sname":"草藤屋","url":"http://www.qiecd.com/uploads/shop/20171027/f69f7388125a346916703f6dd14428f4.jpg"},{"id":"6","stitle":"台东绿岛近綠島漁港","sname":"香城","url":"http://www.qiecd.com/uploads/shop/20171027/47e7cdf3badc1981959654906d1eded1.jpg"},{"id":"7","stitle":"将艺术与分享融入未知的旅途","sname":"随息柒舍","url":"http://www.qiecd.com/uploads/shop/20171027/9f3e39bb232e669f6dd34745bbe1611a.jpg"},{"id":"8","stitle":"随心去做每一件想做的事","sname":"清风徐来","url":"http://www.qiecd.com/uploads/shop/20171027/7194c05a3182271d9d4099f080298829.jpg"},{"id":"17","stitle":"以梦为马，随处可栖","sname":"栖山居","url":"http://www.qiecd.com/uploads/shop/20171027/0be9717f22be45447c39d24c53753a51.jpg"}],"line":[{"id":"12","stitle":"千年古刹的秋韵","sname":"宝光寺","url":"http://www.qiecd.com/uploads/shop/20171027/f8d91c9e44d3b12623c0f400e35d851c.jpg"},{"id":"13","stitle":"隐匿于城市的彩林","sname":"三岔沟","url":"http://www.qiecd.com/uploads/shop/20171027/3975306ed5988a0cd83d114b1dcfc200.jpg"},{"id":"15","stitle":"回归初心的陶泥之旅","sname":"少儿时光体验基地","url":"http://www.qiecd.com/uploads/shop/20171027/5a14a7f968bdfb7018fd35f883ec6560.jpg"}]}
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
             * url : http://www.qiecd.com/uploads/banner/20171026/b221224367de717641793bd9d2942f36.jpg
             * link : http://www.baidu.com
             */

            private String content;
            private String url;
            private String link;

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

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        public static class MealBean {
            /**
             * fname : 桂花煮雨
             * id : 15
             * url : http://www.qiecd.com/uploads/eat/20171027/6c73cefc50cb981e2c323bb82e212c25.jpg
             */

            private String fname;
            private String id;
            private String url;

            public String getFname() {
                return fname;
            }

            public void setFname(String fname) {
                this.fname = fname;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
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
             * id : 3
             * stitle : 邀您与万物共眠
             * sname : 半山的院子
             * url : http://www.qiecd.com/uploads/shop/20171027/2993bf83ada7a432ae7071422c4c8781.jpg
             */

            private String id;
            private String stitle;
            private String sname;
            private String url;

            public String getId() {
                return id;
            }

            public void setId(String id) {
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

        public static class LineBean {
            /**
             * id : 12
             * stitle : 千年古刹的秋韵
             * sname : 宝光寺
             * url : http://www.qiecd.com/uploads/shop/20171027/f8d91c9e44d3b12623c0f400e35d851c.jpg
             */

            private String id;
            private String stitle;
            private String sname;
            private String url;

            public String getId() {
                return id;
            }

            public void setId(String id) {
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
