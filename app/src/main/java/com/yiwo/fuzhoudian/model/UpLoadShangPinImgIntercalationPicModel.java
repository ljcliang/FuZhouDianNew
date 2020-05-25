package com.yiwo.fuzhoudian.model;

/**
 * Created by ljc on 2019/9/10.
 */

public class UpLoadShangPinImgIntercalationPicModel {
    private String picId = "-1";
    private String pic;
    private String describe;
    private Boolean isFirstPic = false;
    public UpLoadShangPinImgIntercalationPicModel() {
    }

    public UpLoadShangPinImgIntercalationPicModel(String pic, String describe) {
        this.pic = pic;
        this.describe = describe;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Boolean getFirstPic() {
        return isFirstPic;
    }

    public void setFirstPic(Boolean firstPic) {
        isFirstPic = firstPic;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String pidId) {
        this.picId = pidId;
    }
}
