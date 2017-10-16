package me.xuzhi.webframemodule;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuzhi on 2017/10/16.
 */

public abstract class WebFrameActivityBase extends AppCompatActivity {

    protected void setWindowStatusBarColor(int color) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void initWebViewSettings(WebSettings webSettings) {
        webSettings.setUserAgentString("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3159.5 Mobile Safari/537.36");
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setSupportZoom(false);
    }

    protected void initWebViewObjects(WebView webView) {
        HashMap<String, WebFrameScriptInterface> invokeObjects = WebFrameSettings.instance.getObjs();
        if (invokeObjects.size() > 0) {
            for (Map.Entry<String, WebFrameScriptInterface> entry : invokeObjects.entrySet()) {
                WebFrameScriptInterface ws = entry.getValue();
                ws.setWebFrameActivity(this);
                webView.addJavascriptInterface(ws, entry.getKey());
            }
        }
    }

}
