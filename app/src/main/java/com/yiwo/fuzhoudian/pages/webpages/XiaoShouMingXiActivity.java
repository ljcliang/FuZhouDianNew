package com.yiwo.fuzhoudian.pages.webpages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseSonicWebActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XiaoShouMingXiActivity extends BaseSonicWebActivity {

    @BindView(R.id.rl_return)
    RelativeLayout mRlReturn;
    @BindView(R.id.titleTv)
    TextView mTitleTv;
    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiao_shou_ming_xi);
        ButterKnife.bind(this);
        initIntentSonic(getIntent().getStringExtra("url"), mWebView);
    }

    public static void start(Context context, String url) {
        Intent intent = new Intent();
        intent.setClass(context, XiaoShouMingXiActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @OnClick(R.id.rl_return)
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rl_return:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();//返回上个页面
            return;
        } else {
            finish();
        }
    }
}
