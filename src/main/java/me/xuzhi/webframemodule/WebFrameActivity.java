package me.xuzhi.webframemodule;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WebFrameActivity extends AppCompatActivity {

    // 此部分由设计器生成，请勿手动修改

    private Toolbar toolbarWebFrameModule;
    private ProgressBar progressBarWebFrameModule;
    private WebView webViewWebFrameModule;

    private void initUIViews() {

        toolbarWebFrameModule = (Toolbar) findViewById(R.id.toolbarWebFrameModule);
        progressBarWebFrameModule = (ProgressBar) findViewById(R.id.progressBarWebFrameModule);
        webViewWebFrameModule = (WebView) findViewById(R.id.webViewWebFrameModule);

    }

    // 以上代码由设计器生成，请勿手动修改


    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setWindowStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.webFrameToolbarBackgroundColor));
        setContentView(R.layout.activity_web_frame);

        initUIViews();

        if (WebFrameSettings.instance.getUrl().length() < 4) {
            Toast.makeText(getApplicationContext(), "url不能为空.", Toast.LENGTH_SHORT).show();
            return;
        }

        toolbarWebFrameModule.setTitle(WebFrameSettings.instance.getUrl());
        toolbarWebFrameModule.setNavigationIcon(R.drawable.ic_action_arrow_back);

        setSupportActionBar(toolbarWebFrameModule);

        if (WebFrameSettings.instance.isNoActionBar()) {
            getSupportActionBar().hide();
        }

        toolbarWebFrameModule.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webViewWebFrameModule.canGoBack()) {
                    webViewWebFrameModule.goBack();
                } else finish();
            }
        });

        WebSettings webSettings = webViewWebFrameModule.getSettings();
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

        HashMap<String, WebFrameScriptInterface> invokeObjects = WebFrameSettings.instance.getObjs();
        if (invokeObjects.size() > 0) {
            for (Map.Entry<String, WebFrameScriptInterface> entry : invokeObjects.entrySet()) {
                WebFrameScriptInterface ws = entry.getValue();
                ws.setWebFrameActivity(WebFrameActivity.this);
                webViewWebFrameModule.addJavascriptInterface(ws, entry.getKey());
            }
        }

        webViewWebFrameModule.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                toolbarWebFrameModule.setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBarWebFrameModule.setProgress(newProgress);
            }
        });

        webViewWebFrameModule.setWebViewClient(new WebViewClient() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //return super.shouldOverrideUrlLoading(view, request);
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });

        webViewWebFrameModule.loadUrl(WebFrameSettings.instance.getUrl());

        looper = new Looper(looperCallback);
        looper.start();
    }

    private Looper looper = null;

    private Looper.LooperCallback looperCallback = new Looper.LooperCallback() {
        @Override
        public void onTicked(int seconds) {
            if (seconds > 3 && progressBarWebFrameModule.getProgress() > 99) {
                progressBarWebFrameModule.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_webframe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_close) {
            finish();
        } else if (id == R.id.action_copylink) {
            doCopyLink();
        } else if (id == R.id.action_openWithBrowser) {
            doOpenWithBrowser();

        } else if (id == R.id.action_refresh) {
            doRefresh();

        } else if (id == R.id.action_share) {
            doShare();
        }
        return true;
    }

    private void doShare() {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, webViewWebFrameModule.getTitle() + " - " +
                "" + WebFrameSettings.instance.getUrl());
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    private void doRefresh() {
        looper.reset();
        progressBarWebFrameModule.setVisibility(View.VISIBLE);
        webViewWebFrameModule.reload();
    }

    private void doOpenWithBrowser() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(WebFrameSettings.instance.getUrl());
        intent.setData(content_url);
        startActivity(intent);
    }

    private void doCopyLink() {
        try {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("link", WebFrameSettings.instance.getUrl());
            cm.setPrimaryClip(clipData);
            Toast.makeText(getApplicationContext(), "复制链接成功", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "复制链接失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setWindowStatusBarColor(int color) {
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
}
