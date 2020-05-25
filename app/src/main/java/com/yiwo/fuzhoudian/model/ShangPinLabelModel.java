package com.yiwo.fuzhoudian.model;

import java.util.List;

public class ShangPinLabelModel {

    /**
     * code : 200
     * message : 获取成功
     * obj : [{"id":"1","name":"食品"},{"id":"2","name":"果蔬"},{"id":"3","name":"玩具"},{"id":"4","name":"箱包"},{"id":"5","name":"鞋帽"},{"id":"6","name":"衣物"},{"id":"7","name":"数码"},{"id":"8","name":"办公"},{"id":"9","name":"软件"},{"id":"10","name":"日常"}]
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
         * id : 1
         * name : 食品
         */

        private String id;
        private String name;
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

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }
    }
}
