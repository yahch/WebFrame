package me.xuzhi.webframemodule;

/**
 * Created by xuzhi on 2017/9/27.
 */

import java.io.Serializable;

/**
 * JS 和 JAVA 互相调用的接口抽象类
 */
public abstract class WebFrameScriptInterface {

    private WebFrameActivityBase webFrameActivityBase;

    public WebFrameActivityBase getWebFrameActivity() {
        return webFrameActivityBase;
    }

    public void setWebFrameActivity(WebFrameActivityBase webFrameActivity) {
        this.webFrameActivityBase = webFrameActivity;
    }
}
