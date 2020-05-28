package com.yiwo.fuzhoudian.model;

public class PeiSongSettingModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"id":"","money":"","userID":"","noMoney":"","zt":"","ps":"","canGet":""}
     */

    private int code;
    private String message;
    private ObjBean obj;

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

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * id :
         * money :
         * userID :
         * noMoney :
         * zt :
         * ps :
         * canGet :
         */

        private String id;
        private String money;
        private String userID;
        private String noMoney;
        private String zt;
        private String ps;
        private String canGet;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getNoMoney() {
            return noMoney;
        }

        public void setNoMoney(String noMoney) {
            this.noMoney = noMoney;
        }

        public String getZt() {
            return zt;
        }

        public void setZt(String zt) {
            this.zt = zt;
        }

        public String getPs() {
            return ps;
        }

        public void setPs(String ps) {
            this.ps = ps;
        }

        public String getCanGet() {
            return canGet;
        }

        public void setCanGet(String canGet) {
            this.canGet = canGet;
        }
    }
}
