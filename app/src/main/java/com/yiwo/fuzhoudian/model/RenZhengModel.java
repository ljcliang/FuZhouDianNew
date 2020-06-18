package com.yiwo.fuzhoudian.model;

public class RenZhengModel {

    /**
     * code : 200
     * message : 待签约!
     * obj : {"sign":"2","url":"https://pay.weixin.qq.com/public/apply4ec_sign/s?applymentId=2000002145415047&sign=25f509122847df6923f2d6fd3dc7105c","mes":"待签约","erWeiCode":"https://fzd.91yiwo.com/index.php/action/ac_user/payImgstr?uid=3"}
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
         * sign : 2
         * url : https://pay.weixin.qq.com/public/apply4ec_sign/s?applymentId=2000002145415047&sign=25f509122847df6923f2d6fd3dc7105c
         * mes : 待签约
         * erWeiCode : https://fzd.91yiwo.com/index.php/action/ac_user/payImgstr?uid=3
         */

        private String sign;
        private String url;
        private String mes;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMes() {
            return mes;
        }

        public void setMes(String mes) {
            this.mes = mes;
        }
    }
}
