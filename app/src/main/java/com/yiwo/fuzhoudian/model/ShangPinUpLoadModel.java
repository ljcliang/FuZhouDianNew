package com.yiwo.fuzhoudian.model;

import java.util.List;

public class ShangPinUpLoadModel {

    /**
     * goodsName : 苹果
     * goodsInfo : 红色的苹果
     * spec : [{"oldPrice":20,"nowPrice":10,"spec":"横撑黄绿蓝靛紫","allNum":100},{"oldPrice":20,"nowPrice":10,"spec":"横撑黄绿蓝靛紫","allNum":100}]
     * service : 1,2,3
     * tag : 1,2,3
     */

    private String goodsName;
    private String goodsInfo;
    private String service;
    private String if_fx;//是否是分销商品 0否 1是
    private String useType;//0正常物流 1到店使用
    private String tag;
    private String ad;//0不推广 1推广。
    private List<SpecBean> spec;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<SpecBean> getSpec() {
        return spec;
    }

    public void setSpec(List<SpecBean> spec) {
        this.spec = spec;
    }

    public String getIf_fx() {
        return if_fx;
    }

    public void setIf_fx(String if_fx) {
        this.if_fx = if_fx;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public static class SpecBean {
        /**
         * oldPrice : 20
         * nowPrice : 10
         * spec : 横撑黄绿蓝靛紫
         * allNum : 100
         * "specID": "29",
         * fx_bonus ：
         */

        private String oldPrice = "";
        private String nowPrice = "";
        private String spec = "";
        private String allNum = "";
        private String specID =  "0";
        private String fx_bonus = "";//提成 默认为0
        public String getOldPrice() {
            return oldPrice;
        }

        public void setOldPrice(String oldPrice) {
            this.oldPrice = oldPrice;
        }

        public String getNowPrice() {
            return nowPrice;
        }

        public void setNowPrice(String nowPrice) {
            this.nowPrice = nowPrice;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getAllNum() {
            return allNum;
        }

        public void setAllNum(String allNum) {
            this.allNum = allNum;
        }

        public String getSpecID() {
            return specID;
        }

        public void setSpecID(String specID) {
            this.specID = specID;
        }

        public String getFx_bonus() {
            return fx_bonus;
        }

        public void setFx_bonus(String fx_bonus) {
            this.fx_bonus = fx_bonus;
        }
    }
}
