package com.yiwo.fuzhoudian.pages;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.session.module.MsgForwardFilter;
import com.netease.nim.uikit.business.session.module.MsgRevokeFilter;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.mixpush.MixPushConfig;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.yiwo.fuzhoudian.MainActivity;
import com.yiwo.fuzhoudian.MyApplication;
import com.yiwo.fuzhoudian.R;
import com.yiwo.fuzhoudian.base.BaseActivity;
import com.yiwo.fuzhoudian.custom.XieYiDialog;
import com.yiwo.fuzhoudian.fragments.webfragment.HomeDianPuGuanLiFragment;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.network.UMConfig;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.utils.StatusBarUtils;
import com.yiwo.fuzhoudian.wangyiyunshipin.CustomAttachParser;
import com.yiwo.fuzhoudian.wangyiyunshipin.DemoCache;

import java.util.Timer;
import java.util.TimerTask;

import static com.baidu.mapapi.NetworkUtil.isNetworkAvailable;

public class WelcomeActivity extends BaseActivity {

    private SpImp spImp;
    private String account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        StatusBarUtils.setStatusBarTransparent(WelcomeActivity.this);
        spImp = new SpImp(this);
        if (!spImp.isAgree()){
            showAgreeDialog();
        }else {
            initSDK();
            initData();
        }
    }
    private void showAgreeDialog() {
        XieYiDialog dialog = new XieYiDialog(this, new XieYiDialog.XieYiDialogListener() {
            @Override
            public void agreeBtnListen() {
                spImp.setIsAgreeXieYi(true);
                initSDK();
                initData();
            }

            @Override
            public void disAgreeBtnListen() {
                spImp.setIsAgreeXieYi(false);
                MyApplication.getInstance().exit();
            }

            @Override
            public void xieYiTextClickListen() {
                Intent itA = new Intent(WelcomeActivity.this, UserAgreementActivity.class);
                itA.putExtra("title", "用户协议");
                itA.putExtra("url", NetConfig.userAgreementUrl);
                startActivity(itA);
            }

            @Override
            public void zhengCeTextClickListen() {
                Intent itTk = new Intent(WelcomeActivity.this, UserAgreementActivity.class);
                itTk.putExtra("title", "隐私政策");
                itTk.putExtra("url", NetConfig.userAgreementUrl1);
                startActivity(itTk);
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }
    private void initData() {
        account = spImp.getYXID();
        String token = spImp.getYXTOKEN();
        Log.d("asdada",account);
        if(TextUtils.isEmpty(account)||account.equals("0")){
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.setClass(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
//                    if(TextUtils.isEmpty(spImp.getYd())){
//                        intent.setClass(WelcomeActivity.this, GuideActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }else {
//                        intent.setClass(WelcomeActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
                }
            }, 2000);

        }else {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.setClass(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
            if(!isNetworkAvailable(WelcomeActivity.this)){
                toToast(WelcomeActivity.this,"当前无网络");
                return;
            }
            LoginInfo info = new LoginInfo(account, token);
//            LoginInfo info = new LoginInfo(account, "1112");
            RequestCallback<LoginInfo> callback =
                    new RequestCallback<LoginInfo>() {

                        @Override
                        public void onSuccess(LoginInfo loginInfo) {
                            NimUIKit.loginSuccess(account);
                            NimUIKit.setAccount(account);
//                            toToast(WelcomeActivity.this, "登录成功");
                            NimUIKit.setMsgForwardFilter(new MsgForwardFilter() {
                                @Override
                                public boolean shouldIgnore(IMMessage message) {
                                    return false;
                                }
                            });
                            NimUIKit.setMsgRevokeFilter(new MsgRevokeFilter() {
                                @Override
                                public boolean shouldIgnore(IMMessage message) {
                                    return false;
                                }
                            });
                            Intent intent = new Intent();
                            intent.setClass(WelcomeActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailed(int i) {
                            Log.d("dsadsda","登录失败："+i);
//                            toToast(WelcomeActivity.this, "登录失败");
//                            Intent intent = new Intent();
//                            if(TextUtils.isEmpty(spImp.getYd())){
//                                intent.setClass(WelcomeActivity.this, GuideActivity.class);
//                                startActivity(intent);
//                            }else {
////                                intent.setClass(WelcomeActivity.this, MainActivity.class);
//                                spImp.setUID("0");
//                                spImp.setYXID("0");
//                                spImp.setYXTOKEN("0");
//                                spImp.setIsAdmin("0");
//                                spImp.setWyUpAccid("");
//                                spImp.setWyUpToken("");
//                                spCache.clear();
//                                intent.setClass(WelcomeActivity.this, MainActivity.class);
//                                startActivity(intent);
//                            }
//                            finish();
                        }

                        @Override
                        public void onException(Throwable throwable) {
                            toToast(WelcomeActivity.this, "登录出错");
                            Intent intent = new Intent();
                            intent.setClass(WelcomeActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        // 可以在此保存LoginInfo到本地，下次启动APP做自动登录用
                    };
            NimUIKit.login(info,callback);
////            NIMClient.getService(AuthService.class).login(info)
////                    .setCallback(callback);
        }
    }
    private void initSDK(){
        //------------百度地图------------------------------------
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(getApplication());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        //----------------------友盟--------------------
        //初始化组件化基础库, 所有友盟业务SDK都必须调用此初始化接口。
        //建议在宿主App的Application.onCreate函数中调用基础组件库初始化函数。
        UMShareAPI.get(this);
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, "5ed5e8e1dbc2ec08279bd8eb", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        {
            PlatformConfig.setWeixin(UMConfig.WECHAT_APPID, UMConfig.WECHAT_APPSECRET);
        }
    }
}
