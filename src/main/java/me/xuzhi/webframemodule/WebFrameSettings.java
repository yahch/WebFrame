package me.xuzhi.webframemodule;

import java.util.HashMap;

/**
 * Created by xuzhi on 2017/9/4.
 */

public enum WebFrameSettings {
    instance;

    private String url;
    private HashMap<String, WebFrameScriptInterface> objs;
    private boolean noActionBar;

    public boolean isNoActionBar() {
        return noActionBar;
    }

    public void setNoActionBar(boolean noActionBar) {
        this.noActionBar = noActionBar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, WebFrameScriptInterface> getObjs() {
        return objs;
    }

    public void addObject(String key, WebFrameScriptInterface obj) {
        objs.put(key, obj);
    }

    public void clearObjects() {
        objs.clear();
    }

    public void removeObject(String key) {
        objs.remove(key);
    }

    public void reset() {
        url = "";
        if (objs == null) {
            objs = new HashMap<>();
        } else {
            objs.clear();
        }
        noActionBar = false;
    }

    WebFrameSettings() {
        reset();
    }

}
