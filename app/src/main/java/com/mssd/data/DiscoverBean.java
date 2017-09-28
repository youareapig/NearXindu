package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/9/28.
 */

public class DiscoverBean {

    /**
     * code : 1000
     * message : 获取数据成功
     * data : {"top":{"title":"听他们摆哈儿历史","url":"http://192.168.10.130/uploads/history/20170914/bc066a40c6ebdb0deeb4d760d32bc868.png"},"t1":{"pic":{"url":"http://192.168.10.130/uploads/banner/20170927/9fd7471483ffb2318a19c9bbb56b7e4e.jpg","content":"不可复制的人文风"},"data":[{"id":4,"hintro":"很好啊","url":"http://192.168.10.130/uploads/history/20170921/ac77de86f8f9d41584df64b58ea0b649.jpg"}]},"t2":{"pic":{"url":"http://192.168.10.130/uploads/banner/20170927/5f688220774bc3cb3b38ad6fa7bd6842.jpg","content":"行走在商城"},"data":[{"id":9,"pname":"北京遇上西雅图","lid":3,"url":"http://192.168.10.130/uploads/play/20170926/f30f8d1b2cc5ba3d82677b2ae9502b4d.jpg"},{"id":10,"pname":"老北京的酒","lid":3,"url":"http://192.168.10.130/uploads/play/20170927/da0a04d787b88b13be024b003421b93c.jpg"}]},"t3":{"pic":{"url":"http://192.168.10.130/uploads/banner/20170927/4f4f4d0439a91d5fa1f9624bc24787d6.jpg","content":"匠人造物"},"data":[{"id":7,"pname":"难得 .运动","lid":2,"url":"http://192.168.10.130/uploads/play/20170925/4b3b9559a023470ce933440f2ef18676.jpg"},{"id":8,"pname":"你猜 . 他是","lid":2,"url":"http://192.168.10.130/uploads/play/20170925/774910ce44140323c32909f1975c1adf.jpg"}]},"t4":{"pic":{"url":"http://192.168.10.130/uploads/banner/20170927/de4453bd4a683e353c04a4d4e6ef60e9.jpg","content":"从这片土地开出的花卷"},"data":[{"id":1,"pname":"画廊1","lid":1,"url":"http://192.168.10.130/uploads/play/20170925/9e435fa72f7ca5cdb78d31c9cffc4722.jpg"}]}}
     */

    private int code;
    private String message;
    private DataBeanXXXX data;

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

    public DataBeanXXXX getData() {
        return data;
    }

    public void setData(DataBeanXXXX data) {
        this.data = data;
    }

    public static class DataBeanXXXX {
        /**
         * top : {"title":"听他们摆哈儿历史","url":"http://192.168.10.130/uploads/history/20170914/bc066a40c6ebdb0deeb4d760d32bc868.png"}
         * t1 : {"pic":{"url":"http://192.168.10.130/uploads/banner/20170927/9fd7471483ffb2318a19c9bbb56b7e4e.jpg","content":"不可复制的人文风"},"data":[{"id":4,"hintro":"很好啊","url":"http://192.168.10.130/uploads/history/20170921/ac77de86f8f9d41584df64b58ea0b649.jpg"}]}
         * t2 : {"pic":{"url":"http://192.168.10.130/uploads/banner/20170927/5f688220774bc3cb3b38ad6fa7bd6842.jpg","content":"行走在商城"},"data":[{"id":9,"pname":"北京遇上西雅图","lid":3,"url":"http://192.168.10.130/uploads/play/20170926/f30f8d1b2cc5ba3d82677b2ae9502b4d.jpg"},{"id":10,"pname":"老北京的酒","lid":3,"url":"http://192.168.10.130/uploads/play/20170927/da0a04d787b88b13be024b003421b93c.jpg"}]}
         * t3 : {"pic":{"url":"http://192.168.10.130/uploads/banner/20170927/4f4f4d0439a91d5fa1f9624bc24787d6.jpg","content":"匠人造物"},"data":[{"id":7,"pname":"难得 .运动","lid":2,"url":"http://192.168.10.130/uploads/play/20170925/4b3b9559a023470ce933440f2ef18676.jpg"},{"id":8,"pname":"你猜 . 他是","lid":2,"url":"http://192.168.10.130/uploads/play/20170925/774910ce44140323c32909f1975c1adf.jpg"}]}
         * t4 : {"pic":{"url":"http://192.168.10.130/uploads/banner/20170927/de4453bd4a683e353c04a4d4e6ef60e9.jpg","content":"从这片土地开出的花卷"},"data":[{"id":1,"pname":"画廊1","lid":1,"url":"http://192.168.10.130/uploads/play/20170925/9e435fa72f7ca5cdb78d31c9cffc4722.jpg"}]}
         */

        private TopBean top;
        private T1Bean t1;
        private T2Bean t2;
        private T3Bean t3;
        private T4Bean t4;

        public TopBean getTop() {
            return top;
        }

        public void setTop(TopBean top) {
            this.top = top;
        }

        public T1Bean getT1() {
            return t1;
        }

        public void setT1(T1Bean t1) {
            this.t1 = t1;
        }

        public T2Bean getT2() {
            return t2;
        }

        public void setT2(T2Bean t2) {
            this.t2 = t2;
        }

        public T3Bean getT3() {
            return t3;
        }

        public void setT3(T3Bean t3) {
            this.t3 = t3;
        }

        public T4Bean getT4() {
            return t4;
        }

        public void setT4(T4Bean t4) {
            this.t4 = t4;
        }

        public static class TopBean {
            /**
             * title : 听他们摆哈儿历史
             * url : http://192.168.10.130/uploads/history/20170914/bc066a40c6ebdb0deeb4d760d32bc868.png
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

        public static class T1Bean {
            /**
             * pic : {"url":"http://192.168.10.130/uploads/banner/20170927/9fd7471483ffb2318a19c9bbb56b7e4e.jpg","content":"不可复制的人文风"}
             * data : [{"id":4,"hintro":"很好啊","url":"http://192.168.10.130/uploads/history/20170921/ac77de86f8f9d41584df64b58ea0b649.jpg"}]
             */

            private PicBean pic;
            private List<DataBean> data;

            public PicBean getPic() {
                return pic;
            }

            public void setPic(PicBean pic) {
                this.pic = pic;
            }

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public static class PicBean {
                /**
                 * url : http://192.168.10.130/uploads/banner/20170927/9fd7471483ffb2318a19c9bbb56b7e4e.jpg
                 * content : 不可复制的人文风
                 */

                private String url;
                private String content;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }

            public static class DataBean {
                /**
                 * id : 4
                 * hintro : 很好啊
                 * url : http://192.168.10.130/uploads/history/20170921/ac77de86f8f9d41584df64b58ea0b649.jpg
                 */

                private int id;
                private String hintro;
                private String url;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getHintro() {
                    return hintro;
                }

                public void setHintro(String hintro) {
                    this.hintro = hintro;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }

        public static class T2Bean {
            /**
             * pic : {"url":"http://192.168.10.130/uploads/banner/20170927/5f688220774bc3cb3b38ad6fa7bd6842.jpg","content":"行走在商城"}
             * data : [{"id":9,"pname":"北京遇上西雅图","lid":3,"url":"http://192.168.10.130/uploads/play/20170926/f30f8d1b2cc5ba3d82677b2ae9502b4d.jpg"},{"id":10,"pname":"老北京的酒","lid":3,"url":"http://192.168.10.130/uploads/play/20170927/da0a04d787b88b13be024b003421b93c.jpg"}]
             */

            private PicBeanX pic;
            private List<DataBeanX> data;

            public PicBeanX getPic() {
                return pic;
            }

            public void setPic(PicBeanX pic) {
                this.pic = pic;
            }

            public List<DataBeanX> getData() {
                return data;
            }

            public void setData(List<DataBeanX> data) {
                this.data = data;
            }

            public static class PicBeanX {
                /**
                 * url : http://192.168.10.130/uploads/banner/20170927/5f688220774bc3cb3b38ad6fa7bd6842.jpg
                 * content : 行走在商城
                 */

                private String url;
                private String content;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }

            public static class DataBeanX {
                /**
                 * id : 9
                 * pname : 北京遇上西雅图
                 * lid : 3
                 * url : http://192.168.10.130/uploads/play/20170926/f30f8d1b2cc5ba3d82677b2ae9502b4d.jpg
                 */

                private int id;
                private String pname;
                private int lid;
                private String url;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getPname() {
                    return pname;
                }

                public void setPname(String pname) {
                    this.pname = pname;
                }

                public int getLid() {
                    return lid;
                }

                public void setLid(int lid) {
                    this.lid = lid;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }

        public static class T3Bean {
            /**
             * pic : {"url":"http://192.168.10.130/uploads/banner/20170927/4f4f4d0439a91d5fa1f9624bc24787d6.jpg","content":"匠人造物"}
             * data : [{"id":7,"pname":"难得 .运动","lid":2,"url":"http://192.168.10.130/uploads/play/20170925/4b3b9559a023470ce933440f2ef18676.jpg"},{"id":8,"pname":"你猜 . 他是","lid":2,"url":"http://192.168.10.130/uploads/play/20170925/774910ce44140323c32909f1975c1adf.jpg"}]
             */

            private PicBeanXX pic;
            private List<DataBeanXX> data;

            public PicBeanXX getPic() {
                return pic;
            }

            public void setPic(PicBeanXX pic) {
                this.pic = pic;
            }

            public List<DataBeanXX> getData() {
                return data;
            }

            public void setData(List<DataBeanXX> data) {
                this.data = data;
            }

            public static class PicBeanXX {
                /**
                 * url : http://192.168.10.130/uploads/banner/20170927/4f4f4d0439a91d5fa1f9624bc24787d6.jpg
                 * content : 匠人造物
                 */

                private String url;
                private String content;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }

            public static class DataBeanXX {
                /**
                 * id : 7
                 * pname : 难得 .运动
                 * lid : 2
                 * url : http://192.168.10.130/uploads/play/20170925/4b3b9559a023470ce933440f2ef18676.jpg
                 */

                private int id;
                private String pname;
                private int lid;
                private String url;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getPname() {
                    return pname;
                }

                public void setPname(String pname) {
                    this.pname = pname;
                }

                public int getLid() {
                    return lid;
                }

                public void setLid(int lid) {
                    this.lid = lid;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }

        public static class T4Bean {
            /**
             * pic : {"url":"http://192.168.10.130/uploads/banner/20170927/de4453bd4a683e353c04a4d4e6ef60e9.jpg","content":"从这片土地开出的花卷"}
             * data : [{"id":1,"pname":"画廊1","lid":1,"url":"http://192.168.10.130/uploads/play/20170925/9e435fa72f7ca5cdb78d31c9cffc4722.jpg"}]
             */

            private PicBeanXXX pic;
            private List<DataBeanXXX> data;

            public PicBeanXXX getPic() {
                return pic;
            }

            public void setPic(PicBeanXXX pic) {
                this.pic = pic;
            }

            public List<DataBeanXXX> getData() {
                return data;
            }

            public void setData(List<DataBeanXXX> data) {
                this.data = data;
            }

            public static class PicBeanXXX {
                /**
                 * url : http://192.168.10.130/uploads/banner/20170927/de4453bd4a683e353c04a4d4e6ef60e9.jpg
                 * content : 从这片土地开出的花卷
                 */

                private String url;
                private String content;

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }
            }

            public static class DataBeanXXX {
                /**
                 * id : 1
                 * pname : 画廊1
                 * lid : 1
                 * url : http://192.168.10.130/uploads/play/20170925/9e435fa72f7ca5cdb78d31c9cffc4722.jpg
                 */

                private int id;
                private String pname;
                private int lid;
                private String url;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getPname() {
                    return pname;
                }

                public void setPname(String pname) {
                    this.pname = pname;
                }

                public int getLid() {
                    return lid;
                }

                public void setLid(int lid) {
                    this.lid = lid;
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
}
