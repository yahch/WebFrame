package me.xuzhi.webframemodule;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import static me.xuzhi.webframemodule.R.id.toolbarWebFrameModule;

public class WebFrameActivitySimple extends WebFrameActivityBase {


// 此部分代码由生成器自动生成

    private ImageView imgWebFrameModuleSimple;
    private TextView tvTitleWebFrameModuleSimple;
    private ProgressBar pbWebFrameModuleSimple;
    private WebView webViewWebFrameModuleSimple;

    private void initUIViews() {

        imgWebFrameModuleSimple = (ImageView) findViewById(R.id.imgWebFrameModuleSimple);
        tvTitleWebFrameModuleSimple = (TextView) findViewById(R.id.tvTitleWebFrameModuleSimple);
        pbWebFrameModuleSimple = (ProgressBar) findViewById(R.id.pbWebFrameModuleSimple);
        webViewWebFrameModuleSimple = (WebView) findViewById(R.id.webViewWebFrameModuleSimple);

    }

// 以上代码由设计器生成，请勿手动修改


    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
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
            Log.d(getClass().getName(), "shouldOverrideUrlLoading: " + url);
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            pbWebFrameModuleSimple.setVisibility(View.GONE);
            webViewWebFrameModuleSimple.setVisibility(View.VISIBLE);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindowStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.webFrameToolbarBackgroundColor));
        try {
            getSupportActionBar().hide();
        } catch (Exception ex) {
        }
        setContentView(R.layout.activity_web_frame_simple);

        initUIViews();

        frameSettings = (WebFrameSettings) getIntent().getSerializableExtra("settings");
        if (frameSettings == null) {
            return;
        }

        webViewWebFrameModuleSimple.setVisibility(View.INVISIBLE);

        tvTitleWebFrameModuleSimple.setText(frameSettings.getTitle());

        imgWebFrameModuleSimple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        WebSettings webSettings = webViewWebFrameModuleSimple.getSettings();
        initWebViewSettings(webSettings);
        initWebViewObjects(webViewWebFrameModuleSimple, frameSettings.getScriptObject());

        webViewWebFrameModuleSimple.setWebChromeClient(webChromeClient);
        webViewWebFrameModuleSimple.setWebViewClient(webViewClient);
        Log.d(getClass().getName(), "onCreate: loadUrl->" + frameSettings.getUrl());
        webViewWebFrameModuleSimple.loadUrl(frameSettings.getUrl());
    }

    @Override
    public void redirect(String title, String url) {
        tvTitleWebFrameModuleSimple.setText(title);
        webViewWebFrameModuleSimple.loadUrl(url);
    }
}
