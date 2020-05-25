package com.yiwo.fuzhoudian.model;

import java.io.Serializable;
import java.util.List;

public class ShangPinServiceModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"id":"1","name":"七天包退","info":"未满七天就给退货"},{"id":"2","name":"30天包换","info":"一个月内出现问题就换新的"},{"id":"3","name":"永久维修","info":"终身维修"}]
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
         * id : 1
         * name : 七天包退
         * info : 未满七天就给退货
         */

        private String id;
        private String name;
        private String info;
        private boolean isChecked = false;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }
}
