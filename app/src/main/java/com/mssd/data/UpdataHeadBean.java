package com.mssd.data;

/**
 * Created by DELL on 2017/9/20.
 */

public class UpdataHeadBean {

    /**
     * code : 3003
     * message : 修改头像成功
     * data : {"headpic":"http://192.168.10.130/uploads/user/20170920\\82f3732f961bf619e0616028a5398f5f.jpg"}
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
         * headpic : http://192.168.10.130/uploads/user/20170920\82f3732f961bf619e0616028a5398f5f.jpg
         */

        private String headpic;

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }
    }
}
