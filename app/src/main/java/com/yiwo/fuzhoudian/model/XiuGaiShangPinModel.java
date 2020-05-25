package com.yiwo.fuzhoudian.model;

import java.util.List;

public class XiuGaiShangPinModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"goodsName":"测试编辑商品","goodsInfo":"这是一个测试编辑商品","spec":[{"specID":"29","oldPrice":"100.00","nowPrice":"99.00","spec":"个","allNum":"3"}],"service":[{"id":"1","name":"七天包退","info":"未满七天就给退货"},{"id":"3","name":"永久维修","info":"终身维修"}],"tag":[{"id":"1","name":"食品"},{"id":"2","name":"果蔬"},{"id":"3","name":"玩具"}],"picList":[{"picID":"30","picUrl":"http://www.tongbanapp.com/uploads/goods/20200428/c1192e134d2c946afb2694d773c8c4ab3704.jpg"}]}
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
         * goodsName : 测试编辑商品
         * goodsInfo : 这是一个测试编辑商品
         * spec : [{"specID":"29","oldPrice":"100.00","nowPrice":"99.00","spec":"个","allNum":"3"}]
         * service : [{"id":"1","name":"七天包退","info":"未满七天就给退货"},{"id":"3","name":"永久维修","info":"终身维修"}]
         * tag : [{"id":"1","name":"食品"},{"id":"2","name":"果蔬"},{"id":"3","name":"玩具"}]
         * picList : [{"picID":"30","picUrl":"http://www.tongbanapp.com/uploads/goods/20200428/c1192e134d2c946afb2694d773c8c4ab3704.jpg"}]
         */

        private String goodsName;
        private String goodsInfo;
        private List<ShangPinUpLoadModel.SpecBean> spec;
        private List<ShangPinServiceModel.ObjBean> service;
        private List<ShangPinLabelModel.ObjBean> tag;
        private List<PicListBean> picList;

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

        public List<ShangPinUpLoadModel.SpecBean> getSpec() {
            return spec;
        }

        public void setSpec(List<ShangPinUpLoadModel.SpecBean> spec) {
            this.spec = spec;
        }

        public List<ShangPinServiceModel.ObjBean> getService() {
            return service;
        }

        public void setService(List<ShangPinServiceModel.ObjBean> service) {
            this.service = service;
        }

        public List<ShangPinLabelModel.ObjBean> getTag() {
            return tag;
        }

        public void setTag(List<ShangPinLabelModel.ObjBean> tag) {
            this.tag = tag;
        }

        public List<PicListBean> getPicList() {
            return picList;
        }

        public void setPicList(List<PicListBean> picList) {
            this.picList = picList;
        }
        public static class PicListBean {
            /**
             * picID : 30
             * picUrl : http://www.tongbanapp.com/uploads/goods/20200428/c1192e134d2c946afb2694d773c8c4ab3704.jpg
             */

            private String picID;
            private String picUrl;

            public String getPicID() {
                return picID;
            }

            public void setPicID(String picID) {
                this.picID = picID;
            }

            public String getPicUrl() {
                return picUrl;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }
        }
    }
}
