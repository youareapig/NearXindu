package com.mssd.utils;

/**
 * Created by Dell on 2017/3/7.
 * 单例模式实现全局变量
 */
public class SingleModleUrl {
//    private String imgUrl = "http://192.168.10.130/";
//    private String testUrl = "http://192.168.10.130/api.php/";

    private String imgUrl = "http://www.qiecd.com/";
    private String testUrl = "http://www.qiecd.com/api.php/";

//    private String imgUrl = "http://192.168.10.253/";
//    private String testUrl = "http://192.168.10.253/api.php/";

    public String getImgUrl() {
        return imgUrl;
    }

    private SingleModleUrl() {
    }

    public String getTestUrl() {
        return testUrl;
    }

    private static class SingleModleUrlHolder {
        private static SingleModleUrl singleModleUrl = new SingleModleUrl();
    }

    public static SingleModleUrl singleModleUrl() {
        return SingleModleUrlHolder.singleModleUrl;
    }
}
