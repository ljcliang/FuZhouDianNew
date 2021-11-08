package com.yiwo.fuzhoudian.model;

import java.util.List;

public class LaoWuZhieYeModel {

    /**
     * code : 200
     * message : 获取成功!
     * obj : [{"id":"1","jobname":"油工"},{"id":"2","jobname":"木工"},{"id":"3","jobname":"电工"},{"id":"4","jobname":"水暖工"},{"id":"5","jobname":"泥瓦工"},{"id":"6","jobname":"清洁工"}]
     */

    private String code;
    private String message;
    private List<ObjBean> obj;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
         * jobname : 油工
         */

        private String id;
        private String jobname;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJobname() {
            return jobname;
        }

        public void setJobname(String jobname) {
            this.jobname = jobname;
        }
    }
}
