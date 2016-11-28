package com.wgl.bored.main;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.wgl.bored.R;
import com.wgl.bored.util.ToastUtil;

/**
 * 合同 webView.loadUrl("file:///android_asset/chen/app.html");
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class WebViewActivity extends AppCompatActivity {
    private String url;
    private WebView webView;
    private ProgressBar mProgressBar;
    private LinearLayout mLayoutnotactivity_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
    }


    private void initView() {
        url = this.getIntent().getStringExtra("url");
        String s = url;
        webView = (WebView) findViewById(R.id.webView1);
        mProgressBar = (ProgressBar) findViewById(R.id.myProgressBar);
        mLayoutnotactivity_layout = (LinearLayout) findViewById(R.id.notactivity_layout);
        if (TextUtils.isEmpty(url)) {
            mLayoutnotactivity_layout.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
        } else {
            initWebView();
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView.getSettings().setSupportZoom(true);// 支持缩放
        webView.addJavascriptInterface(new JavaScriptInterface1(), "robot");
        webView.getSettings().setUseWideViewPort(true);// 适应屏幕
        webView.getSettings().setJavaScriptEnabled(true);
        // webView.setInitialScale(35);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        // webView.getSettings().setRenderPriority(RenderPriority.HIGH);

        // 判断网络加载状况) {
        /*
         * if (CommonUtils.isNetworkAvailable(this)) {// 判断网络加载状况
		 * webView.clearCache(true);
		 * webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // 设置
		 * // 缓存模式 } else { webView.getSettings().setCacheMode(
		 * WebSettings.LOAD_CACHE_ELSE_NETWORK); // 设置 // 缓存模式 }
		 */

        // 开启 DOM storage API 功能
        // webView.getSettings().setDomStorageEnabled(true);
        // 开启 database storage API 功能
        // webView.getSettings().setDatabaseEnabled(true);
        // String cacheDirPath = getFilesDir().getAbsolutePath()
        // + APP_CACAHE_DIRNAME;
        // String cacheDirPath =
        // getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        // 设置数据库缓存路径
        // webView.getSettings().setDatabasePath(cacheDirPath);
        // 设置 Application Caches 缓存目录
        // webView.getSettings().setAppCachePath(cacheDirPath);
        // 开启 Application Caches 功能
        // webView.getSettings().setAppCacheEnabled(true);
        // webView.clearCache(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                try {
                  /*  if(url.contains(MainConst.ACTIVITY_URL_INTERCEPT_LOGIN)){//进入登陆
                        CommonUtils.launchActivity(WebViewActivity.this, LoginActivity.class);
                        finish();
                    } */
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                //	MyToastView.showToast(errorCode+"    "+description, WebViewActivity.this);
                if (errorCode == WebViewClient.ERROR_CONNECT
                        || errorCode == WebViewClient.ERROR_TIMEOUT
                        || errorCode == WebViewClient.ERROR_HOST_LOOKUP) {
                    //	MyToastView.showToast(errorCode+"    "+description, WebViewActivity.this);
                    /**
                     * 网络加载处理替换界面
                     */
                    // mWebView.loadData("", "text/html", "utf_8");
                    // mLayoutNetError.setVisibility(View.VISIBLE); // 显示网络错误
                    // mBtnRetry.setTag(failingUrl); // 记录URL
                    // mNetworkError = true;
                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    /*
                     * if (View.INVISIBLE == mProgressBar.getVisibility()) {
					 * mProgressBar.setVisibility(View.VISIBLE); }
					 */
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {

                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url,
                                       String message, JsResult result) {

                return true;
            }

        });
        webView.loadUrl(url);
    }

    /**
     * 调用成功 h5本地调用方法
     */
    public class JavaScriptInterface1 {
        @JavascriptInterface
        public void callAndroidMethod() {
            try {
                ToastUtil.getInstance().toastInCenter(WebViewActivity.this, "操作成功");
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        @JavascriptInterface
        public void puyMethod() {

        }
    }

    public void returnmainActivity(View view) {
        finish();
    }
}
