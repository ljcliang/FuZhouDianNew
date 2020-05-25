package com.yiwo.fuzhoudian;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.mixpush.MixPushConfig;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.vise.xsnow.http.ViseHttp;
import com.yatoooon.screenadaptation.ScreenAdapterTools;
import com.yiwo.fuzhoudian.network.NetConfig;
import com.yiwo.fuzhoudian.sp.SpImp;
import com.yiwo.fuzhoudian.utils.FTPTimeCount;
import com.yiwo.fuzhoudian.utils.TimeCount;
import com.yiwo.fuzhoudian.wangyiyunshipin.CustomAttachParser;
import com.yiwo.fuzhoudian.wangyiyunshipin.DemoCache;

import java.util.LinkedList;
import java.util.List;

public class MyApplication extends Application {


    // 注册获取验证码倒计时
    public static TimeCount timecount;
    // 修改密码获取验证码倒计时
    public static FTPTimeCount ftptimecount;
    public static String genPath;
    private List<Activity> mList = new LinkedList<Activity>();
    private static MyApplication instance;
    private SpImp spImp;
    @Override
    public void onCreate() {
        super.onCreate();
        ScreenAdapterTools.init(this);
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//SD卡已挂载
            genPath = getExternalCacheDir().getAbsolutePath().toString() + "/";
        } else {
            genPath = getCacheDir().getAbsolutePath() + "/";
        }
        MultiDex.install(this);
        DemoCache.setContext(this);
        spImp = new SpImp(this);
        //        // 此处 certificate 请传入为开发者配置好的小米证书名称
        MixPushConfig xm_config = new MixPushConfig();
        xm_config.xmAppId = "2882303761518398929";
        xm_config.xmAppKey = "5781839872929";
        xm_config.xmCertificateName = "fzdxiaomizhengshu";

        xm_config.hwCertificateName = "fzdhuaweizhengshu";
        // 4.6.0 开始，第三方推送配置入口改为 SDKOption#mixPushConfig，旧版配置方式依旧支持
        NIMClient.init(this, loginInfo(), options(xm_config));
        if (NIMUtil.isMainProcess(this)) {
            // 注意：以下操作必须在主进程中进行
            // 注册自定义消息附件解析器
            NIMClient.getService(MsgService.class).registerCustomAttachmentParser(new CustomAttachParser());
            // 1、UI相关初始化操作
            // 2、相关Service调用
            // 初始化
            NimUIKit.init(this);
            // 注册定位信息提供者类（可选）,如果需要发送地理位置消息，必须提供。
            // demo中使用高德地图实现了该提供者，开发者可以根据自身需求，选用高德，百度，google等任意第三方地图和定位SDK。
//            NimUIKit.setLocationProvider(new NimDemoLocationProvider());

            //关闭消息提醒。
            NIMClient.toggleNotification(false);
        }
        ViseHttp.init(this);
        ViseHttp.CONFIG()
                //配置请求主机地址
                .baseUrl(NetConfig.BaseUrl);
        timecount = new TimeCount(60000, 1000);
        ftptimecount = new FTPTimeCount(60000, 1000);
    }
//    / 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        Log.d("getgetwangyitoken", "aaaa|||" + spImp.getYXID() + "|||" + spImp.getYXTOKEN());
//         从本地读取上次登录成功时保存的用户登录信息
        String account = spImp.getYXID();
        String token = spImp.getYXTOKEN();
//        String account = null;
//        String token = null;

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
//            DemoCache.setAccount(account.toLowerCase());
            DemoCache.setAccount(account);
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }
    // 如果返回值为 null，则全部使用默认参数。xn_config为小米
    private SDKOptions options(MixPushConfig xm_config) {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = MainActivity.class; // 点击通知栏跳转到该Activity
//        config.notificationSmallIconId = R.mipmap.logo_gray;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;
        options.mixPushConfig = xm_config;
        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用采用默认路径作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
//        String sdkPath = getAppCacheDir(context) + "/nim"; // 可以不设置，那么将采用默认路径
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
//        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
//        options.thumbnailSize = ${Screen.width} / 2;

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = new UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String account, String sessionId,
                                                           SessionTypeEnum sessionType) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(SessionTypeEnum sessionTypeEnum, String s) {
                return null;
            }
        };
        return options;
    }
    public synchronized static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }
    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
