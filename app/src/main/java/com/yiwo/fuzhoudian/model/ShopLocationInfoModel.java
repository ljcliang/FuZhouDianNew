package com.yiwo.fuzhoudian.model;

public class ShopLocationInfoModel {

    /**
     * code : 200
     * message : 操作成功!
     * obj : {"shopLat":"","shopLng":"","address":"黑龙江省-哈尔滨市"}
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
         * shopLat :
         * shopLng :
         * address : 黑龙江省-哈尔滨市
         */

        private String shopLat;
        private String shopLng;
        private String address;

        public String getShopLat() {
            return shopLat;
        }

        public void setShopLat(String shopLat) {
            this.shopLat = shopLat;
        }

        public String getShopLng() {
            return shopLng;
        }

        public void setShopLng(String shopLng) {
            this.shopLng = shopLng;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
