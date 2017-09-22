package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/21.
 */

public class HistoryIndexBean {

    /**
     * code : 1000
     * message : 获取数据成功
     * data : {"t1":{"title":"听他们摆哈儿历史","url":"http://192.168.10.130/uploads/history/20170914\\bc066a40c6ebdb0deeb4d760d32bc868.png"},"t2":[{"cid":2,"id":4,"hname":"你猜12","url":"http://192.168.10.130/uploads/history/20170921/ac77de86f8f9d41584df64b58ea0b649.jpg"}],"t3":{"title":"香城地理","url":"http://192.168.10.130/uploads/history/20170914\\3b1b84d549c104eb89707d39d42880e1.png"},"t4":[{"cid":4,"id":5,"hname":"农作物","url":"http://192.168.10.130/uploads/history/20170921/aeeeded3ce32573d49521c2669bcb5b1.jpg"}],"t5":{"title":"行政建制","url":"http://192.168.10.130/uploads/history/20170914\\fa844cabf35bf809959594fd8f8da8f1.png"},"t6":[{"cid":6,"id":6,"hname":"孙中三","url":"http://192.168.10.130/uploads/history/20170921/b1852f70b97215f26b281469127ce135.jpg"},{"cid":6,"id":7,"hname":"毛泽东","url":"http://192.168.10.130/uploads/history/20170921/eec88363020928a92678f6201e6d3d44.jpg"}]}
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
         * t1 : {"title":"听他们摆哈儿历史","url":"http://192.168.10.130/uploads/history/20170914\\bc066a40c6ebdb0deeb4d760d32bc868.png"}
         * t2 : [{"cid":2,"id":4,"hname":"你猜12","url":"http://192.168.10.130/uploads/history/20170921/ac77de86f8f9d41584df64b58ea0b649.jpg"}]
         * t3 : {"title":"香城地理","url":"http://192.168.10.130/uploads/history/20170914\\3b1b84d549c104eb89707d39d42880e1.png"}
         * t4 : [{"cid":4,"id":5,"hname":"农作物","url":"http://192.168.10.130/uploads/history/20170921/aeeeded3ce32573d49521c2669bcb5b1.jpg"}]
         * t5 : {"title":"行政建制","url":"http://192.168.10.130/uploads/history/20170914\\fa844cabf35bf809959594fd8f8da8f1.png"}
         * t6 : [{"cid":6,"id":6,"hname":"孙中三","url":"http://192.168.10.130/uploads/history/20170921/b1852f70b97215f26b281469127ce135.jpg"},{"cid":6,"id":7,"hname":"毛泽东","url":"http://192.168.10.130/uploads/history/20170921/eec88363020928a92678f6201e6d3d44.jpg"}]
         */

        private T1Bean t1;
        private T3Bean t3;
        private T5Bean t5;
        private List<T2Bean> t2;
        private List<T4Bean> t4;
        private List<T6Bean> t6;

        public T1Bean getT1() {
            return t1;
        }

        public void setT1(T1Bean t1) {
            this.t1 = t1;
        }

        public T3Bean getT3() {
            return t3;
        }

        public void setT3(T3Bean t3) {
            this.t3 = t3;
        }

        public T5Bean getT5() {
            return t5;
        }

        public void setT5(T5Bean t5) {
            this.t5 = t5;
        }

        public List<T2Bean> getT2() {
            return t2;
        }

        public void setT2(List<T2Bean> t2) {
            this.t2 = t2;
        }

        public List<T4Bean> getT4() {
            return t4;
        }

        public void setT4(List<T4Bean> t4) {
            this.t4 = t4;
        }

        public List<T6Bean> getT6() {
            return t6;
        }

        public void setT6(List<T6Bean> t6) {
            this.t6 = t6;
        }

        public static class T1Bean {
            /**
             * title : 听他们摆哈儿历史
             * url : http://192.168.10.130/uploads/history/20170914\bc066a40c6ebdb0deeb4d760d32bc868.png
             */

            private String title;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class T3Bean {
            /**
             * title : 香城地理
             * url : http://192.168.10.130/uploads/history/20170914\3b1b84d549c104eb89707d39d42880e1.png
             */

            private String title;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class T5Bean {
            /**
             * title : 行政建制
             * url : http://192.168.10.130/uploads/history/20170914\fa844cabf35bf809959594fd8f8da8f1.png
             */

            private String title;
            private String url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class T2Bean {
            /**
             * cid : 2
             * id : 4
             * hname : 你猜12
             * url : http://192.168.10.130/uploads/history/20170921/ac77de86f8f9d41584df64b58ea0b649.jpg
             */

            private int cid;
            private int id;
            private String hname;
            private String url;

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getHname() {
                return hname;
            }

            public void setHname(String hname) {
                this.hname = hname;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class T4Bean {
            /**
             * cid : 4
             * id : 5
             * hname : 农作物
             * url : http://192.168.10.130/uploads/history/20170921/aeeeded3ce32573d49521c2669bcb5b1.jpg
             */

            private int cid;
            private int id;
            private String hname;
            private String url;

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getHname() {
                return hname;
            }

            public void setHname(String hname) {
                this.hname = hname;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class T6Bean {
            /**
             * cid : 6
             * id : 6
             * hname : 孙中三
             * url : http://192.168.10.130/uploads/history/20170921/b1852f70b97215f26b281469127ce135.jpg
             */

            private int cid;
            private int id;
            private String hname;
            private String url;

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getHname() {
                return hname;
            }

            public void setHname(String hname) {
                this.hname = hname;
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
