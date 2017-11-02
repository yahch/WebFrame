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

public class WebFrameActivity extends WebFrameActivityBase {

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

    private WebChromeClient webChromeClient = new WebChromeClient() {
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
    };

    private WebViewClient webViewClient = new WebViewClient() {

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
    };

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setWindowStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.webFrameToolbarBackgroundColor));
        setContentView(R.layout.activity_web_frame);

        initUIViews();

        frameSettings = (WebFrameSettings) getIntent().getSerializableExtra("settings");
        if (frameSettings == null) {
            return;
        }

        if (frameSettings.getUrl().length() < 4) {
            Toast.makeText(getApplicationContext(), "url不能为空.", Toast.LENGTH_SHORT).show();
            return;
        }

        toolbarWebFrameModule.setTitle(frameSettings.getUrl());
        toolbarWebFrameModule.setNavigationIcon(R.drawable.ic_action_arrow_back);

        setSupportActionBar(toolbarWebFrameModule);

        if (frameSettings.isNoActionBar()) {
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
        initWebViewSettings(webSettings);
        initWebViewObjects(webViewWebFrameModule, frameSettings.getScriptObject());


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
                Log.d(getClass().getName(), "shouldOverrideUrlLoading: " + url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });

        Log.d(getClass().getName(), "onCreate: loadUrl->" + frameSettings.getUrl());
        webViewWebFrameModule.loadUrl(frameSettings.getUrl());

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
                "" + frameSettings.getUrl());
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
        Uri content_url = Uri.parse(frameSettings.getUrl());
        intent.setData(content_url);
        startActivity(intent);
    }

    private void doCopyLink() {
        try {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("link", frameSettings.getUrl());
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


    @Override
    protected void redirect(String title, String url) {
        try {
            toolbarWebFrameModule.setTitle(title);
            webViewWebFrameModule.loadUrl(url);
        } catch (Exception __) {

        }
    }
}
