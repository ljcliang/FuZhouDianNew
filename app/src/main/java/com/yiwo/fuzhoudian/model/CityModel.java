package com.yiwo.fuzhoudian.model;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/7/15.
 */

public class CityModel implements Serializable {
    private String id;
    private String name;

    public String getId() {
        Log.d("test", "getId: ");
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

    @Override
    public String toString() {
        return "CityBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
