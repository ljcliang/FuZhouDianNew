package com.yiwo.fuzhoudian.model;

import java.util.List;

public class SellerOrderModel {
    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"id":"17","order_sn":"2020052215233291973","addTime":"2020-05-22 15:23","payType":"2","status":"1","get_type":"0","sendPrice":"0.00","allMoney":"0.01","userID":"3","username":"花生","userpic":"https://fzd.91yiwo.com/uploads/header/2020/05/22/d842f5833a37096daac2e826a4adcce5159013397917.png","orderMes":"https://fzd.91yiwo.com/index.php/action/ac_goods/sellerOrderMessage?uid=3&oid=17","statusMes":"待处理","gList":[{"goodsID":"2","goodsName":"一个键盘","goodsImg":"https://fzd.91yiwo.com/uploads/goods/20200513/a68ec93a18f30d086ce44c5fc800de8a3322.jpg","goodsSpec":"1","price":"0.01","priceBuy":"0.01","buyNum":"1","specID":"3"}]}]
     */

    private int code;
    private String message;
    private List<ObjBean> obj;

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

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        /**
         * id : 17
         * order_sn : 2020052215233291973
         * addTime : 2020-05-22 15:23
         * payType : 2
         * status : 1
         * get_type : 0
         * sendPrice : 0.00
         * allMoney : 0.01
         * userID : 3
         * username : 花生
         * userpic : https://fzd.91yiwo.com/uploads/header/2020/05/22/d842f5833a37096daac2e826a4adcce5159013397917.png
         * orderMes : https://fzd.91yiwo.com/index.php/action/ac_goods/sellerOrderMessage?uid=3&oid=17
         * statusMes : 待处理
         * gList : [{"goodsID":"2","goodsName":"一个键盘","goodsImg":"https://fzd.91yiwo.com/uploads/goods/20200513/a68ec93a18f30d086ce44c5fc800de8a3322.jpg","goodsSpec":"1","price":"0.01","priceBuy":"0.01","buyNum":"1","specID":"3"}]
         */

        private String id;
        private String order_sn;
        private String addTime;
        private String payType;
        private String status;
        private String get_type;
        private String sendPrice;
        private String allMoney;
        private String userID;
        private String username;
        private String userpic;
        private String orderMes;
        private String statusMes;
        private List<GListBean> gList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGet_type() {
            return get_type;
        }

        public void setGet_type(String get_type) {
            this.get_type = get_type;
        }

        public String getSendPrice() {
            return sendPrice;
        }

        public void setSendPrice(String sendPrice) {
            this.sendPrice = sendPrice;
        }

        public String getAllMoney() {
            return allMoney;
        }

        public void setAllMoney(String allMoney) {
            this.allMoney = allMoney;
        }

        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserpic() {
            return userpic;
        }

        public void setUserpic(String userpic) {
            this.userpic = userpic;
        }

        public String getOrderMes() {
            return orderMes;
        }

        public void setOrderMes(String orderMes) {
            this.orderMes = orderMes;
        }

        public String getStatusMes() {
            return statusMes;
        }

        public void setStatusMes(String statusMes) {
            this.statusMes = statusMes;
        }

        public List<GListBean> getGList() {
            return gList;
        }

        public void setGList(List<GListBean> gList) {
            this.gList = gList;
        }

        public static class GListBean {
            /**
             * goodsID : 2
             * goodsName : 一个键盘
             * goodsImg : https://fzd.91yiwo.com/uploads/goods/20200513/a68ec93a18f30d086ce44c5fc800de8a3322.jpg
             * goodsSpec : 1
             * price : 0.01
             * priceBuy : 0.01
             * buyNum : 1
             * specID : 3
             */

            private String goodsID;
            private String goodsName;
            private String goodsImg;
            private String goodsSpec;
            private String price;
            private String priceBuy;
            private String buyNum;
            private String specID;

            public String getGoodsID() {
                return goodsID;
            }

            public void setGoodsID(String goodsID) {
                this.goodsID = goodsID;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsImg() {
                return goodsImg;
            }

            public void setGoodsImg(String goodsImg) {
                this.goodsImg = goodsImg;
            }

            public String getGoodsSpec() {
                return goodsSpec;
            }

            public void setGoodsSpec(String goodsSpec) {
                this.goodsSpec = goodsSpec;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getPriceBuy() {
                return priceBuy;
            }

            public void setPriceBuy(String priceBuy) {
                this.priceBuy = priceBuy;
            }

            public String getBuyNum() {
                return buyNum;
            }

            public void setBuyNum(String buyNum) {
                this.buyNum = buyNum;
            }

            public String getSpecID() {
                return specID;
            }

            public void setSpecID(String specID) {
                this.specID = specID;
            }
        }
    }
}
