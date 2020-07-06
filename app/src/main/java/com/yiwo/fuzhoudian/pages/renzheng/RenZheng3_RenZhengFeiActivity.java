package com.yiwo.fuzhoudian.pages.renzheng;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.custom.WeiboDialogUtils;
import com.yiwo.fuzhoudian.model.OrderToPayModel;
import com.yiwo.fuzhoudian.model.RenZhengModel;
import com.yiwo.fuzhoudian.model.YanZhengYaoQingMaMode;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.network.UMConfig;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.utils.QRCodeUtil;
import com.yiwo.fuzhoudian.utils.TokenUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RenZheng3_RenZhengFeiActivity extends BaseActivity {

    @BindView(R.id.tv_text)
    TextView tvText;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.btn_yanzheng)
    TextView btnYanzheng;
    @BindView(R.id.btn_zhifu)
    TextView btnZhifu;
    @BindView(R.id.ll_edt)
    LinearLayout llEdt;
    private SpImp spImp;
    private Dialog dialog;
    private IWXAPI api;
    private String sBindStaus = ""; // 0 未上传  1审核中  2待授权  3已授权  4失败
    private String sRenZhengFeiStaus = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ren_zheng3__ren_zheng_fei);
        ButterKnife.bind(this);
        spImp = new SpImp(this);
        api = WXAPIFactory.createWXAPI(RenZheng3_RenZhengFeiActivity.this, null);
        initInfo();
    }

    private void initInfo() {
        ViseHttp.POST(NetConfig.inviteInfo)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.inviteInfo))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject pay = new JSONObject(data);
                            if (pay.getInt("code") == 200) {
                                JSONObject jsonObject = pay.getJSONObject("obj");
                                tvPrice.setText("¥"+jsonObject.getString("price"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
        ViseHttp.POST(NetConfig.verifyStatus)
                .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.verifyStatus))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject0 = new JSONObject(data);
                            if (jsonObject0.getInt("code") == 200) {
                                JSONObject jsonObject1 = jsonObject0.getJSONObject("obj");
                                if (jsonObject1.getString("verifyCodeStatus").equals("1")){
                                    llEdt.setVisibility(View.VISIBLE);
                                }else {
                                    llEdt.setVisibility(View.GONE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
    }

    public static void openActivity(Activity context) {
        Intent intent = new Intent();
        intent.setClass(context, RenZheng3_RenZhengFeiActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkJiao();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void yanchiCheck(){
        dialog = WeiboDialogUtils.createLoadingDialog(RenZheng3_RenZhengFeiActivity.this,"处理中...");
        ViseHttp.POST(NetConfig.wxQuery)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.wxQuery))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                RenZhengModel model = gson.fromJson(data, RenZhengModel.class);
                                sBindStaus = model.getObj().getSign();
                                sRenZhengFeiStaus = model.getObj().getPay();
//                                if (sRenZhengFeiStaus.equals("1")){
                                    if (false){
                                    toToast(RenZheng3_RenZhengFeiActivity.this,"缴纳成功");
                                    WeiboDialogUtils.closeDialog(dialog);
                                    onBackPressed();
                                }else {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(10000);//延时1s
                                                ViseHttp.POST(NetConfig.wxQuery)
                                                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.wxQuery))
                                                        .addParam("uid", spImp.getUID())
                                                        .request(new ACallback<String>() {
                                                            @Override
                                                            public void onSuccess(String data) {
                                                                try {
                                                                    JSONObject jsonObject = new JSONObject(data);
                                                                    if (jsonObject.getInt("code") == 200) {
                                                                        Gson gson = new Gson();
                                                                        RenZhengModel model = gson.fromJson(data, RenZhengModel.class);
                                                                        sBindStaus = model.getObj().getSign();
                                                                        sRenZhengFeiStaus = model.getObj().getPay();
                                                                        if (sRenZhengFeiStaus.equals("1")){
                                                                            toToast(RenZheng3_RenZhengFeiActivity.this,"缴纳成功");
                                                                            WeiboDialogUtils.closeDialog(dialog);
                                                                            onBackPressed();
                                                                        }else {
                                                                            toToast(RenZheng3_RenZhengFeiActivity.this,"支付失败，请重试");
                                                                        }
                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                    toToast(RenZheng3_RenZhengFeiActivity.this,"网络错误，请重试");
                                                                }
                                                                WeiboDialogUtils.closeDialog(dialog);
                                                            }

                                                            @Override
                                                            public void onFail(int errCode, String errMsg) {
                                                                toToast(RenZheng3_RenZhengFeiActivity.this,"网络错误，请重试");
                                                                WeiboDialogUtils.closeDialog(dialog);
                                                            }
                                                        });
                                                //do something
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            toToast(RenZheng3_RenZhengFeiActivity.this,"网络错误，请重试");
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        toToast(RenZheng3_RenZhengFeiActivity.this,"网络错误，请重试");
                        WeiboDialogUtils.closeDialog(dialog);
                    }
                });

    }
    private void checkJiao() {
        Log.d("asdasdas","check_sad");
        ViseHttp.POST(NetConfig.wxQuery)
                .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.wxQuery))
                .addParam("uid", spImp.getUID())
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.getInt("code") == 200) {
                                Gson gson = new Gson();
                                RenZhengModel model = gson.fromJson(data, RenZhengModel.class);
                                sBindStaus = model.getObj().getSign();
                                sRenZhengFeiStaus = model.getObj().getPay();
                                if (sRenZhengFeiStaus.equals("1")){
                                    toToast(RenZheng3_RenZhengFeiActivity.this,"缴纳成功");
                                    onBackPressed();
                                }else {
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            toToast(RenZheng3_RenZhengFeiActivity.this,"网络错误，请重试");
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        toToast(RenZheng3_RenZhengFeiActivity.this,"网络错误，请重试");
                    }
                });
    }
    @OnClick({R.id.btn_yanzheng, R.id.btn_zhifu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_yanzheng:
                if (TextUtils.isEmpty(edtCode.getText())){
                 toToast(RenZheng3_RenZhengFeiActivity.this,"邀请码为空");
                 break;
                }
                dialog = WeiboDialogUtils.createLoadingDialog(RenZheng3_RenZhengFeiActivity.this,"加载中...");
                ViseHttp.POST(NetConfig.getCodeMoney)
                        .addParam("app_key", getToken(NetConfig.BaseUrl + NetConfig.getCodeMoney))
                        .addParam("code", edtCode.getText().toString())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.getInt("code") == 200){
                                        Gson gson = new Gson();
                                        YanZhengYaoQingMaMode mode = gson.fromJson(data,YanZhengYaoQingMaMode.class);
                                        tvPrice.setText("¥"+mode.getObj().getPrice());
                                        toToast(RenZheng3_RenZhengFeiActivity.this,"验证成功！");
                                    }else if (jsonObject.getInt("code") == 400){
                                        toToast(RenZheng3_RenZhengFeiActivity.this,jsonObject.getString("message"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                WeiboDialogUtils.closeDialog(dialog);
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                WeiboDialogUtils.closeDialog(dialog);
                            }
                        });
                break;
            case R.id.btn_zhifu:
                dialog = WeiboDialogUtils.createLoadingDialog(RenZheng3_RenZhengFeiActivity.this,"加载中...");
                ViseHttp.POST(NetConfig.shopInvitePay)
                        .addParam("app_key", TokenUtils.getToken(NetConfig.BaseUrl + NetConfig.shopInvitePay))
                        .addParam("inviteCode", edtCode.getText().toString())
                        .addParam("uid", spImp.getUID())
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                Log.e("222", data);
                                try {
                                    JSONObject pay = new JSONObject(data);
                                    if (pay.getInt("code") == 200) {
                                        Gson gson1 = new Gson();
                                        OrderToPayModel model1 = gson1.fromJson(data, OrderToPayModel.class);
                                        wxPay(model1.getObj());
                                        toToast(RenZheng3_RenZhengFeiActivity.this,model1.getMessage());
                                        WeiboDialogUtils.closeDialog(dialog);
                                        yanchiCheck();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                toToast(RenZheng3_RenZhengFeiActivity.this,errMsg);
                                WeiboDialogUtils.closeDialog(dialog);
                            }
                        });
                break;
        }
    }
    public void wxPay(OrderToPayModel.ObjBean model){
        api.registerApp(UMConfig.WECHAT_APPID);
        PayReq req = new PayReq();
        req.appId = model.getAppId();
        req.partnerId = model.getPartnerId();
        req.prepayId = model.getPrepayId();
        req.nonceStr = model.getNonceStr();
        req.timeStamp = model.getTimestamp()+"";
        req.packageValue = model.getPackageX();
        req.sign = model.getSign();
        req.extData = "app data";
        api.sendReq(req);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WeiboDialogUtils.closeDialog(dialog);
    }
}
