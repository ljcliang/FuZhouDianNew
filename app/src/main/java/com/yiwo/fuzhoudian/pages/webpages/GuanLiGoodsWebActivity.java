package com.yiwo.fuzhoudian.pages.webpages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseSonicWebActivity;
import com.yiwo.fuzhoudian.pages.FaBu_XiuGaiShangPinActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuanLiGoodsWebActivity extends BaseSonicWebActivity {

    @BindView(R.id.rl_return)
    RelativeLayout rlReturn;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.rl_fabu)
    RelativeLayout mRlFabu;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guan_li_goods_web);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        initIntentSonic(url, webView);
        webView.addJavascriptInterface(new AndroidInterface(), "android");
    }

    public static void start(Context context, String url) {
        Intent intent = new Intent();
        intent.setClass(context, GuanLiGoodsWebActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_return, R.id.rl_fabu})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.rl_return:
                onBackPressed();
                break;
            case R.id.rl_fabu:
                Intent intent = new Intent();
                intent.setClass(GuanLiGoodsWebActivity.this, FaBu_XiuGaiShangPinActivity.class);
                startActivity(intent);
                break;
        }
    }

    public class AndroidInterface extends Object {
        @JavascriptInterface
        public void editgood(String gid) {
            Intent intent = new Intent();
            intent.setClass(GuanLiGoodsWebActivity.this, FaBu_XiuGaiShangPinActivity.class);
            intent.putExtra(FaBu_XiuGaiShangPinActivity.GID, gid);
            startActivity(intent);
        }
    }
}
