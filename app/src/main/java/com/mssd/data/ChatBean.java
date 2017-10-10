package com.mssd.data;

import java.util.List;

/**
 * Created by DELL on 2017/10/10.
 */

public class ChatBean {

    /**
     * code : 3000
     * message : 获取成功
     * data : [{"message":"123","isreplay":1,"replay":"55555555k","repaytime":"2017-10-10 11:17:27","addtime":"2017-10-10 11:04:43"},{"message":"236","isreplay":0,"replay":"","repaytime":"","addtime":"2017-10-10 11:11:58"}]
     */

    private int code;
    private String message;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * message : 123
         * isreplay : 1
         * replay : 55555555k
         * repaytime : 2017-10-10 11:17:27
         * addtime : 2017-10-10 11:04:43
         */

        private String message;
        private int isreplay;
        private String replay;
        private String repaytime;
        private String addtime;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getIsreplay() {
            return isreplay;
        }

        public void setIsreplay(int isreplay) {
            this.isreplay = isreplay;
        }

        public String getReplay() {
            return replay;
        }

        public void setReplay(String replay) {
            this.replay = replay;
        }

        public String getRepaytime() {
            return repaytime;
        }

        public void setRepaytime(String repaytime) {
            this.repaytime = repaytime;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }
    }
}
