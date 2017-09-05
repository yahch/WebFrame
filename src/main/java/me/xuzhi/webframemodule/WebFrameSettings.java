package me.xuzhi.webframemodule;

import java.util.HashMap;

/**
 * Created by xuzhi on 2017/9/4.
 */

public enum WebFrameSettings {
    instance;

    private String url;
    private HashMap<String, Object> objs;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HashMap<String, Object> getObjs() {
        return objs;
    }

    public void addObject(String key, Object val) {
        objs.put(key, val);
    }

    public void clearObjects() {
        objs.clear();
    }

    public void removeObject(String key) {
        objs.remove(key);
    }

    WebFrameSettings() {
        url = "";
        objs = new HashMap<>();
    }

}
