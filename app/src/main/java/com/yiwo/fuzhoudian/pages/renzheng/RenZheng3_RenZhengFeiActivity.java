package com.yiwo.fuzhoudian.pages.renzheng;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yiwo.fuzhoudian.R;

public class RenZheng3_RenZhengFeiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zheng3__ren_zheng_fei);
    }
    public static void openActivity(Context context){
        Intent intent = new Intent();
        intent.setClass(context,RenZheng3_RenZhengFeiActivity.class);
        context.startActivity(intent);
    }
}
