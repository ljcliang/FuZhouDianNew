package com.yiwo.fuzhoudian.tongban_emoticon;

public class EmoticonModel {
    private int id;
    private String name;//[傲慢]
    private int resources; //R.drawable.em_35

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResources() {
        return resources;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }
}
