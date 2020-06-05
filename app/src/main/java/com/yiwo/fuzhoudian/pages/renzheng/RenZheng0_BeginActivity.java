package com.yiwo.fuzhoudian.pages.renzheng;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RenZheng0_BeginActivity extends BaseActivity {

    @BindView(R.id.tv_next)
    TextView tvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zheng0__begin);
        ButterKnife.bind(this);
    }

    public static void openActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RenZheng0_BeginActivity.class);
        context.startActivity(intent);
    }

    @OnClick(R.id.tv_next)
    public void onViewClicked() {
        RenZheng1_EditInfoActivity.openActivity(this);
    }
}
