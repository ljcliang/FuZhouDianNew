package com.yiwo.fuzhoudian.model;

/**
 * Created by Administrator on 2018/7/24.
 */

public class UserModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : {"headeimg":"http://47.92.136.19/uploads/header/2018/10/19/3ccfe6790eeac1bc99dbfcd164dabb89153992760420.jpg","username":"老司机","sex":"0","useraddress":"黑龙江省-哈尔滨市","userautograph":"恶魔","userbirthday":"1993-09-25","usertime":"2018-08-01","usercodeok":"已认证","usermarry":"1","usergrade":"1","sign":"0","vip":"1","news":15,"Friendnote":1,"Focusonnews":9,"Activitymessage":1}
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
         * headeimg : http://47.92.136.19/uploads/header/2018/10/19/3ccfe6790eeac1bc99dbfcd164dabb89153992760420.jpg
         * username : 老司机
         * sex : 0
         * useraddress : 黑龙江省-哈尔滨市
         * userautograph : 恶魔
         * userbirthday : 1993-09-25
         * usertime : 2018-08-01
         * usercodeok : 已认证
         * usermarry : 1
         * usergrade : 1
         * sign : 0   0 不是领队 1 是领队
         * vip : 1
         * news : 15
         * Friendnote : 1
         * Focusonnews : 9
         * Activitymessage : 1
         * type  0未开启  1已开启
         * video_num  视频数   fm_num文章数   goods_num商品数    like_num关注数
         * tel  电话
         */
        private String video_num;
        private String fm_num;
        private String goods_num;
        private String like_num;
        private String tel;
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
        private int news;
        private int Friendnote;
        private int Focusonnews;
        private int Activitymessage;
        private String type;
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

        public int getNews() {
            return news;
        }

        public void setNews(int news) {
            this.news = news;
        }

        public int getFriendnote() {
            return Friendnote;
        }

        public void setFriendnote(int Friendnote) {
            this.Friendnote = Friendnote;
        }

        public int getFocusonnews() {
            return Focusonnews;
        }

        public void setFocusonnews(int Focusonnews) {
            this.Focusonnews = Focusonnews;
        }

        public int getActivitymessage() {
            return Activitymessage;
        }

        public void setActivitymessage(int Activitymessage) {
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
