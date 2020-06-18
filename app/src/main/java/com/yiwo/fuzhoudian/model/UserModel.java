package com.yiwo.fuzhoudian.model;

/**
 * Created by Administrator on 2018/7/24.
 */

public class UserModel {
    /**
     * code : 200
     * message : 获取成功!
     * obj : {"shopUrl":"https://fzd.91yiwo.com/wxweb/wx_goods/goodsList?uid=3","headeimg":"https://fzd.91yiwo.com/uploads/header/2020/06/18/8403a0eb1cbb9ad4a082535cecac8742159246588413.png","username":"花生仓买店","sex":"0","useraddress":"黑龙江省哈尔滨市松北区丰源街","userautograph":"食品日杂百货","userbirthday":"暂未设置生日","usertime":"2020-05-11 16:24:04","usercodeok":"已认证","usermarry":"1","usergrade":"0","sign":"0","vip":"0","news":"0","Friendnote":"7","Focusonnews":"0","Activitymessage":"0","type":"0","video_num":"1","fm_num":"7","goods_num":"4","like_num":"0","tel":"110"}
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
         * shopUrl : https://fzd.91yiwo.com/wxweb/wx_goods/goodsList?uid=3
         * headeimg : https://fzd.91yiwo.com/uploads/header/2020/06/18/8403a0eb1cbb9ad4a082535cecac8742159246588413.png
         * username : 花生仓买店
         * sex : 0
         * useraddress : 黑龙江省哈尔滨市松北区丰源街
         * userautograph : 食品日杂百货
         * userbirthday : 暂未设置生日
         * usertime : 2020-05-11 16:24:04
         * usercodeok : 已认证
         * usermarry : 1
         * usergrade : 0
         * sign : 0
         * vip : 0
         * news : 0
         * Friendnote : 7
         * Focusonnews : 0
         * Activitymessage : 0
         * type : 0
         * video_num : 1
         * fm_num : 7
         * goods_num : 4
         * like_num : 0
         * tel : 110
         */

        private String shopUrl;
        private String headeimg;
        private String username;
        private String sex;
        private String useraddress;
        private String userautograph;
        private String userbirthday;
        private String usertime;
        private String usercodeok;
        private String usermarry;
        private String usergrade;
        private String sign;
        private String vip;
        private String news;
        private String Friendnote;
        private String Focusonnews;
        private String Activitymessage;
        private String type;
        private String video_num;
        private String fm_num;
        private String goods_num;
        private String like_num;
        private String tel;

        public String getShopUrl() {
            return shopUrl;
        }

        public void setShopUrl(String shopUrl) {
            this.shopUrl = shopUrl;
        }

        public String getHeadeimg() {
            return headeimg;
        }

        public void setHeadeimg(String headeimg) {
            this.headeimg = headeimg;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUseraddress() {
            return useraddress;
        }

        public void setUseraddress(String useraddress) {
            this.useraddress = useraddress;
        }

        public String getUserautograph() {
            return userautograph;
        }

        public void setUserautograph(String userautograph) {
            this.userautograph = userautograph;
        }

        public String getUserbirthday() {
            return userbirthday;
        }

        public void setUserbirthday(String userbirthday) {
            this.userbirthday = userbirthday;
        }

        public String getUsertime() {
            return usertime;
        }

        public void setUsertime(String usertime) {
            this.usertime = usertime;
        }

        public String getUsercodeok() {
            return usercodeok;
        }

        public void setUsercodeok(String usercodeok) {
            this.usercodeok = usercodeok;
        }

        public String getUsermarry() {
            return usermarry;
        }

        public void setUsermarry(String usermarry) {
            this.usermarry = usermarry;
        }

        public String getUsergrade() {
            return usergrade;
        }

        public void setUsergrade(String usergrade) {
            this.usergrade = usergrade;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }

        public String getNews() {
            return news;
        }

        public void setNews(String news) {
            this.news = news;
        }

        public String getFriendnote() {
            return Friendnote;
        }

        public void setFriendnote(String Friendnote) {
            this.Friendnote = Friendnote;
        }

        public String getFocusonnews() {
            return Focusonnews;
        }

        public void setFocusonnews(String Focusonnews) {
            this.Focusonnews = Focusonnews;
        }

        public String getActivitymessage() {
            return Activitymessage;
        }

        public void setActivitymessage(String Activitymessage) {
            this.Activitymessage = Activitymessage;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getVideo_num() {
            return video_num;
        }

        public void setVideo_num(String video_num) {
            this.video_num = video_num;
        }

        public String getFm_num() {
            return fm_num;
        }

        public void setFm_num(String fm_num) {
            this.fm_num = fm_num;
        }

        public String getGoods_num() {
            return goods_num;
        }

        public void setGoods_num(String goods_num) {
            this.goods_num = goods_num;
        }

        public String getLike_num() {
            return like_num;
        }

        public void setLike_num(String like_num) {
            this.like_num = like_num;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }
    }
}
