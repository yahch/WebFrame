package me.xuzhi.webframemodule;

/**
 * Created by xuzhi on 2017/9/27.
 */

/**
 * JS 和 JAVA 互相调用的接口抽象类
 */
public abstract class WebFrameScriptInterface {

    private WebFrameActivity webFrameActivity;

    public WebFrameActivity getWebFrameActivity() {
        return webFrameActivity;
    }

    public void setWebFrameActivity(WebFrameActivity webFrameActivity) {
        this.webFrameActivity = webFrameActivity;
    }
}
