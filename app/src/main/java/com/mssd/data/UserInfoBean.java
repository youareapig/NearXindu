package com.mssd.data;

/**
 * Created by DELL on 2017/9/20.
 */

public class UserInfoBean {

    /**
     * code : 3000
     * message : 获取资料成功
     * data : {"gender":0,"nickname":"吃翔翔","birthday":"2017 - 09 - 24","sign":"","domicile":"阿坝藏族羌族自治州"}
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
         * gender : 0
         * nickname : 吃翔翔
         * birthday : 2017 - 09 - 24
         * sign :
         * domicile : 阿坝藏族羌族自治州
         */

        private int gender;
        private String nickname;
        private String birthday;
        private String sign;
        private String domicile;

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getDomicile() {
            return domicile;
        }

        public void setDomicile(String domicile) {
            this.domicile = domicile;
        }
    }
}
