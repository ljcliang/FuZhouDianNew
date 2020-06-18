package com.yiwo.fuzhoudian.pages.renzheng;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.custom.WeiboDialogUtils;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.utils.AndTools;
import com.yiwo.fuzhoudian.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

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
//    @BindView(R.id.rl_up_dianmianzhaopian)
//    RelativeLayout rlUpDianmianzhaopian;
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
    @BindView(R.id.edt_person_num)
    EditText edtPersonNum;
    @BindView(R.id.edt_person_name)
    EditText edtPersonName;
    @BindView(R.id.edt_shop_email)
    EditText edtShopEmail;

    private String yingyezhizhao = "";
//    private String dianmianzhaopian = "";
    private String shenfenzheng_renxiang = "";
    private String shenfenzheng_guohui = "";

    private static final int REQUEST_CODE_YINGYEZHIZHAO = 0x00000011;
    private static final int REQUEST_CODE_DIANMIANPIC = 0x00000012;
    private static final int REQUEST_CODE_SHENFENZHNEG_RENXIANG = 0x00000013;
    private static final int REQUEST_CODE_SHENFENZHNEG_GUOHUI = 0x00000014;

    private Dialog dialog;
    private SpImp spImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zheng1__edit_info);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
//        Glide.with(this).load("http://192.168.7.103/2weima.php").into(ivYingyezhizhao)
//        AndTools.saveImageUrlToGallery(this,"http://192.168.7.103/2weima.php");

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
//            case R.id.rl_up_dianmianzhaopian:
//                ImageSelector.builder()
//                        .useCamera(true) // 设置是否使用拍照
//                        .setSingle(true)  //设置是否单选
//                        .start(this, REQUEST_CODE_DIANMIANPIC); // 打开相册
//                break;
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
        if (TextUtils.isEmpty(yingyezhizhao)) {
            toToast(this, "请上传营业执照");
            return;
        }
//        if (TextUtils.isEmpty(dianmianzhaopian)) {
//            toToast(this, "请上传店面照片");
//            return;
//        }
        if (TextUtils.isEmpty(shenfenzheng_renxiang)) {
            toToast(this, "请上传身份证正面");
            return;
        }
        if (TextUtils.isEmpty(shenfenzheng_guohui)) {
            toToast(this, "请上传身份证反面");
            return;
        }
        if (TextUtils.isEmpty(edtShopName.getText().toString())) {
            toToast(this, "请输入店铺名称");
            return;
        }
        if (TextUtils.isEmpty(edtShopTel.getText().toString())) {
            toToast(this, "请输入店铺电话");
            return;
        }
        if (TextUtils.isEmpty(edtPersonName.getText().toString())) {
            toToast(this, "请输入姓名");
            return;
        }
        if (TextUtils.isEmpty(edtPersonNum.getText().toString())) {
            toToast(this, "请输入身份证号");
            return;
        }
        if (!StringUtils.isEmail(edtShopEmail.getText().toString())) {
            toToast(this, "电子邮箱格式不对");
            return;
        }
        if (TextUtils.isEmpty(edtPersonNum.getText().toString())) {
            toToast(this, "请输入店铺简称");
            return;
        }
        upDataInfo();
    }

    private void upDataInfo() {
        Observable<List<File>> observable = Observable.create(new ObservableOnSubscribe<List<File>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<File>> e) throws Exception {
                dialog = WeiboDialogUtils.createLoadingDialog(RenZheng1_EditInfoActivity.this, "请等待...");
                List<String> list = new ArrayList<>();
                list.add(yingyezhizhao);
//                list.add(dianmianzhaopian);
                list.add(shenfenzheng_renxiang);
                list.add(shenfenzheng_guohui);
                final List<File> files = new ArrayList<>();
                Luban.with(RenZheng1_EditInfoActivity.this)
                        .load(list)
                        .ignoreBy(100)
                        .filter(new CompressionPredicate() {
                            @Override
                            public boolean apply(String path) {
                                return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                            }
                        })
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                // TODO 压缩开始前调用，可以在方法内启动 loading UI
                            }

                            @Override
                            public void onSuccess(File file) {
                                // TODO 压缩成功后调用，返回压缩后的图片文件
                                files.add(file);
                                Log.e("22222::", files.size() + "///"+file.getPath());
                                if (files.size() == 3) {
                                    e.onNext(files);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO 当压缩过程出现问题时调用
                                toToast(RenZheng1_EditInfoActivity.this, "压缩图片失败，请重试");
                            }
                        }).launch();
            }
        });
        Observer<List<File>> observer = new Observer<List<File>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<File> value) {
                ViseHttp.UPLOAD(NetConfig.userVerify)
                        .addHeader("Content-Type", "multipart/form-data")
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.userVerify))
                        .addParam("uid", spImp.getUID())
                        .addParam("store_name", edtShopName.getText().toString())
                        .addParam("tel", edtShopTel.getText().toString())
                        .addParam("merchant_shortname", edtShopName.getText().toString())
                        .addParam("email", edtShopEmail.getText().toString())
                        .addParam("id_card_name", edtPersonName.getText().toString())
                        .addParam("id_card_number", edtPersonNum.getText().toString())
                        .addFile("businessLicense", value.get(0))
//                        .addFile("shopImg", value.get(1))
                        .addFile("idCardCopy", value.get(1))
                        .addFile("idCardNational", value.get(2))
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("22222", data + "::::");
                                Log.e("22222::", data + "");
                                try {
                                    JSONObject jsonObject = new JSONObject();
                                    if (!TextUtils.isEmpty(data)) {
                                        jsonObject = new JSONObject(data);
                                    }
                                    if (jsonObject.getInt("code") == 200) {
                                        toToast(RenZheng1_EditInfoActivity.this, "已提交审核");
                                        RenZheng0_BeginActivity.openActivity(RenZheng1_EditInfoActivity.this);
                                    }
                                    if (jsonObject.getInt("code") == 400) {
                                        toToast(RenZheng1_EditInfoActivity.this, jsonObject.getString("message"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                WeiboDialogUtils.closeDialog(dialog);
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                Log.e("22222", errMsg);
                                WeiboDialogUtils.closeDialog(dialog);
                                toToast(RenZheng1_EditInfoActivity.this, "提交失败：" + errMsg);
                            }
                        });
            }

            @Override
            public void onError(Throwable e) {
                WeiboDialogUtils.closeDialog(dialog);
                toToast(RenZheng1_EditInfoActivity.this, "提交失败：" + e);
            }

            @Override
            public void onComplete() {

            }
        };
        observable.observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
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
//        if (requestCode == REQUEST_CODE_DIANMIANPIC && data != null) {
//            //获取选择器返回的数据
//            List<String> scList = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
//            Glide.with(this).load("file://" + scList.get(0)).into(ivDianmianzhaopian);
//            tvDianmianpic.setVisibility(View.GONE);
//            dianmianzhaopian = scList.get(0);
//        }
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
