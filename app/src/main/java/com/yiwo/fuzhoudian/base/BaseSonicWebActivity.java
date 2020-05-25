package com.yiwo.fuzhoudian.base;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tencent.sonic.sdk.SonicConfig;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.vas_sonic.SonicJavaScriptInterface;
import com.yiwo.fuzhoudian.vas_sonic.TBSonicRuntime;
import com.yiwo.fuzhoudian.vas_sonic.TBSonicSessionClient;

import java.util.HashMap;

public class BaseSonicWebActivity extends BaseActivity {

    private Dialog dialogLoading;
    private SonicSession sonicSession;
    private String url;
    private Intent intent;
    private WebView webView;
    private SpImp spImp;
    public static final String URL = "url";
    public static final String WEB_VIEW_ID = "wedViewId";
    private TBSonicSessionClient sonicSessionClient = null;
    private SonicJavaScriptInterface sonicJavaScriptInterface = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = getIntent();
        spImp = new SpImp(BaseSonicWebActivity.this);
    }
    public void initIntentSonic(String url,WebView webView){
        this.webView = webView;
        Intent intent = new Intent();
        intent.putExtra(URL, url);
        intent.putExtra(SonicJavaScriptInterface.PARAM_CLICK_TIME, System.currentTimeMillis());
        this.intent = intent;
        url = intent.getStringExtra(URL);
        if (!SonicEngine.isGetInstanceAllowed()) {
            SonicEngine.createInstance(new TBSonicRuntime(getApplication()), new SonicConfig.Builder().build());
            Log.d("SonicEngine.create","webPage_bbb");
        }
        SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
        sessionConfigBuilder.setSupportLocalServer(true);
        HashMap mapRp = new HashMap();
        String str_vlue = "http://www.91yiwo.com/ylyy/include/activity_web/js/jquery-3.3.1.min.js;"
                +"http://www.91yiwo.com/ylyy/include/activity_web/css/web_main.css;"
                +"http://www.91yiwo.com/ylyy/include/activity_web/js/builder.js;";
        mapRp.put("sonic-link",str_vlue);
        sessionConfigBuilder.setCustomResponseHeaders(mapRp);
        sonicSession = SonicEngine.getInstance().createSession(url, sessionConfigBuilder.build());
        if (null != sonicSession) {
            sonicSessionClient = new TBSonicSessionClient();
            if (sonicSession.bindClient(sonicSessionClient)){
            }
        } else {
        }
        // init webview
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (sonicSession != null) {
                    sonicSession.getSessionClient().pageFinish(url);
                }
            }

            @TargetApi(21)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return shouldInterceptRequest(view, request.getUrl().toString());
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (sonicSession != null) {
                    return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
                }
                return null;
            }
        });

        WebSettings webSettings = webView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis());
        sonicJavaScriptInterface = new SonicJavaScriptInterface(sonicSessionClient, intent);
        webView.addJavascriptInterface(sonicJavaScriptInterface, "sonic");

        // init webview settings
        webSettings.setAllowContentAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);


        // webview is ready now, just tell session client to bind
        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(webView);
            sonicSessionClient.clientReady();
        } else { // default mode
            webView.loadUrl(url);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sonicJavaScriptInterface = null;
        if (null != sonicSession) {
            sonicSession.destroy();
            sonicSession = null;
        }
    }
}
