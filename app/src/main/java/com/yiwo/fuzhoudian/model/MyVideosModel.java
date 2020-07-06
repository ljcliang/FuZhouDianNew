package com.yiwo.fuzhoudian.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ljc on 2019/7/5.
 */

public class MyVideosModel {


    /**
     * code : 200
     * message : 操作成功!
     * obj : [{"vID":"25","vname":"5454545","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/596c0134-b316-411a-b991-08d2669a5898.mp4","vtime":"2020-07-01 11:18","img":"http://vodsmnjjkoj.nosdn.127.net/596c0134-b316-411a-b991-08d2669a5898_1_0_0.jpg","address":"花生仓买店","pfID":"65","gid":"65","gname":"小苹果饮料","praise_num":"0","comment_num":"0"},{"vID":"12","vname":"ooo","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/32050314-986a-4d75-9cb6-f455f61dbe9d.mp4","vtime":"2020-07-01 09:21","img":"http://vodsmnjjkoj.nosdn.127.net/32050314-986a-4d75-9cb6-f455f61dbe9d_1_0_0.jpg","address":"花生仓买店","pfID":"0","gid":"0","gname":"","praise_num":"0","comment_num":"0"},{"vID":"11","vname":"1231","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/fa29162a-801c-45fb-b53a-d79683eee3e4.mp4","vtime":"2020-07-01 08:48","img":"http://vodsmnjjkoj.nosdn.127.net/fa29162a-801c-45fb-b53a-d79683eee3e4_1_0_0.jpg","address":"花生仓买店","pfID":"0","gid":"0","gname":"","praise_num":"0","comment_num":"0"},{"vID":"6","vname":"Lll","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/7c0b4250-0e72-4c3e-b624-85d3340b51a3.mp4","vtime":"2020-06-24 16:41","img":"http://vodsmnjjkoj.nosdn.127.net/e3b2c6a8-deb4-4916-96f2-a02e536ef762.jpg","address":"北京","pfID":"0","gid":"0","gname":"","praise_num":"0","comment_num":"9"},{"vID":"5","vname":"ooo","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/759ce663-66d2-4169-bb15-67f5e2efe932.mp4","vtime":"2020-06-24 09:28","img":"http://vodsmnjjkoj.nosdn.127.net/759ce663-66d2-4169-bb15-67f5e2efe932_1_0_0.jpg","address":"阿拉善盟","pfID":"0","gid":"0","gname":"","praise_num":"0","comment_num":"0"},{"vID":"1","vname":"呃呃呃呃呃呃呃呃","vurl":"http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/7a0fb147-abb0-4eb9-96ab-6e7886424274.mp4","vtime":"2020-05-14 10:21","img":"http://vodsmnjjkoj.nosdn.127.net/ae542fa1-e1ca-445d-a78b-f5d6c0173185.jpg","address":"哈尔滨","pfID":"0","gid":"0","gname":"","praise_num":"0","comment_num":"9"}]
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

    public static class ObjBean implements Serializable {
        /**
         * vID : 25
         * vname : 5454545
         * vurl : http://vodsmnjjkoj.vod.126.net/vodsmnjjkoj/596c0134-b316-411a-b991-08d2669a5898.mp4
         * vtime : 2020-07-01 11:18
         * img : http://vodsmnjjkoj.nosdn.127.net/596c0134-b316-411a-b991-08d2669a5898_1_0_0.jpg
         * address : 花生仓买店
         * pfID : 65
         * gid : 65
         * gname : 小苹果饮料
         * praise_num : 0
         * comment_num : 0
         */

        private String vID;
        private String vname;
        private String vurl;
        private String vtime;
        private String img;
        private String address;
        private String pfID;
        private String gid;
        private String gname;
        private String praise_num;
        private String comment_num;

        public String getVID() {
            return vID;
        }

        public void setVID(String vID) {
            this.vID = vID;
        }

        public String getVname() {
            return vname;
        }

        public void setVname(String vname) {
            this.vname = vname;
        }

        public String getVurl() {
            return vurl;
        }

        public void setVurl(String vurl) {
            this.vurl = vurl;
        }

        public String getVtime() {
            return vtime;
        }

        public void setVtime(String vtime) {
            this.vtime = vtime;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPfID() {
            return pfID;
        }

        public void setPfID(String pfID) {
            this.pfID = pfID;
        }

        public String getGid() {
            return gid;
        }

        public void setGid(String gid) {
            this.gid = gid;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public String getPraise_num() {
            return praise_num;
        }

        public void setPraise_num(String praise_num) {
            this.praise_num = praise_num;
        }

        public String getComment_num() {
            return comment_num;
        }

        public void setComment_num(String comment_num) {
            this.comment_num = comment_num;
        }
    }
}
