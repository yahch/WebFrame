package me.xuzhi.webframemodule;

import java.io.Serializable;

/**
 * Created by xuzhi on 2017/11/2.
 */

public class ScriptObject implements Serializable {
    private String name;
    private String fullClassName;

    public ScriptObject() {
    }

    public ScriptObject(String name, String fullClassName) {
        this.name = name;
        this.fullClassName = fullClassName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullClassName() {
        return fullClassName;
    }

    public void setFullClassName(String fullClassName) {
        this.fullClassName = fullClassName;
    }
}
