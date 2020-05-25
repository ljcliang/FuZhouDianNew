package com.yiwo.fuzhoudian.pages;

import android.app.Dialog;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.custom.WeiboDialogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserAgreementActivity extends AppCompatActivity {
    @BindView(R.id.activity_user_agreement_webview)
    WebView webView;
    @BindView(R.id.ctivity_user_agreement_tv_title)
    TextView titleTv;
    private Dialog dialogLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);
        ButterKnife.bind(this);

        initWebView();
    }

    public void initWebView(){
        titleTv.setText(getIntent().getStringExtra("title"));
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                WeiboDialogUtils.closeDialog(dialogLoading);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        }
        webView.loadUrl(getIntent().getStringExtra("url"));
        dialogLoading = WeiboDialogUtils.createLoadingDialog(UserAgreementActivity.this,"加载中...");
    }

    @OnClick({R.id.activity_user_agreement_rl_back})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.activity_user_agreement_rl_back:
                finish();
                break;
        }
    }
}
