package com.yiwo.fuzhoudian.wxapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.umeng.socialize.weixin.view.WXCallbackActivity;
import com.yiwo.fuzhoudian.R;

public class WXEntryActivity extends WXCallbackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w_x_entry);
    }
}
