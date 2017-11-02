package me.xuzhi.webframemodule;

import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuzhi on 2017/10/16.
 */

public abstract class WebFrameActivityBase extends AppCompatActivity {

    private static final String TAG = "WebFrameActivityBase";

    protected WebFrameSettings frameSettings;

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

    protected void initWebViewObjects(WebView webView, ScriptObject scriptObject) {
        if (scriptObject == null) return;
        WebFrameScriptInterface ws;
        try {
            ws = (WebFrameScriptInterface) Class.forName(scriptObject.getFullClassName())
                    .newInstance();
            ws.setWebFrameActivity(this);
            webView.addJavascriptInterface(ws, scriptObject.getName());
        } catch (Exception __) {
            Log.e(TAG, "initWebViewObjects: 动态创建对象失败:", __);
        }
    }

}
