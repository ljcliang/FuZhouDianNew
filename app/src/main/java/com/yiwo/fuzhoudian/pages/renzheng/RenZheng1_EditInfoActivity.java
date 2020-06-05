package com.yiwo.fuzhoudian.pages.renzheng;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RenZheng1_EditInfoActivity extends BaseActivity {

    @BindView(R.id.edt_shop_name)
    EditText edtShopName;
    @BindView(R.id.edt_shop_tel)
    EditText edtShopTel;
    @BindView(R.id.iv_yingyezhizhao)
    ImageView ivYingyezhizhao;
    @BindView(R.id.rl_up_yingyezhizhao)
    RelativeLayout rlUpYingyezhizhao;
    @BindView(R.id.iv_dianmianzhaopian)
    ImageView ivDianmianzhaopian;
    @BindView(R.id.rl_up_dianmianzhaopian)
    RelativeLayout rlUpDianmianzhaopian;
    @BindView(R.id.iv_shenfenzheng_head)
    ImageView ivShenfenzhengHead;
    @BindView(R.id.rl_up_shenfenzheng_renxiang)
    RelativeLayout rlUpShenfenzhengRenxiang;
    @BindView(R.id.iv_shenfenzheng_back)
    ImageView ivShenfenzhengBack;
    @BindView(R.id.rl_up_shenfenzheng_guohui)
    RelativeLayout rlUpShenfenzhengGuohui;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_yingyezhizhao)
    TextView tvYingyezhizhao;
    @BindView(R.id.tv_dianmianpic)
    TextView tvDianmianpic;
    @BindView(R.id.tv_shenfenzheng_renxiang)
    TextView tvShenfenzhengRenxiang;
    @BindView(R.id.tv_shenfenzheng_guohui)
    TextView tvShenfenzhengGuohui;

    private String yingyezhizhao = "";
    private String dianmianzhaopian = "";
    private String shenfenzheng_renxiang = "";
    private String shenfenzheng_guohui = "";

    private static final int REQUEST_CODE_YINGYEZHIZHAO = 0x00000011;
    private static final int REQUEST_CODE_DIANMIANPIC = 0x00000012;
    private static final int REQUEST_CODE_SHENFENZHNEG_RENXIANG = 0x00000013;
    private static final int REQUEST_CODE_SHENFENZHNEG_GUOHUI = 0x00000014;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zheng1__edit_info);
        ButterKnife.bind(this);
    }

    public static void openActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, RenZheng1_EditInfoActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.rl_up_yingyezhizhao, R.id.rl_up_dianmianzhaopian, R.id.rl_up_shenfenzheng_renxiang, R.id.rl_up_shenfenzheng_guohui, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_up_yingyezhizhao:
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(true)  //设置是否单选
                        .start(this, REQUEST_CODE_YINGYEZHIZHAO); // 打开相册
                break;
            case R.id.rl_up_dianmianzhaopian:
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(true)  //设置是否单选
                        .start(this, REQUEST_CODE_DIANMIANPIC); // 打开相册
                break;
            case R.id.rl_up_shenfenzheng_renxiang:
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(true)  //设置是否单选
                        .start(this, REQUEST_CODE_SHENFENZHNEG_RENXIANG); // 打开相册
                break;
            case R.id.rl_up_shenfenzheng_guohui:
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(true)  //设置是否单选
                        .start(this, REQUEST_CODE_SHENFENZHNEG_GUOHUI); // 打开相册
                break;
            case R.id.tv_next:
                next();
                break;
        }
    }

    private void next() {
        if (TextUtils.isEmpty(yingyezhizhao)){
            toToast(this,"请上传营业执照");
            return;
        }
        if (TextUtils.isEmpty(dianmianzhaopian)){
            toToast(this,"请上传店面照片");
            return;
        }
        if ( TextUtils.isEmpty(shenfenzheng_renxiang)){
            toToast(this,"请上传身份证正面");
            return;
        }
        if (TextUtils.isEmpty(shenfenzheng_guohui)){
            toToast(this,"请上传身份证反面");
            return;
        }
        if (TextUtils.isEmpty(edtShopName.getText().toString())){
            toToast(this,"请输入店铺名称");
            return;
        }
        if (TextUtils.isEmpty(edtShopTel.getText().toString())){
            toToast(this,"请输入店铺电话");
            return;
        }
        RenZheng2_BindWXActivity.openActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_YINGYEZHIZHAO && data != null) {
            //获取选择器返回的数据
            List<String> scList = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            Glide.with(this).load("file://" + scList.get(0)).into(ivYingyezhizhao);
            tvYingyezhizhao.setVisibility(View.GONE);
            yingyezhizhao = scList.get(0);
        }
        if (requestCode == REQUEST_CODE_DIANMIANPIC && data != null) {
            //获取选择器返回的数据
            List<String> scList = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            Glide.with(this).load("file://" + scList.get(0)).into(ivDianmianzhaopian);
            tvDianmianpic.setVisibility(View.GONE);
            dianmianzhaopian = scList.get(0);
        }
        if (requestCode == REQUEST_CODE_SHENFENZHNEG_RENXIANG && data != null) {
            //获取选择器返回的数据
            List<String> scList = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            Glide.with(this).load("file://" + scList.get(0)).into(ivShenfenzhengHead);
            tvShenfenzhengRenxiang.setVisibility(View.GONE);
            shenfenzheng_renxiang = scList.get(0);
        }
        if (requestCode == REQUEST_CODE_SHENFENZHNEG_GUOHUI && data != null) {
            //获取选择器返回的数据
            List<String> scList = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            Glide.with(this).load("file://" + scList.get(0)).into(ivShenfenzhengBack);
            tvShenfenzhengGuohui.setVisibility(View.GONE);
            shenfenzheng_guohui = scList.get(0);
        }
    }
}
