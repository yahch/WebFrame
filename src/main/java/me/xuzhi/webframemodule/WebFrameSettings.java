package me.xuzhi.webframemodule;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by xuzhi on 2017/9/4.
 */

public class WebFrameSettings implements Serializable {

    private String url;
    private String title;
    private ScriptObject scriptObject;
    private boolean noActionBar;
    private boolean backEnable;


    public WebFrameSettings() {
        url = "";
        scriptObject = null;
        noActionBar = true;
        title = "";
        backEnable = false;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ScriptObject getScriptObject() {
        return scriptObject;
    }

    public void setScriptObject(ScriptObject scriptObject) {
        this.scriptObject = scriptObject;
    }

    public boolean isNoActionBar() {
        return noActionBar;
    }

    public void setNoActionBar(boolean noActionBar) {
        this.noActionBar = noActionBar;
    }

    public boolean isBackEnable() {
        return backEnable;
    }

    public void setBackEnable(boolean backEnable) {
        this.backEnable = backEnable;
    }
}
